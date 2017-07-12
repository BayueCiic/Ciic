package com.bayue.ciic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bayue.ciic.App;
import com.bayue.ciic.MainActivity;
import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseLoginActivity;
import com.bayue.ciic.bean.RegBean;
import com.bayue.ciic.bean.VerificationBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.DensityUtil;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/26.
 */

public class LoginRegisterCompanyActivity extends BaseLoginActivity {
    @BindView(R.id.et_company_mail)
    EditText etCompanyMail;
    @BindView(R.id.et_company_yanzheng)
    EditText etCompanyYanzheng;
    @BindView(R.id.tv_company_send)
    TextView tvCompanySend;
    @BindView(R.id.et_company_name)
    EditText etCompanyName;
    @BindView(R.id.et_company_shorname)
    EditText etCompanyShorname;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.et_company_address)
    EditText etCompanyAddress;
    @BindView(R.id.et_company_prassword)
    EditText etCompanyPrassword;
    @BindView(R.id.et_company_prassword2)
    EditText etCompanyPrassword2;
    @BindView(R.id.but_company_zhuce)
    Button butCompanyZhuce;
    @BindView(R.id.et_company_backlogin)
    TextView etCompanyBacklogin;

    String mail;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_login_company;
    }

    @Override
    protected void initViews() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_company_send, R.id.but_company_zhuce, R.id.et_company_backlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_company_send:
                send();
                break;
            case R.id.but_company_zhuce:
                reg();
                break;
            case R.id.et_company_backlogin:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            tvCompanySend.setText(msg.obj.toString()+"秒后重发");

            if((int)msg.obj==0){
                tvCompanySend.setEnabled(true);
                tvCompanySend.setText("发送验证码");
            }
        }
    };
    private void sendTag(){
        tvCompanySend.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 5; i >-1; i--) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message m=new Message();
                    m.obj=i;
                    handler.sendMessage(m);
                }
            }
        }).start();
    }
    private void send(){
        mail=etCompanyMail.getText().toString();
        if(mail.isEmpty()||mail==null){
            ToastUtils.showShortToast("请输入邮箱");
            return;
        }
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher m = p.matcher(mail);
        //Mather m = p.matcher("wangxu198709@gmail.com.cn");这种也是可以的！
        boolean b = m.matches();

        if(!b){
            ToastUtils.showShortToast("请输入正确的邮箱");
            return;
        }
        Map<String ,Object> map=new HashMap();
          map.put("to",mail);
        sendTag();
        HTTPUtils.getNetDATA(API.BaseUrl + API.Login.MAIL, map, new Callback() {
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
                String msg=response.body().string();
                if(response.code()==200){
                    Gson gson=new Gson();
                    final VerificationBean bean=gson.fromJson(msg,VerificationBean.class);
                    if(bean.getCode()==200){
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                ToastUtils.showShortToast(bean.getData());
                            }
                        });
                    }
                }else {

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

    private void reg(){
        mail=etCompanyMail.getText().toString();
        String verification=etCompanyYanzheng.getText().toString();
        String name=etCompanyName.getText().toString();
        String shorName=etCompanyShorname.getText().toString();
        String address=etCompanyAddress.getText().toString();
        String password=etCompanyPrassword.getText().toString();
        String password2=etCompanyPrassword2.getText().toString();

        if(mail.isEmpty()||mail==null){
            ToastUtils.showShortToast("请输入邮箱");
            return;
        }
        if(verification.isEmpty()||verification==null){
            ToastUtils.showShortToast("请输入验证码");
            return;
        }
        if(password.isEmpty()||password==null){
            ToastUtils.showShortToast("请输入密码");
            return;
        }
        if(!password.equals(password2)){
            ToastUtils.showShortToast("密码不一致");
            return;
        }
        if(name.isEmpty()||name==null){
            ToastUtils.showShortToast("请输入公司名称");
            return;
        }
        if(shorName.isEmpty()||shorName==null){
            ToastUtils.showShortToast("请输入公司简称");
            return;
        }
        if(address.isEmpty()||address==null){
            ToastUtils.showShortToast("请输入公司地址");
            return;
        }
        Map<String ,Object> map=new HashMap<>();

        map.put("email",mail);
        map.put("code",verification);
        map.put("name",name);
        map.put("short_name",shorName);
        map.put("address",address);
        map.put("password",password);
        HTTPUtils.getNetDATA(API.BaseUrl + API.Login.EMAIL_REG, map, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("请检查网络");                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg=response.body().string();
                if(response.code()==200){
                    Gson gson=new Gson();
                    final RegBean bean=gson.fromJson(msg,RegBean.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if(bean.getCode()==200){
                                Preferences.saveString(getApplicationContext(),Preferences.TOKEN,bean.getToken());
                                ToastUtils.showShortToast(bean.getData());
                                startActivity(new Intent(LoginRegisterCompanyActivity.this, MainActivity.class));
                                finish();
                                App.bgActivity.finish();
                            }else {
                                ToastUtils.showShortToast(bean.getMsg());                             }
                        }
                    });
                }else {
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
}
