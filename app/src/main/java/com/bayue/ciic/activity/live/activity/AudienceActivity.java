package com.bayue.ciic.activity.live.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.liveplayer.NEVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/18.
 */

public class AudienceActivity extends BaseActivity {
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
    @BindView(R.id.iv_video_photo)
    ImageView ivVideoPhoto;
    @BindView(R.id.tv_video_name)
    TextView tvVideoName;
    @BindView(R.id.tv_video_title)
    TextView tvVideoTitle;
    @BindView(R.id.tv_video_time)
    TextView tvVideoTime;
    @BindView(R.id.nevideoview)
    NEVideoView nevideoview;
    @BindView(R.id.tv_video_number)
    TextView tvVideoNumber;
    @BindView(R.id.ll_number_shipin)
    LinearLayout llNumberShipin;
    @BindView(R.id.iv_video_zan)
    ImageView ivVideoZan;
    @BindView(R.id.tv_video_zan)
    TextView tvVideoZan;
    @BindView(R.id.ll_zan_shipin)
    LinearLayout llZanShipin;
    @BindView(R.id.tv_video_fen)
    TextView tvVideoFen;
    @BindView(R.id.ll_feng_shipin)
    LinearLayout llFengShipin;
    @BindView(R.id.hr)
    View hr;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.send)
    TextView send;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_audience;
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

    @OnClick(R.id.fl_goback)
    public void onViewClicked() {
    }
}
