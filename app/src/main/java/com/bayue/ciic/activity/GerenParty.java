package com.bayue.ciic.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.fragment.NewsCompany;
import com.bayue.ciic.fragment.NewsPlatfrom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/30.
 */

public class GerenParty extends BaseActivity {
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
    @BindView(R.id.tv_news_platfrom)
    TextView tvNewsPlatfrom;
    @BindView(R.id.tv_news_company)
    TextView tvNewsCompany;
    @BindView(R.id.fl_news)
    FrameLayout flNews;

    NewsCompany company;
    NewsPlatfrom platfrom;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_geren_party;
    }

    @Override
    protected void initViews() {
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("活动管理");
        ivShezhi.setVisibility(View.INVISIBLE);

        company=new NewsCompany();
        platfrom=new NewsPlatfrom();
        FragmentTransaction transaction=getFragmentManager().beginTransaction();

        transaction.replace(R.id.fl_news,platfrom);

        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fl_goback, R.id.tv_news_platfrom, R.id.tv_news_company})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_goback:
                finish();
                break;
            case R.id.tv_news_platfrom:
                tvNewsPlatfrom.setBackgroundResource(R.drawable.geren_newstitle_bg);
                tvNewsPlatfrom.setTextColor(getResources().getColor(R.color.themecolor));

                tvNewsCompany.setBackgroundColor(getResources().getColor(R.color.itemtitlecolor));
                tvNewsCompany.setTextColor(getResources().getColor(R.color.h99));

                setFrament(platfrom);
                break;
            case R.id.tv_news_company:

                tvNewsCompany.setBackgroundResource(R.drawable.geren_newstitle_bg);
                tvNewsCompany.setTextColor(getResources().getColor(R.color.themecolor));

                tvNewsPlatfrom.setBackgroundColor(getResources().getColor(R.color.itemtitlecolor));
                tvNewsPlatfrom.setTextColor(getResources().getColor(R.color.h99));

                setFrament(company);
                break;
        }
    }
    private void setFrament(Fragment frament){

        FragmentTransaction transaction=getFragmentManager().beginTransaction();

        transaction.replace(R.id.fl_news,frament);

        transaction.commit();



    }
}
