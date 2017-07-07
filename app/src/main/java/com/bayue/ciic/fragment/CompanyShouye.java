package com.bayue.ciic.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/7.
 */

public class CompanyShouye extends BaseFragment {
    @BindView(R.id.iv_shipin_sanjiao)
    ImageView ivShipinSanjiao;
    @BindView(R.id.tv_shipin_shipin)
    TextView tvShipinShipin;
    @BindView(R.id.tv_shipin_xiuxian)
    TextView tvShipinXiuxian;
    @BindView(R.id.view_shipin_one)
    View viewShipinOne;
    @BindView(R.id.tv_shipin_yepao)
    TextView tvShipinYepao;
    @BindView(R.id.view_shipin_two)
    View viewShipinTwo;
    @BindView(R.id.tv_shipin_name)
    TextView tvShipinName;
    @BindView(R.id.tv_shipin_genduo)
    TextView tvShipinGenduo;
    @BindView(R.id.tv_shipin_title)
    TextView tvShipinTitle;
    @BindView(R.id.tv_shipin_compere)
    TextView tvShipinCompere;
    @BindView(R.id.ll_zhibo)
    LinearLayout llZhibo;
    @BindView(R.id.vp_shouye)
    RecyclerView vpShouye;
    @BindView(R.id.iv_huodong_sanjiao)
    ImageView ivHuodongSanjiao;
    @BindView(R.id.tv_huodong_shipin)
    TextView tvHuodongShipin;
    @BindView(R.id.tv_huodong_genduo)
    TextView tvHuodongGenduo;
    @BindView(R.id.tv_time_huodon1)
    TextView tvTimeHuodon1;
    @BindView(R.id.tv_name_huodon1)
    TextView tvNameHuodon1;
    @BindView(R.id.ll_huodon1)
    LinearLayout llHuodon1;
    @BindView(R.id.tv_time_huodon2)
    TextView tvTimeHuodon2;
    @BindView(R.id.tv_name_huodon2)
    TextView tvNameHuodon2;
    @BindView(R.id.ll_huodon2)
    LinearLayout llHuodon2;
    @BindView(R.id.tv_time_huodon3)
    TextView tvTimeHuodon3;
    @BindView(R.id.tv_name_huodon3)
    TextView tvNameHuodon3;
    @BindView(R.id.ll_huodon3)
    LinearLayout llHuodon3;
    @BindView(R.id.tv_time_huodon4)
    TextView tvTimeHuodon4;
    @BindView(R.id.tv_name_huodon4)
    TextView tvNameHuodon4;
    @BindView(R.id.ll_huodon4)
    LinearLayout llHuodon4;
    Unbinder unbinder;

    @Override
    protected int getViewId() {
        return R.layout.frament_company_shouye;
    }

    @Override
    public void init() {
        vpShouye.setLayoutManager(new GridLayoutManager(getContext(),3));
        vpShouye.setHasFixedSize(true);
        vpShouye.setItemAnimator(new DefaultItemAnimator());
        vpShouye.setAdapter(new CompanyShouye.Myadapter());
        vpShouye.addItemDecoration(new CompanyShouye.SpaceItemDecoration(18));




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

    @OnClick({R.id.tv_shipin_genduo, R.id.ll_zhibo, R.id.tv_huodong_genduo, R.id.ll_huodon1, R.id.ll_huodon2, R.id.ll_huodon3, R.id.ll_huodon4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shipin_genduo:
                break;
            case R.id.ll_zhibo:
                break;
            case R.id.tv_huodong_genduo:
                break;
            case R.id.ll_huodon1:
                break;
            case R.id.ll_huodon2:
                break;
            case R.id.ll_huodon3:
                break;
            case R.id.ll_huodon4:
                break;
        }
    }
    class Myadapter extends RecyclerView.Adapter<CompanyShouye.Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder{

            public MyHolder(View itemView) {
                super(itemView);
            }
        }
        @Override
        public CompanyShouye.Myadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platfrom_shouye,parent,false);
            CompanyShouye.Myadapter.MyHolder holder=new CompanyShouye.Myadapter.MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(CompanyShouye.Myadapter.MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 6;
        }
    }
    class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            /*outRect.left = space;
            outRect.bottom = 0;*/

            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) %3==0) {
                outRect.left = 0;
                outRect.right=10;
            }
            if ((parent.getChildLayoutPosition(view)-1) %3==0) {
                outRect.left = 5;
                outRect.right=5;
            }
            if ((parent.getChildLayoutPosition(view)-2) %3==0) {
                outRect.left = 10;
                outRect.right=0;
            }
        }

    }

}
