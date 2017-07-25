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
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseFragment;
import com.bayue.ciic.bean.TagBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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


    int currentIndex;

    int screenWidth;

    ArrayList<BaseFragment> fragments;
    @BindView(R.id.ll_div)
    LinearLayout llDiv;
    @BindView(R.id.iv_shezhi)
    ImageView ivShezhi;
    @BindView(R.id.tv_shezhi)
    TextView tvShezhi;

    float offsets;
    @Override
    protected int getViewId() {
        return R.layout.frament_main_platfrom;
    }

    @Override
    public void init() {
        fragments = new ArrayList<>();
        initTabLineWidth();
        fragments.add(PlatfromShouye.getPlatfromShouye(this));
        fragments.add(new PlatfromZhibo());
        fragments.add(new PlatfromShipin());
        fragments.add(new PlatfromHuodong());
        fragments.add(new PlatfromNews());
        fragments.add(new PlatfromWenderful());

        vpPaltfrom.setAdapter(new VpAdapter(getChildFragmentManager()));
        vpPaltfrom.setCurrentItem(0);
        setColor(0);
//        Log.e("创建++++++++++","111111111==="+fragments.size());

        vpPaltfrom.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset,
                                       int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPlatfromXian
                        .getLayoutParams();

                Log.e("offset:", offset + "");

                if(currentIndex==5){
                    offsets=offset;

                }
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->2; 2->1; 1->0
                 */

                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6) + currentIndex
                            * (screenWidth / 6));
                    Log.e(">>>>>滑动>>>", "" + lp.leftMargin);

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 6) + currentIndex
                            * (screenWidth / 6));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6) + currentIndex
                            * (screenWidth / 6));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 6) + currentIndex
                            * (screenWidth / 6));
                } else if (currentIndex == 2 && position == 2) // 2->3
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6) + currentIndex
                            * (screenWidth / 6));
                } else if (currentIndex == 3 && position == 2) // 3->2
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 6) + currentIndex
                            * (screenWidth / 6));
                } else if (currentIndex == 3 && position == 3) // 3->4
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6) + currentIndex
                            * (screenWidth / 6));
                } else if (currentIndex == 4 && position == 3) // 4->3
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 6) + currentIndex
                            * (screenWidth / 6));
                } else if (currentIndex == 4 && position == 4) // 4->5
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6) + currentIndex
                            * (screenWidth / 6));
                } else if (currentIndex == 5 && position == 4) // 5->4
                {
                    lp.leftMargin = (int) (-(1 - offsets)
                            * (screenWidth * 1.0 / 6) + currentIndex
                            * (screenWidth / 6));
                }
                Log.e("<<<<<平台<<<<<<",lp.leftMargin+"");
                viewPlatfromXian.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                setColor(position);
                currentIndex = position;
            }
        });


    }
    private void resetTextView(){
        tvPaltfromShouye.setTextColor(getContext().getResources().getColor(R.color.newgerenBg));
        tvPaltfromZhibo.setTextColor(getContext().getResources().getColor(R.color.newgerenBg));
        tvPaltfromShipin.setTextColor(getContext().getResources().getColor(R.color.newgerenBg));
        tvPaltfromHuodong.setTextColor(getContext().getResources().getColor(R.color.newgerenBg));
        tvPaltfromXinwen.setTextColor(getContext().getResources().getColor(R.color.newgerenBg));
        tvPaltfromJingcai.setTextColor(getContext().getResources().getColor(R.color.newgerenBg));
    }
    private void setColor(int i){
        resetTextView();
        switch (i){
            case 0:
                tvPaltfromShouye.setTextColor(getContext().getResources().getColor(R.color.white));
                break;
            case 1:
                tvPaltfromZhibo.setTextColor(getContext().getResources().getColor(R.color.white));

                break;
            case 2:
                tvPaltfromShipin.setTextColor(getContext().getResources().getColor(R.color.white));

                break;
            case 3:
                tvPaltfromHuodong.setTextColor(getContext().getResources().getColor(R.color.white));

                break;
            case 4:
                tvPaltfromXinwen.setTextColor(getContext().getResources().getColor(R.color.white));

                break;
            case 5:
                tvPaltfromJingcai.setTextColor(getContext().getResources().getColor(R.color.white));

                break;
        }

    }

    private void initTabLineWidth() {
        ViewTreeObserver vto2 = llDiv.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llDiv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                screenWidth=llDiv.getWidth();
                Log.e("lldiv==111==",screenWidth+"");
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPlatfromXian
                        .getLayoutParams();
                lp.width = screenWidth / 6;
                Log.e("lldiv==222==",lp.width+"");
                viewPlatfromXian.setLayoutParams(lp);
//                textView.append("\n\n" + imageView.getHeight() + "," + imageView.getWidth());
            }
        });


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
                vpPaltfrom.setCurrentItem(0);
                setColor(0);
                break;
            case R.id.tv_paltfrom_zhibo:
                vpPaltfrom.setCurrentItem(1);
                setColor(1);
                break;
            case R.id.tv_paltfrom_shipin:
                vpPaltfrom.setCurrentItem(2);
                setColor(2);
                break;
            case R.id.tv_paltfrom_huodong:
                vpPaltfrom.setCurrentItem(3);
                setColor(3);
                break;
            case R.id.tv_paltfrom_xinwen:
                vpPaltfrom.setCurrentItem(4);
                setColor(4);
                break;
            case R.id.tv_paltfrom_jingcai:
                vpPaltfrom.setCurrentItem(5);
                setColor(5);
                break;
            case R.id.fl_paltfrom_zhibo:
                break;
        }
    }

    public void setCurrentItem(int item){

        vpPaltfrom.setCurrentItem(item);
        setColor(item);
    }



    class VpAdapter extends FragmentPagerAdapter {
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
