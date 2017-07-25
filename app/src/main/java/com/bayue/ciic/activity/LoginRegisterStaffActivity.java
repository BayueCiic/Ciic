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

public class LoginRegisterStaffActivity extends BaseLoginActivity {
    @BindView(R.id.et_staff_phone)
    EditText etStaffPhone;
    @BindView(R.id.et_staff_yanzheng)
    EditText etStaffYanzheng;
    @BindView(R.id.tv_staff_send)
    TextView tvStaffSend;
    @BindView(R.id.et_staff_prassword)
    EditText etStaffPrassword;
    @BindView(R.id.et_staff_id)
    EditText etStaffId;
    @BindView(R.id.et_staff_link)
    EditText etStaffLink;
    @BindView(R.id.but_staff_reg)
    Button butStaffReg;
    @BindView(R.id.tv_staff_backlogin)
    TextView tvStaffBacklogin;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_login_staff;
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

    @OnClick({R.id.tv_staff_send, R.id.but_staff_reg, R.id.tv_staff_backlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_staff_send:
                sendTag();
                break;
            case R.id.but_staff_reg:
                break;
            case R.id.tv_staff_backlogin:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            tvStaffSend.setText(msg.obj.toString()+"秒后重发");

            if((int)msg.obj==0){
                tvStaffSend.setEnabled(true);
                tvStaffSend.setText("发送验证码");
            }
        }
    };
    String phone;
    private void send(){
        tvStaffSend.setEnabled(false);
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
        phone=etStaffPhone.getText().toString();
        if(phone.length()!=11){
            Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
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
                        ToastUtils.showShortToast("请检查网络");
                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.e("^^^^^^^^^^^^","2222222222222");
                String msg=response.body().string();
                if(response.code()==200){
                    Gson gson=new Gson();
                    final RegBean bean=gson.fromJson(msg,RegBean.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if(bean.getCode()==200){
                                Preferences.saveString(getApplicationContext(),Preferences.TOKEN,bean.getToken());
                                Preferences.saveAdmin(bean.getIs_admin()+"");
                                Preferences.saveEnterprise_id(bean.getEnterprise_id());

                            }else {
                                ToastUtils.showShortToast(bean.getData());
                            }
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
