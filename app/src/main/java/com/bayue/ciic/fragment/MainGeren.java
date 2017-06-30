package com.bayue.ciic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.ciic.MainActivity;
import com.bayue.ciic.R;
import com.bayue.ciic.activity.GerenComplaint;
import com.bayue.ciic.activity.GerenGuanyu;
import com.bayue.ciic.activity.GerenNews;
import com.bayue.ciic.activity.GerenParty;
import com.bayue.ciic.activity.GerenShezhi;
import com.bayue.ciic.activity.GerenWonderful;
import com.bayue.ciic.activity.GerenXiugai;
import com.bayue.ciic.activity.GerenXx;
import com.bayue.ciic.activity.Gerenlianxi;
import com.bayue.ciic.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/27.
 */

public class MainGeren extends BaseFragment {
    @BindView(R.id.fl_shezhi)
    FrameLayout flShezhi;
    @BindView(R.id.iv_geren_toux)
    ImageView ivGerenToux;
    @BindView(R.id.tv_geren_toux)
    TextView tvGerenToux;
    @BindView(R.id.tv_geren_companyname)
    TextView tvGerenCompanyname;
    @BindView(R.id.tv_geren_mininame)
    TextView tvGerenMininame;
    @BindView(R.id.ll_geren_huodong)
    LinearLayout llGerenHuodong;
    @BindView(R.id.ll_geren_xinwen)
    LinearLayout llGerenXinwen;
    @BindView(R.id.ll_geren_shipin)
    LinearLayout llGerenShipin;
    @BindView(R.id.ll_geren_zhibo)
    LinearLayout llGerenZhibo;
    @BindView(R.id.ll_geren_jingcai)
    LinearLayout llGerenJingcai;
    @BindView(R.id.ll_geren_canyu)
    LinearLayout llGerenCanyu;
    @BindView(R.id.ll_geren_shoucang)
    LinearLayout llGerenShoucang;
    @BindView(R.id.ll_geren_tousu)
    LinearLayout llGerenTousu;
    @BindView(R.id.rl_geren_xx)
    RelativeLayout rlGerenXx;
    @BindView(R.id.rl_geren_guanyu)
    RelativeLayout rlGerenGuanyu;
    @BindView(R.id.rl_geren_lianxi)
    RelativeLayout rlGerenLianxi;
    @BindView(R.id.rl_geren_xiugai)
    RelativeLayout rlGerenXiugai;
    @BindView(R.id.rl_geren_tuichu)
    RelativeLayout rlGerenTuichu;
    Unbinder unbinder;
    MainActivity main;

    @Override
    protected int getViewId() {
        return R.layout.frament_main_geren;
    }

    @Override
    public void init() {
        main= (MainActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fl_shezhi, R.id.iv_geren_toux, R.id.ll_geren_huodong, R.id.ll_geren_xinwen, R.id.ll_geren_shipin, R.id.ll_geren_zhibo, R.id.ll_geren_jingcai, R.id.ll_geren_canyu, R.id.ll_geren_shoucang, R.id.ll_geren_tousu, R.id.rl_geren_xx, R.id.rl_geren_guanyu, R.id.rl_geren_lianxi, R.id.rl_geren_xiugai, R.id.rl_geren_tuichu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_shezhi:
                main.startActivity(new Intent(main,GerenShezhi.class));
                break;
            case R.id.iv_geren_toux:
                break;
            case R.id.ll_geren_huodong:
                main.startActivity(new Intent(main,GerenParty.class));

                break;
            case R.id.ll_geren_xinwen:
                main.startActivity(new Intent(main,GerenNews.class));
                break;
            case R.id.ll_geren_shipin:
                break;
            case R.id.ll_geren_zhibo:
                break;
            case R.id.ll_geren_jingcai:
                main.startActivity(new Intent(main,GerenWonderful.class));

                break;
            case R.id.ll_geren_canyu:
                break;
            case R.id.ll_geren_shoucang:
                break;
            case R.id.ll_geren_tousu:
                main.startActivity(new Intent(main,GerenComplaint.class));
                break;
            case R.id.rl_geren_xx:
                main.startActivity(new Intent(main,GerenXx.class));
                break;
            case R.id.rl_geren_guanyu:
                main.startActivity(new Intent(main,GerenGuanyu.class));
                break;
            case R.id.rl_geren_lianxi:
                main.startActivity(new Intent(main,Gerenlianxi.class));
                break;
            case R.id.rl_geren_xiugai:
                main.startActivity(new Intent(main,GerenXiugai.class));
                break;
            case R.id.rl_geren_tuichu:
                break;
        }
    }
}
