package com.bayue.ciic.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.fragment.PartyPlatfrom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/11.
 */

public class GerenShipin extends BaseActivity {
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
    @BindView(R.id.tv_shipin_platfrom)
    TextView tvShipinPlatfrom;
    @BindView(R.id.tv_shipin_company)
    TextView tvShipinCompany;
    @BindView(R.id.rlv_shipin)
    RecyclerView rlvShipin;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_geren_shipin;
    }

    @Override
    protected void initViews() {
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("视频管理");
        ivShezhi.setVisibility(View.INVISIBLE);

        rlvShipin.setLayoutManager(new LinearLayoutManager(this));
        rlvShipin.setHasFixedSize(true);
        rlvShipin.setItemAnimator(new DefaultItemAnimator());
        rlvShipin.setAdapter(new GerenShipin.Myadapter());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fl_goback, R.id.tv_shipin_platfrom, R.id.tv_shipin_company})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_goback:
                finish();
                break;
            case R.id.tv_shipin_platfrom:
                tvShipinPlatfrom.setBackgroundResource(R.drawable.geren_newstitle_bg);
                tvShipinPlatfrom.setTextColor(getResources().getColor(R.color.themecolor));

                tvShipinCompany.setBackgroundColor(getResources().getColor(R.color.itemtitlecolor));
                tvShipinCompany.setTextColor(getResources().getColor(R.color.h99));

                break;
            case R.id.tv_shipin_company:
                tvShipinCompany.setBackgroundResource(R.drawable.geren_newstitle_bg);
                tvShipinCompany.setTextColor(getResources().getColor(R.color.themecolor));

                tvShipinPlatfrom.setBackgroundColor(getResources().getColor(R.color.itemtitlecolor));
                tvShipinPlatfrom.setTextColor(getResources().getColor(R.color.h99));


                break;
        }
    }

    class Myadapter extends RecyclerView.Adapter<GerenShipin.Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder{

            public MyHolder(View itemView) {
                super(itemView);
            }
        }
        @Override
        public GerenShipin.Myadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_shipin,parent,false);
            GerenShipin.Myadapter.MyHolder holder=new GerenShipin.Myadapter.MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(GerenShipin.Myadapter.MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 8;
        }
    }

}
