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

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseLoginActivity;
import com.bayue.ciic.http.API;
import com.bayue.ciic.utils.HTTPUtils;
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

public class LoginRegisterCommonActivity extends BaseLoginActivity {
    @BindView(R.id.et_common_phone)
    EditText etCommonPhone;
    @BindView(R.id.et_common_verification)
    EditText etCommonVerification;
    @BindView(R.id.tv_common_send)
    TextView tvCommonSend;
    @BindView(R.id.et_common_password)
    EditText etCommonPassword;
    @BindView(R.id.et_common_password2)
    EditText etCommonPassword2;
    @BindView(R.id.but_common_zhuce)
    Button butCommonZhuce;
    @BindView(R.id.et_common_backlogin)
    TextView etCommonBacklogin;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_login_common;
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

    @OnClick({R.id.tv_common_send, R.id.but_common_zhuce, R.id.et_common_backlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_common_send:
                send();
                break;
            case R.id.but_common_zhuce:

                break;
            case R.id.et_common_backlogin:
                startActivity(new Intent(this,LoginActivity.class));
                overridePendingTransition(R.animator.slide_left_out, R.animator.slide_right_in);
                finish();
                break;
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            tvCommonSend.setText(msg.obj.toString()+"秒后重发");

            if((int)msg.obj==0){
                tvCommonSend.setEnabled(true);
                tvCommonSend.setText("发送验证码");
            }
        }
    };
    private void send(){
        tvCommonSend.setEnabled(false);
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
    String phone;
    private void reg(){
        phone=etCommonPhone.getText().toString();
        String verification=etCommonVerification.getText().toString();
        String password=etCommonPassword.getText().toString();
        String password2=etCommonPassword2.getText().toString();

        if(phone.length()!=11){
            Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
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
        if(password.equals(password2)){
            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String ,Object> map=new HashMap<>();

        map.put("phone",phone);
        map.put("password",password);
        HTTPUtils.getNetDATA(API.BaseUrl + API.Login.REG, map, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String msg=response.body().string();
                if(response.code()==200){
                    Gson gson=new Gson();

                }

            }
        });



    }
    private void sendTag(){
        if(phone.length()!=11){
            Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String ,Object> map=new HashMap<>();

        map.put("phone",phone);

        HTTPUtils.getNetDATA(API.BaseUrl + API.Login.VALI, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });







    }
}
