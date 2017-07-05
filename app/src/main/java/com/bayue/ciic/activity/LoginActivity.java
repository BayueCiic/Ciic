package com.bayue.ciic.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bayue.ciic.App;
import com.bayue.ciic.MainActivity;
import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.base.BaseLoginActivity;
import com.bayue.ciic.bean.RegBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.DensityUtil;
import com.bayue.ciic.utils.HTTPUtils;
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

public class LoginActivity extends BaseLoginActivity {
    @BindView(R.id.et_login_account)
    EditText etLoginAccount;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.cbox_login_memoryPassword)
    CheckBox cboxLoginMemoryPassword;
    @BindView(R.id.tv_login_forgetPassword)
    TextView tvLoginForgetPassword;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    protected int activityCloseEnterAnimation;

    protected int activityCloseExitAnimation;
    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        cboxLoginMemoryPassword.setVisibility(View.GONE);
        /*if(!Preferences.getUserName().isEmpty()&&!Preferences.getPassword().isEmpty()){

        }
        etLoginAccount.setText(Preferences.getUserName());
        etLoginPassword.setText(Preferences.getPassword());
        cboxLoginMemoryPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Preferences.saveUserName(etLoginAccount.getText().toString());
                    Preferences.savePassword(etLoginPassword.getText().toString());
                }else {
                    Preferences.saveUserName("");
                    Preferences.savePassword("");
                }
            }
        });*/
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_login_forgetPassword, R.id.bt_login, R.id.tv_login_register})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_login_forgetPassword:
                finish();
                startActivity(new Intent(LoginActivity.this,LoginForgetActivity.class));
                break;
            case R.id.bt_login:
                Log.e("登录","+++++++++");
//                startActivity(new Intent(this, MainActivity.class));
//                finish();
//                App.bgActivity.finish();
                login();

                break;
            case R.id.tv_login_register:
                finish();
                startActivity(new Intent(LoginActivity.this,LoginRegisterActivity.class));
                break;
        }
    }
    String phone;
    private void login(){
        phone=etLoginAccount.getText().toString();
//        String verification=etCommonVerification.getText().toString();
        final String password=etLoginPassword.getText().toString();
//        String password2=etCommonPassword2.getText().toString();

        if(phone.length()!=11){
            Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
            return;
        }
//        if(verification.isEmpty()){
//            Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
//            return;
//        }
        if(password.isEmpty()){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }
//        if(password.equals(password2)){
//            Toast.makeText(this,"两次密码不一致",Toast.LENGTH_SHORT).show();
//            return;
//        }
        Map<String ,Object> map=new HashMap<>();

        map.put("phone",phone);
        map.put("password",password);
        HTTPUtils.getNetDATA(API.BaseUrl + API.Login.LOGIN, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

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
                                Preferences.saveAdmin(bean.getIs_admin());
                                Preferences.saveUserName(etLoginAccount.getText().toString());
                                Preferences.savePassword(etLoginPassword.getText().toString());
                                Log.e("token",bean.getToken());
                                DensityUtil.showToast(LoginActivity.this,bean.getData());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                LoginActivity.this.finish();
                                App.bgActivity.finish();
                            }else {
                                DensityUtil.showToast(LoginActivity.this,bean.getMsg());
                            }
                        }
                    });
                }else {
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            DensityUtil.showToast(LoginActivity.this,response.message());
                        }
                    });
                }
            }
        });



    }

    /*private void initAnim() {
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});

        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);

        activityStyle.recycle();

        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});

        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);

        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);

        activityStyle.recycle();
    }*/

}
