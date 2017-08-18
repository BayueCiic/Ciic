package com.bayue.ciic.liveplayer;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bayue.ciic.R;
import com.netease.neliveplayer.NELivePlayer;
import com.netease.neliveplayer.NEMediaPlayer;
import com.netease.neliveplayer.c;

import java.io.IOException;

/**
 * Created by Administrator on 2017/8/14.
 */

public class PlayerView extends RelativeLayout implements PlayerContract.MediaPlayControllerBase {

    private NEVideoView myVideoView;
    private View view;

    private NELivePlayer mMediaPlayer = null;

    private Context mContext;

    public PlayerView(Context context) {
        super(context);
        mContext = context;
        addvideo();
        addbottm();
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        addvideo();
        addbottm();
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        addvideo();
        addbottm();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        addvideo();
        addbottm();
    }


    //添加surfaceView
    private void addvideo() {

        myVideoView = new NEVideoView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myVideoView.setLayoutParams(params);
        this.addView(myVideoView);

        SurfaceHolder holder=  myVideoView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                initMedia();

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });




    }
    //控制
    private void addbottm() {

        view = LayoutInflater.from(mContext).inflate(R.layout.mediacontrol_layout, null);
        LayoutParams lp1 = new  LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.setLayoutParams(lp1);
        this.addView(view);

    }


    private void initMedia(){


        Log.e("播放器！！！",">>>>>>>>>1111>>>>>>>");

        NEMediaPlayer neMediaPlayer = new NEMediaPlayer(myVideoView.getContext()); //mContext为VideoView的上下文信息

        mMediaPlayer = neMediaPlayer;
        mMediaPlayer.setBufferStrategy(3); //0为直播极速模式，1为直播低延时模式，2为直播流畅模式, 3为点播抗抖动模式
        mMediaPlayer.setHardwareDecoder(false); //false为软件解码，true为硬件解码

        try {
            mMediaPlayer.setDataSource("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4");//设置数据源，返回0正常，返回-1地址非法
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.setDisplay(myVideoView.getHolder()); //设置显示surface
        mMediaPlayer.setScreenOnWhilePlaying(true); //true:播放过程中屏幕长亮

        mMediaPlayer.prepareAsync();//预处理视频文件，准备播放

        //预处理完成后，sdk会有一个回调，需要先注册一个 OnPreparedListener 获取准备完成的回调
        mMediaPlayer.setOnPreparedListener(new NELivePlayer.OnPreparedListener() {
            @Override
            public void onPrepared(NELivePlayer neLivePlayer) {

                mMediaPlayer.start();

//                mediaPlayerControl.setTime(mMediaPlayer.getDuration());

            }
        });

    }




















    @Override
    public void start() {
        initMedia();
    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public void seekTo(long pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public boolean switchPauseResume() {
        return false;
    }

    @Override
    public void setManualPause(boolean paused) {

    }

    @Override
    public void setVideoScalingMode(int videoScalingMode) {

    }

    @Override
    public void setMute(boolean mute) {

    }

    @Override
    public void initVideo() {

    }

    @Override
    public void onActivityResume() {

    }

    @Override
    public void onActivityPause() {

    }

    @Override
    public void onActivityDestroy() {

    }

    @Override
    public void getSnapshot() {

    }

    @Override
    public boolean isLocalAudio() {
        return false;
    }

    @Override
    public boolean isLiveStream() {
        return false;
    }

    @Override
    public boolean isHwDecoder() {
        return false;
    }
}