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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/11.
 */

public class GerenZhibo extends BaseActivity {
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
    @BindView(R.id.rlv_zhibo)
    RecyclerView rlvZhibo;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {

        return R.layout.activity_geren_zhibo;
    }

    @Override
    protected void initViews() {
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("直播管理");
        ivShezhi.setVisibility(View.INVISIBLE);

        rlvZhibo.setLayoutManager(new LinearLayoutManager(this));
        rlvZhibo.setHasFixedSize(true);
        rlvZhibo.setItemAnimator(new DefaultItemAnimator());
        rlvZhibo.setAdapter(new GerenZhibo.Myadapter());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.fl_goback)
    public void onViewClicked() {
        finish();
    }

    class Myadapter extends RecyclerView.Adapter<GerenZhibo.Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder{

            public MyHolder(View itemView) {
                super(itemView);
            }
        }
        @Override
        public GerenZhibo.Myadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_zhibo,parent,false);
            GerenZhibo.Myadapter.MyHolder holder=new GerenZhibo.Myadapter.MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(GerenZhibo.Myadapter.MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 8;
        }
    }

}
