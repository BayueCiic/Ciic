package com.bayue.ciic.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/28.
 */

public class GerenXx extends BaseActivity {

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
    @BindView(R.id.cb_xx_checkall)
    CheckBox cbXxCheckall;
    @BindView(R.id.tv_xx_del)
    TextView tvXxDel;
    @BindView(R.id.tv_xx_tag)
    TextView tvXxTag;
    @BindView(R.id.rv_xx)
    RecyclerView rvXx;

    @Override
    protected void setTheme() {



    }

    @Override
    protected int getViewId() {
        return R.layout.activity_geren_xx;
    }

    @Override
    protected void initViews() {

        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("官方消息");
        ivShezhi.setVisibility(View.INVISIBLE);
        rvXx.setLayoutManager(new LinearLayoutManager(this));
        rvXx.setHasFixedSize(true);
        rvXx.setItemAnimator(new DefaultItemAnimator());
        rvXx.setAdapter(new Myadapter());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fl_goback, R.id.tv_xx_del, R.id.tv_xx_tag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_goback:
                finish();
                break;
            case R.id.tv_xx_del:
                break;
            case R.id.tv_xx_tag:
                break;
        }
    }

    class Myadapter extends RecyclerView.Adapter<Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder{

            public MyHolder(View itemView) {
                super(itemView);
            }
        }
        @Override
        public Myadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_xx,parent,false);
            MyHolder holder=new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(Myadapter.MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 8;
        }
    }
}
