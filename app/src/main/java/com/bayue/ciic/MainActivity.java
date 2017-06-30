package com.bayue.ciic;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.fragment.MainCompany;
import com.bayue.ciic.fragment.MainGeren;
import com.bayue.ciic.fragment.MainPlatfrom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.ll_main_platform)
    LinearLayout llMainPlatform;
    @BindView(R.id.ll_main_company)
    LinearLayout llMainCompany;
    @BindView(R.id.ll_main_geren)
    LinearLayout llMainGeren;


    MainCompany company;
    MainPlatfrom platfrom;
    MainGeren geren;


    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

        platfrom=new MainPlatfrom();
        company=new MainCompany();
        geren=new MainGeren();
        setFrament(geren,llMainGeren);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_main_platform, R.id.ll_main_company, R.id.ll_main_geren})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_main_platform:
                setFrament(platfrom,llMainPlatform);
                llMainPlatform.setEnabled(false);
                llMainCompany.setEnabled(true);
                llMainGeren.setEnabled(true);
                break;
            case R.id.ll_main_company:
                setFrament(company,llMainCompany);
                llMainPlatform.setEnabled(true);
                llMainCompany.setEnabled(false);
                llMainGeren.setEnabled(true);
                break;
            case R.id.ll_main_geren:
                setFrament(geren,llMainGeren);
                llMainPlatform.setEnabled(true);
                llMainCompany.setEnabled(true);
                llMainGeren.setEnabled(false);
                break;
        }
    }
    private void setFrament(Fragment fragm,LinearLayout ll){
        restoreColor();

        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        ll.setBackgroundColor(getResources().getColor(R.color.clickColor));
        getFragmentManager().popBackStack();
//        Log.e("数量yyyyyyy", getFragmentManager().getBackStackEntryCount()+"");
        transaction.replace(R.id.fl_main,fragm);
//        transaction.addToBackStack(null);//将fragment加入返回栈
        transaction.commit();
    }

    private void restoreColor(){
        llMainPlatform.setBackgroundColor(getResources().getColor(R.color.themecolor));
        llMainCompany.setBackgroundColor(getResources().getColor(R.color.themecolor));
        llMainGeren.setBackgroundColor(getResources().getColor(R.color.themecolor));
    }

    public void addFrament(Fragment fragm,String tag){

        FragmentTransaction transaction=getFragmentManager().beginTransaction();
//        ll.setBackgroundColor(getResources().getColor(R.color.clickColor));
        getFragmentManager().popBackStack();
        Log.e("数量", getFragmentManager().getBackStackEntryCount()+"");
        transaction.add(R.id.fl_main,fragm,tag);
        transaction.addToBackStack(null);//将fragment加入返回栈
        transaction.commit();
    }

}
