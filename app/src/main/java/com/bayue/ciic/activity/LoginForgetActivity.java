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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/26.
 */

public class LoginForgetActivity extends BaseLoginActivity {
    @BindView(R.id.et_forget_phone)
    EditText etForgetPhone;
    @BindView(R.id.et_forget_yzm)
    EditText etForgetYzm;
    @BindView(R.id.tv_forget_send)
    TextView tvForgetSend;
    @BindView(R.id.et_forget_password)
    EditText etForgetPassword;
    @BindView(R.id.et_forget_password2)
    EditText etForgetPassword2;
    @BindView(R.id.but_forget_complete)
    Button butForgetComplete;
    @BindView(R.id.tv_forget_backlogin)
    TextView tvForgetBacklogin;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_login_forget;
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

    @OnClick({R.id.tv_forget_send, R.id.but_forget_complete, R.id.tv_forget_backlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_send:
                sendTag();
                break;
            case R.id.but_forget_complete:
                comp();
                break;
            case R.id.tv_forget_backlogin:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            tvForgetSend.setText(msg.obj.toString()+"秒后重发");

            if((int)msg.obj==0){
                tvForgetSend.setEnabled(true);
                tvForgetSend.setText("发送验证码");
            }
        }
    };
    private String phone;
    private void send(){
        tvForgetSend.setEnabled(false);
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
    private void sendTag(){
        phone=etForgetPhone.getText().toString();
        if(phone.length()!=11){
//            Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
            ToastUtils.showShortToast("请输入正确的帐号1111111111111");
            return;
        }
        Map<String ,Object> map=new HashMap<>();

        map.put("phone",phone);
        send();
        HTTPUtils.getNetDATA(API.BaseUrl + API.Login.VALI, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        DensityUtil.showToast(LoginForgetActivity.this,"请检查网络");
                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.e("^^^^^^^^^^^^","2222222222222");
                String msg=response.body().string();
                if(response.code()==200){
                    Gson gson=new Gson();
                    final VerificationBean bean=gson.fromJson(msg,VerificationBean.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if(bean.getCode()==200){
                                etForgetYzm.setText(bean.getData());
                            }else {
                                DensityUtil.showToast(LoginForgetActivity.this,bean.getMsg());
                            }
                        }
                    });
                }else {
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            DensityUtil.showToast(LoginForgetActivity.this,response.message());
                        }
                    });



                }


            }
        });







    }
    private void comp(){
        phone=etForgetPhone.getText().toString();
        String verification=etForgetYzm.getText().toString();
        String password=etForgetPassword.getText().toString();
        String password2=etForgetPassword2.getText().toString();

        if(phone.length()!=11){
//            DensityUtil.showToast(LoginForgetActivity.this,"请输入正确的帐号");
            ToastUtils.showShortToast("请输入正确的帐号1111111111111");
            return;
        }
        if(verification.isEmpty()){
            Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty()){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }

        boolean b=password.equals(password2);
        Log.e(password+"=="+password2,b+"");
        if(!b){
            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String ,Object> map=new HashMap<>();

        map.put("phone",phone);
        map.put("message",verification);
        map.put("password",password);
        map.put("password_two",password2);
        HTTPUtils.getNetDATA(API.BaseUrl + API.Login.FORGET, map, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        DensityUtil.showToast(LoginForgetActivity.this,"请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg=response.body().string();
                Log.e(">>>>>>>>>>>>>>>>",msg);
                if(response.code()==200){
                    Gson gson=new Gson();
                    final RegBean bean=gson.fromJson(msg,RegBean.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if(bean.getCode()==200){
                                Preferences.saveString(getApplicationContext(),Preferences.TOKEN,bean.getToken());
                                DensityUtil.showToast(LoginForgetActivity.this,bean.getData());
                                startActivity(new Intent(LoginForgetActivity.this, MainActivity.class));
                                LoginForgetActivity.this.finish();
                                App.bgActivity.finish();
                            }else {
                                DensityUtil.showToast(LoginForgetActivity.this,bean.getMsg());
                            }
                        }
                    });
                }else {
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            DensityUtil.showToast(LoginForgetActivity.this,response.message());
                        }
                    });



                }
            }
        });



    }
}
