package com.bayue.ciic.activity.live.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.liveplayer.NEVideoView;
import com.bayue.ciic.liveplayer.PlayerContract;
import com.bayue.ciic.liveplayer.VideoConstant;
import com.bayue.ciic.liveplayer.VideoPlayerController;
import com.netease.neliveplayer.NELivePlayer;
import com.netease.neliveplayer.NEMediaPlayer;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.log.LogUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/1.
 */

public class VideoTest extends BaseActivity implements PlayerContract.MediaPlayControllerBase {
    @BindView(R.id.iv_goback)
    ImageView ivGoback;
    @BindView(R.id.fl_goback)
    FrameLayout flGoback;
    @BindView(R.id.tv_titletxt)
    TextView tvTitletxt;
    @BindView(R.id.iv_shezhi)
    ImageView ivShezhi;
    @BindView(R.id.tv_shezhi)
    TextView tvShezhi;
    @BindView(R.id.fl_shezhi)
    FrameLayout flShezhi;
    @BindView(R.id.video_view)
    NEVideoView videoView;


    /**
     * sdk controller
     */
    private NELivePlayer mMediaPlayer = null;
    private SurfaceHolder mSurfaceHolder;

    private NEVideoView mVideoView;

    private Handler mHandler;


    public static final String TYPE_LIVE_STREAM = "livestream";
    public static final String TYPE_LOCAL_AUDIO = "localaudio";
    public static final String TYPE_VIDEO_ON_DEMAND = "videoondemand";

    //states refer to MediaPlayer
    private static final int IDLE = 0;
    private static final int INITIALIZED = 1;
    private static final int PREPARING = 2;
    private static final int PREPARED = 3;
    private static final int STARTED = 4;
    private static final int PAUSED = 5;
    private static final int STOPED = 6;
    private static final int PLAYBACKCOMPLETED = 7;
    private static final int END = 8;
    private static final int RESUME = 9;
    private static final int ERROR = -1;

    private String mMediaType = TYPE_LIVE_STREAM;
    private String mVideoPath;
    private int mBufferStrategy = 0; //直播低延时
    private Uri mUri;
    private int mVideoScalingMode = VideoConstant.VIDEO_SCALING_MODE_FIT;

    /**
     * time
     */
    private long mDuration = 0;
    private long mPlayableDuration = 0;
    private long mSeekWhenPrepared;
    private int mCurrentBufferPercentage;

    /**
     * state
     */
    private boolean mIsPrepared;
    private boolean isHwDecoder = false; //是否硬件解码
    private boolean mPauseInBackground = true; //进入后台时是否暂停
    private boolean isBackground; //是否在后台
    private boolean manualPause = false;
    private int mCurrState = IDLE;
    private int mNextState = IDLE;
    private boolean hasVideo = false;
    private boolean hasAudio = false;


    private SdkStateListener sdkStateListener = new SdkStateListener();
    /**
     * 底层状态监听器
     */
    public class SdkStateListener implements NELivePlayer.OnVideoSizeChangedListener, NELivePlayer.OnPreparedListener, NELivePlayer.OnCompletionListener, NELivePlayer.OnErrorListener,
            NELivePlayer.OnBufferingUpdateListener, NELivePlayer.OnInfoListener, NELivePlayer.OnSeekCompleteListener, NELivePlayer.OnVideoParseErrorListener {




        public void onVideoSizeChanged(NELivePlayer mp, int width, int height,
                                       int sarNum, int sarDen) {
//            Log.d(TAG, "onVideoSizeChanged: " + width + "x" + height + "sarNum:" + sarNum + "sarDen:" + sarDen);
            mVideoView.upDateVideoSize(mp.getVideoWidth(), mp.getVideoHeight(), sarNum, sarDen);
            if (mp.getVideoWidth() != 0 && mp.getVideoHeight() != 0)
                setVideoScalingMode(mVideoScalingMode);
        }

        public void onPrepared(NELivePlayer mp) {
//            Log.d(TAG, "onPrepared");
            mCurrState = PREPARED;
            mNextState = STARTED;
            // briefly show the controlLayout
            mIsPrepared = true;

            if (isHwDecoder) {
//                mUi.showLoading(false);
            }

            /*if (mMediaControlLayout != null) {
                mMediaControlLayout.setEnabled(true);
            }*/

            mVideoView.upDateVideoSize(mp.getVideoWidth(), mp.getVideoHeight(), 0, 0);

            if (mMediaPlayer != null && mVideoView.isSizeNormal()) {

                if (mSeekWhenPrepared != 0 && !isLiveStream()) {
                    seekTo(mSeekWhenPrepared);
                }
                if (!isManualPaused()) {
                    start();
                } else {
                    pause();
                }
//                if (mMediaControlLayout != null) {
//                    mMediaControlLayout.show();
//                }
            }
            mVideoView.setVisibility(View.VISIBLE);

        }

        public void onCompletion(NELivePlayer mp) {
//            LogUtil.d(TAG, "onCompletion");
            mCurrState = PLAYBACKCOMPLETED;
           /* if (mMediaControlLayout != null)
                mMediaControlLayout.hide();

            if (mUi.onCompletion()) {
                return;
            }*/

            if (mVideoView.getWindowToken() != null && mMediaType.equals(TYPE_LIVE_STREAM)) {
                // 适配Android6.0
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(VideoTest.this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(VideoTest.this);
                }

                builder.setTitle("Completed!")
                        .setMessage("播放结束！")
                        .setPositiveButton("OK", null)
                        .setCancelable(false)
                        .show();
            }
        }

        public boolean onError(NELivePlayer mp, int a, int b) {
//            Log.d(TAG, "Error: " + a + "," + b);
            mCurrState = ERROR;
//            if (mMediaControlLayout != null) {
//                mMediaControlLayout.hide();
//            }
//            if (needResumeWhenNetworkConnect) {
//                //若网络检查器已启动,则由检测器处理异常.
//                return true;
//            }
//            mUi.onError(isLiveStream() ? "直播异常" : "播放异常");
            return true;
        }

        public void onBufferingUpdate(NELivePlayer mp, int percent) {
            mCurrentBufferPercentage = percent;
//            mUi.onBufferingUpdate();
        }

        @Override
        public boolean onInfo(NELivePlayer mp, int what, final int extra) {
//            Log.d(TAG, "onInfo: " + what + ", " + extra);
            if (mMediaPlayer != null) {
//                Log.d(TAG, "mMediaPlayer != null");
                if (what == NELivePlayer.NELP_BUFFERING_START) {
//                    LogUtil.d(TAG, "onInfo: NELP_BUFFERING_START");
//                    mUi.showLoading(true);
                } else if (what == NELivePlayer.NELP_BUFFERING_END) {
//                    LogUtil.d(TAG, "onInfo: NELP_BUFFERING_END");
//                    mUi.showLoading(false);
                    DialogMaker.dismissProgressDialog();
                } else if (what == NELivePlayer.NELP_FIRST_VIDEO_RENDERED) {
//                    LogUtil.d(TAG, "onInfo: NELP_FIRST_VIDEO_RENDERED");
                    hasVideo = true;
//                    mUi.showLoading(false);
//                    mUi.showAudioAnimate(false);
                    DialogMaker.dismissProgressDialog();
                } else if (what == NELivePlayer.NELP_FIRST_AUDIO_RENDERED) {
//                    LogUtil.d(TAG, "onInfo: NELP_FIRST_AUDIO_RENDERED");
                    hasAudio = true;
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!hasVideo) {
//                                mUi.showLoading(false);
//                                mUi.showAudioAnimate(true);
                            }
                        }
                    }, 500);

                }
            }
            return true;
        }

        @Override
        public void onSeekComplete(NELivePlayer mp) {
//            Log.d(TAG, "onSeekComplete");
//            mUi.onSeekComplete();
        }

        @Override
        public void onVideoParseError(NELivePlayer neLivePlayer) {
//            Log.i(TAG, "onVideoParseError");
        }
    }


    /**
     * 处理surfaceView生命周期
     * 在此处控制进入后台时的数据存储
     */
    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//            Log.d(TAG, "onSurfaceChanged");
            mSurfaceHolder = holder;
            if (mMediaPlayer != null) {
                mMediaPlayer.setDisplay(mSurfaceHolder);
            }
            mVideoView.onSurfaceChanged(w, h);
            if (!isHwDecoder && mPauseInBackground && mIsPrepared && mVideoView.isSizeNormal()) {
                if (isManualPaused()) {
                    pause();
                } else {
                    start();
                }
            }

        }



        private Handler mHandler;

        public void surfaceCreated(SurfaceHolder holder) {
//            Log.d(TAG, "onSurfaceCreated");
            mSurfaceHolder = holder;

            if (mNextState == RESUME || isBackground) {
                if (isHwDecoder) {
                    openVideo();
//                    mUi.showLoading(true);
                    isBackground = false; //不在后台
                } else if (mPauseInBackground) {
                    isBackground = false; //不在后台
                }
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
//            Log.d(TAG, "onSurfaceDestroyed");
            mSurfaceHolder = null;
//            if (mMediaControlLayout != null) mMediaControlLayout.hide();
            if (mMediaPlayer != null) {
                if (isHwDecoder) {
//                    mSeekWhenPrepared = getCurrentPosition();
                    if (mMediaPlayer != null) {
                        mMediaPlayer.setDisplay(null);
                        mMediaPlayer.reset();
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                        mCurrState = IDLE;
                    }
                    isBackground = true;
                } else if (!mPauseInBackground) {
                    mMediaPlayer.setDisplay(null);
                    isBackground = true;
                } else {
                    pause();
                    isBackground = true;
                }
                mNextState = RESUME;
            }
        }
    };

    public boolean isManualPaused() {
        return manualPause;
    }
   /* public void setLogPath() {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                mLogPath = Environment.getExternalStorageDirectory() + "/log/";
            }
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
    }*/
    public void release_resource() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrState = IDLE;
        }
    }
    private void openVideo() {

        if (mUri == null || mSurfaceHolder == null) {
            // not ready for playback just yet, will try again later
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    openVideo();
                }
            }, 200);
            return;
        }

        // Tell the music playback service to pause
        // TODO: these constants need to be published somewhere in the framework
        Intent i = new Intent("com.android.music.musicservicecommand");
        i.putExtra("command", "pause");
        this.sendBroadcast(i);

        release_resource();

        mIsPrepared = false;
//        setLogPath();
        NEMediaPlayer neMediaPlayer = new NEMediaPlayer(VideoTest.this.getBaseContext());
//        neMediaPlayer.setLogPath(mLogLevel, mLogPath);
        neMediaPlayer.setBufferStrategy(mBufferStrategy);//设置缓冲策略，0为直播低延时，1为点播抗抖动
        neMediaPlayer.setHardwareDecoder(isHwDecoder);//设置是否开启硬件解码，0为软解，1为硬解
        neMediaPlayer.setOnPreparedListener(sdkStateListener);
        neMediaPlayer.setOnVideoSizeChangedListener(sdkStateListener);
        neMediaPlayer.setOnCompletionListener(sdkStateListener);
        neMediaPlayer.setOnErrorListener(sdkStateListener);
        neMediaPlayer.setOnBufferingUpdateListener(sdkStateListener);
        neMediaPlayer.setOnInfoListener(sdkStateListener);
        neMediaPlayer.setOnSeekCompleteListener(sdkStateListener);
        neMediaPlayer.setOnVideoParseErrorListener(sdkStateListener);
        mMediaPlayer = neMediaPlayer;
//        attachControlLayout();
        int ret;
        try {
            ret = neMediaPlayer.setDataSource(mUri.toString());
            if (ret < 0) {
                if (isLiveStream()) {
//                    mUi.onError("地址非法，请输入网易视频云官方地址!");
                }
                release_resource();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            sdkStateListener.onError(mMediaPlayer, -1, 0);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mCurrState = INITIALIZED;
                    mNextState = PREPARING;
                    mMediaPlayer.setPlaybackTimeout(30000);
                    mMediaPlayer.setDisplay(mSurfaceHolder);
                    mMediaPlayer.setScreenOnWhilePlaying(true);
                    mMediaPlayer.prepareAsync();
                    mCurrState = PREPARING;
                } catch (Exception e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            sdkStateListener.onError(mMediaPlayer, -1, 0);
                        }
                    });
                }
            }
        }).start();
    }



    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_live_test;
    }

    @Override
    protected void initViews() {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void start() {

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
