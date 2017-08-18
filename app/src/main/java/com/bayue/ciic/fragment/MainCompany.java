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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/27.
 */

public class MainCompany extends BaseFragment {
    @BindView(R.id.fl_company_sousou)
    FrameLayout flCompanySousou;
    @BindView(R.id.tv_company_shouye)
    TextView tvCompanyShouye;
    @BindView(R.id.tv_company_zhibo)
    TextView tvCompanyZhibo;
    @BindView(R.id.tv_company_shipin)
    TextView tvCompanyShipin;
    @BindView(R.id.tv_company_huodong)
    TextView tvCompanyHuodong;
    @BindView(R.id.view_platfrom_xian)
    View viewPlatfromXian;
    @BindView(R.id.iv_shezhi)
    ImageView ivShezhi;
    @BindView(R.id.tv_shezhi)
    TextView tvShezhi;
    @BindView(R.id.fl_company_zhibo)
    FrameLayout flCompanyZhibo;
    @BindView(R.id.vp_company)
    ViewPager vpCompany;
    Unbinder unbinder;

    int currentIndex;

    int screenWidth;

    ArrayList<BaseFragment> fragments;
    @BindView(R.id.ll_div)
    LinearLayout llDiv;

    @Override
    protected int getViewId() {
        return R.layout.frament_main_company;
    }

    @Override
    public void init() {
        fragments = new ArrayList<>();
        initTabLineWidth();

        fragments.add(CompanyShouye.getCompanyShouye(this));
        fragments.add(new PlatfromZhibo(false));
        fragments.add(new PlatfromShipin(false));
        fragments.add(new PlatfromHuodong(false));

        vpCompany.setAdapter(new VpAdapter(getChildFragmentManager()));
        vpCompany.setCurrentItem(0);
        setColor(0);
        vpCompany.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->2; 2->1; 1->0
                 */

                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                    Log.e(">>>>>滑动>>>", "" + lp.leftMargin);

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }else if (currentIndex == 2&& position == 2) // 2->3
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }else if (currentIndex == 3 && position == 2) // 3->2
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }
                Log.e("<<<<<<企业<<<<<",lp.leftMargin+"");
                viewPlatfromXian.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                setColor(position);
                currentIndex = position;
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        initTabLineWidth();
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
                lp.width = screenWidth / 4;
                Log.e("lldiv==222==",lp.width+"");
                viewPlatfromXian.setLayoutParams(lp);
//                textView.append("\n\n" + imageView.getHeight() + "," + imageView.getWidth());
            }
        });


    }
    private void resetTextView(){
        tvCompanyShouye.setTextColor(getContext().getResources().getColor(R.color.newgerenBg));
        tvCompanyZhibo.setTextColor(getContext().getResources().getColor(R.color.newgerenBg));
        tvCompanyShipin.setTextColor(getContext().getResources().getColor(R.color.newgerenBg));
        tvCompanyHuodong.setTextColor(getContext().getResources().getColor(R.color.newgerenBg));
    }
    private void setColor(int i){
        resetTextView();
        switch (i){
            case 0:
                tvCompanyShouye.setTextColor(getContext().getResources().getColor(R.color.white));
                break;
            case 1:
                tvCompanyZhibo.setTextColor(getContext().getResources().getColor(R.color.white));

                break;
            case 2:
                tvCompanyShipin.setTextColor(getContext().getResources().getColor(R.color.white));

                break;
            case 3:
                tvCompanyHuodong.setTextColor(getContext().getResources().getColor(R.color.white));

                break;

        }

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

    @OnClick({R.id.fl_company_sousou, R.id.tv_company_shouye, R.id.tv_company_zhibo, R.id.tv_company_shipin, R.id.tv_company_huodong, R.id.fl_company_zhibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_company_sousou:
                break;
            case R.id.tv_company_shouye:
                vpCompany.setCurrentItem(0);
                setColor(0);
                break;
            case R.id.tv_company_zhibo:
                vpCompany.setCurrentItem(1);
                setColor(1);
                break;
            case R.id.tv_company_shipin:
                vpCompany.setCurrentItem(2);
                setColor(2);
                break;
            case R.id.tv_company_huodong:
                vpCompany.setCurrentItem(3);
                setColor(3);
                break;
            case R.id.fl_company_zhibo:
                break;
        }
    }

    public void setCurrentItem(int item){

        vpCompany.setCurrentItem(item);
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
