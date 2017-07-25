package com.bayue.ciic;




import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bayue.ciic.activity.LoginBgActivity;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.bean.TagBean;
import com.bayue.ciic.fragment.MainCompany;
import com.bayue.ciic.fragment.MainGeren;
import com.bayue.ciic.fragment.MainPlatfrom;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

    public  static List<TagBean.DataBean> lists;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(){
        App.mainActivity=this;
        getTagData();
        platfrom=new MainPlatfrom();
        company=new MainCompany();
        geren=new MainGeren();
        setFrament(platfrom,llMainPlatform);

        Log.e("Token======", Preferences.getString(this,Preferences.TOKEN));


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

                if(Preferences.getEnterprise_id().equals("0")){
                    remind();
                    return;

                }

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
    private void setFrament(Fragment fragm, LinearLayout ll){
        restoreColor();

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        ll.setBackgroundColor(getResources().getColor(R.color.clickColor));
        getFragmentManager().popBackStack();
//        Log.e("数量yyyyyyy", getFragmentManager().getBackStackEntryCount()+"");
       /* if(platfrom!=null)
        transaction.hide(platfrom);
        if(company!=null)
        transaction.hide(company);
        if(geren!=null)
        transaction.hide(geren);


            transaction.add(R.id.fl_main,fragm);
            Log.e("","添加--------");

            transaction.show(fragm);
*/
        transaction.replace(R.id.fl_main,fragm);
//        transaction.addToBackStack(null);//将fragment加入返回栈
        transaction.commit();
    }
   /* public void switchContent(Fragment from, Fragment to, int position) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = fm.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.content_frame, to, tags[position]).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }*/

    private void restoreColor(){

        llMainPlatform.setBackgroundColor(getResources().getColor(R.color.themecolor));
        llMainCompany.setBackgroundColor(getResources().getColor(R.color.themecolor));
        llMainGeren.setBackgroundColor(getResources().getColor(R.color.themecolor));
    }

    public void addFrament(Fragment fragm,String tag){

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
//      ll.setBackgroundColor(getResources().getColor(R.color.clickColor));
        getFragmentManager().popBackStack();
        Log.e("数量", getFragmentManager().getBackStackEntryCount()+"");
        transaction.add(R.id.fl_main,fragm,tag);
        transaction.addToBackStack(null);//将fragment加入返回栈
        transaction.commit();
    }
    //改写物理按键——返回的逻辑
    private long firstTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (mWebView.canGoBack()) {
//                mWebView.goBack();//返回上一页面
//                return true;
//            } else {
//                System.exit(0);//退出程序
//            }
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;//更新firstTime
                return true;
            } else {
                //两次按键小于2秒时，退出应用
//                App.bgActivity.finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void remind(){
        LayoutInflater inflaterDl = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout)inflaterDl.inflate(R.layout.dialog_remind, null );

        //对话框
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes() ;
        Display display =this.getWindowManager().getDefaultDisplay();
        params.width =(int) (display.getWidth()*0.8);

        //使用这种方式更改了dialog的框宽
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setContentView(layout);

        layout.findViewById(R.id.but_dialog_queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    //获取数据类型
    private void getTagData() {
        Map<String, Object> map = new HashMap<>();

//        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.TAG, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final TagBean bean = gson.fromJson(msg, TagBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                 lists = bean.getData();

                                if(setTag!=null)
                                setTag.setTag(bean.getData());

                                Log.e("TTTTTT", lists.get(0).getName() + lists.get(1).getName() + lists.get(2).getName());

                            }
                        });
                    } else {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
//                                ToastUtils.showShortToast(bean.getMsg());
                            }
                        });
                    }
                } else {
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showShortToast(response.message());
                        }
                    });
                }
            }
        });
    }

    SetTag setTag;

    public interface SetTag{
        void setTag(List<TagBean.DataBean> lists);

    }

    public void setTag(SetTag setTag){
        this.setTag=setTag;
    }
}
