/*
 * Copyright (C) 2006 The Android Open Source Project
 * Copyright (C) 2015 netease
 * @auther biwei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bayue.ciic.liveplayer;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

import com.netease.neliveplayer.NELivePlayer;
import com.netease.neliveplayer.NEMediaPlayer;
import com.netease.neliveplayer.c;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NEVideoView extends SurfaceView {
    private static final String TAG = "NEVideoView";

    private int mVideoWidth;//view的宽
    private int mVideoHeight;//view的高
    private int mPixelSarNum;//像素的甚么值
    private int mPixelSarDen;//像素的甚么值
    private int mSurfaceWidth;//播放画面的宽
    private int mSurfaceHeight;//播放画面的高
    private static Context mContext;

    PlayerContract.MediaPlayControllerBase controllerBase;

    private NELivePlayer mMediaPlayer = null;

    public void setMediaPlayControllerBase( PlayerContract.MediaPlayControllerBase controllerBase){
        if(controllerBase!=null)
        this.controllerBase=controllerBase;

    }
    //键盘的监听接口
    EventListener listener;



    interface EventListener {

        boolean onKeyDown(int keyCode, KeyEvent event);

        void onTouchEvent(MotionEvent event);

        void onTrackballEvent(MotionEvent event);
    }

    //构造器
    public NEVideoView(Context context) {
        super(context);
        NEVideoView.mContext = context;
        initVideoView();
    }

    public NEVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        NEVideoView.mContext = context;
        initVideoView();
    }

    public NEVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        NEVideoView.mContext = context;
        initVideoView();
    }

    //本view获取焦点
    private void initVideoView() {

        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    //定义本view的尺寸
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            if ( mVideoWidth * height  > width * mVideoHeight ) {
                //Log.i("@@@", "image too tall, correcting");
                //height = width * mVideoHeight / mVideoWidth;
            } else if ( mVideoWidth * height  < width * mVideoHeight ) {
                //Log.i("@@@", "image too wide, correcting");
                //width = height * mVideoWidth / mVideoHeight;
            } else {
                //Log.i("@@@", "aspect ratio is correct: " +
                        //width+"/"+height+"="+
                        //mVideoWidth+"/"+mVideoHeight);
            }
        }
        setMeasuredDimension(width, height);
    }

    //设置video的扩展模式
    public void setVideoScalingMode(int videoScalingMode) {
        //Log.d("zhukun", "in setVideoMode myVideoWidth:" + mVideoWidth +"height"+ mVideoHeight);
        LayoutParams layPara = getLayoutParams();
        int winWidth = 0;
        int winHeight = 0;
        Rect rect = new Rect();
        this.getWindowVisibleDisplayFrame(rect);//获取状态栏高度
        //获取获取WindowManager服务：
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay(); //获取屏幕分辨率

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) { //new
            DisplayMetrics metrics = new DisplayMetrics();
            display.getRealMetrics(metrics);
            winWidth = metrics.widthPixels;
            winHeight = metrics.heightPixels - rect.top;
        } else { //old
            try {
                Method mRawWidth = Display.class.getMethod("getRawWidth");
                Method mRawHeight = Display.class.getMethod("getRawHeight");
                winWidth = (Integer) mRawWidth.invoke(display);
                winHeight = (Integer) mRawHeight.invoke(display) - rect.top;
            } catch (NoSuchMethodException e) {
                DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
                winWidth = dm.widthPixels;
                winHeight = dm.heightPixels - rect.top;
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        float winRatio = (float) winWidth / winHeight;
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            float aspectRatio = (float) (mVideoWidth) / mVideoHeight;
            if (mPixelSarNum > 0 && mPixelSarDen > 0)
                aspectRatio = aspectRatio * mPixelSarNum / mPixelSarDen;
            mSurfaceHeight = mVideoHeight;
            mSurfaceWidth = mVideoWidth;

            if (VideoConstant.VIDEO_SCALING_MODE_NONE == videoScalingMode && mSurfaceWidth < winWidth && mSurfaceHeight < winHeight) {
                layPara.width = (int) (mSurfaceHeight * aspectRatio);
                layPara.height = mSurfaceHeight;
            } else if (VideoConstant.VIDEO_SCALING_MODE_FIT == videoScalingMode) { // 按宽度计算
                if (winRatio < aspectRatio) {
                    layPara.width  = winWidth;
                    layPara.height = (int)(winWidth / aspectRatio);
                }
                else {
                    layPara.width  = (int)(aspectRatio * winHeight);
                    layPara.height = winHeight;
                }
            } else if (VideoConstant.VIDEO_SCALING_MODE_FILL_SCALE == videoScalingMode){ //全屏拉伸裁剪
               if(winRatio > aspectRatio){
                   layPara.width = winWidth;
                   layPara.height = (int) (winWidth/aspectRatio);
               }else{
                   layPara.width = (int)(winHeight * aspectRatio);
                   layPara.height = winHeight;
               }
            }
            else if (VideoConstant.VIDEO_SCALING_MODE_FILL_BLACK == videoScalingMode) { // 全屏留黑边，信息全保留
                if (winRatio > aspectRatio) {
                    layPara.width  = (int)(winHeight * aspectRatio);
                    layPara.height = winHeight;
                }
                else {
                    layPara.width  = winWidth;
                    layPara.height = (int)(winWidth / aspectRatio);
                }
            } else {
                if (winRatio < aspectRatio) {
                    layPara.width  = winWidth;
                    layPara.height = (int)(winWidth / aspectRatio);
                }
                else {
                    layPara.width  = (int)(aspectRatio * winHeight);
                    layPara.height = winHeight;
                }
            }
            setLayoutParams(layPara);
            getHolder().setFixedSize(mSurfaceWidth, mSurfaceHeight);
        }
    }

    //返回键的监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(listener != null){
            if(listener.onKeyDown(keyCode, event)){
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //轨迹球的
    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        if(listener != null){
            listener.onTrackballEvent(event);
        }
        return false;
    }

    //屏幕事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(listener !=null ){
            listener.onTouchEvent(event);
        }
        return false;
    }

    //设置 view宽高和 像素属性
    public void upDateVideoSize(int videoWidth, int videoHeight, int pixelSarNum, int pixelSarDen){

        if(videoWidth!=0 && videoHeight!=0){
            this.mVideoWidth = videoWidth;
            this.mVideoHeight = videoHeight;
        }

        if(pixelSarNum!=0 && pixelSarDen!=0){
            this.mPixelSarNum = pixelSarNum;
            this.mPixelSarDen = pixelSarDen;
        }
    }

    //获取surface宽
    public int getSurfaceWidth(){
        return this.mSurfaceWidth;
    }

    //获取surface高
    public int getSurfaceHeight(){
        return this.mSurfaceHeight;
    }

    //设置 surface宽高和 像素属性
    public void onSurfaceChanged(int w, int h) {
        this.mSurfaceWidth = w;
        this.mSurfaceHeight = h;
    }

    //判断view和surface宽高是否一样
    public boolean isSizeNormal() {
        return mSurfaceWidth == mVideoWidth && mSurfaceHeight == mVideoHeight;
    }

    //获取surface宽
    public int getVideoWidth() {
        return mVideoWidth;
    }

    //获取surface高
    public int getVideoHeight() {
        return mVideoHeight;
    }

    //设置 监听
    public void setEventListener(EventListener lis){
        this.listener = lis;
    }
}
