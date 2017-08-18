package com.bayue.ciic.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/17.
 */

public class Soso extends BaseActivity {
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
    @BindView(R.id.ed_content_soso)
    EditText edContentSoso;
    @BindView(R.id.iv_del_soso)
    ImageView ivDelSoso;
    @BindView(R.id.tv_soso_soso)
    TextView tvSosoSoso;
    @BindView(R.id.rv_soso)
    RecyclerView rvSoso;
    @BindView(R.id.iv_soso_soso)
    ImageView ivSosoSoso;

    Myadapter myadapter;
    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_soso;
    }

    @Override
    protected void initViews() {
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("搜索");
        ivShezhi.setVisibility(View.INVISIBLE);

        myadapter=new Myadapter();
        rvSoso.setLayoutManager(new LinearLayoutManager(this));
        rvSoso.setHasFixedSize(true);
        rvSoso.setItemAnimator(new DefaultItemAnimator());
        rvSoso.setAdapter(myadapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_soso_soso,R.id.fl_goback, R.id.iv_del_soso, R.id.tv_soso_soso})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.fl_goback:
                finish();
                break;
            case R.id.iv_del_soso:
                break;
            case R.id.tv_soso_soso:
                break;
            case R.id.iv_soso_soso:
                break;
        }
    }
    class Myadapter extends RecyclerView.Adapter<Soso.Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder {


            public MyHolder(View itemView) {
                super(itemView);


            }
        }

        @Override
        public Soso.Myadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_soso, parent, false);
            Soso.Myadapter.MyHolder holder = new Soso.Myadapter.MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(Soso.Myadapter.MyHolder holder, int position) {



        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

}
