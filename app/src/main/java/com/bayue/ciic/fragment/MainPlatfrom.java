package com.bayue.ciic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/27.
 */

public class MainPlatfrom extends BaseFragment {
    @BindView(R.id.fl_paltfrom_sousou)
    FrameLayout flPaltfromSousou;
    @BindView(R.id.tv_paltfrom_shouye)
    TextView tvPaltfromShouye;
    @BindView(R.id.tv_paltfrom_zhibo)
    TextView tvPaltfromZhibo;
    @BindView(R.id.tv_paltfrom_shipin)
    TextView tvPaltfromShipin;
    @BindView(R.id.tv_paltfrom_huodong)
    TextView tvPaltfromHuodong;
    @BindView(R.id.tv_paltfrom_xinwen)
    TextView tvPaltfromXinwen;
    @BindView(R.id.tv_paltfrom_jingcai)
    TextView tvPaltfromJingcai;
    @BindView(R.id.view_platfrom_xian)
    View viewPlatfromXian;
    @BindView(R.id.fl_paltfrom_zhibo)
    FrameLayout flPaltfromZhibo;
    @BindView(R.id.vp_paltfrom)
    ViewPager vpPaltfrom;
    Unbinder unbinder;

    ArrayList<BaseFragment> fragments;
    @Override
    protected int getViewId() {
        return R.layout.frament_main_platfrom;
    }

    @Override
    public void init() {
        fragments=new ArrayList<>();
        fragments.add(new PlatfromShouye());
        fragments.add(new PlatfromShouye());

        vpPaltfrom.setAdapter(new VpAdapter(getChildFragmentManager()));
        vpPaltfrom.setCurrentItem(0);
        Log.e("创建++++++++++","111111111==="+fragments.size());

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

    @OnClick({R.id.fl_paltfrom_sousou, R.id.tv_paltfrom_shouye, R.id.tv_paltfrom_zhibo, R.id.tv_paltfrom_shipin, R.id.tv_paltfrom_huodong, R.id.tv_paltfrom_xinwen, R.id.tv_paltfrom_jingcai, R.id.fl_paltfrom_zhibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_paltfrom_sousou:
                break;
            case R.id.tv_paltfrom_shouye:
                break;
            case R.id.tv_paltfrom_zhibo:
                break;
            case R.id.tv_paltfrom_shipin:
                break;
            case R.id.tv_paltfrom_huodong:
                break;
            case R.id.tv_paltfrom_xinwen:
                break;
            case R.id.tv_paltfrom_jingcai:
                break;
            case R.id.fl_paltfrom_zhibo:
                break;
        }
    }
    class  VpAdapter extends FragmentPagerAdapter{
        public VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
