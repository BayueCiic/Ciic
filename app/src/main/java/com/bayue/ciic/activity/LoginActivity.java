package com.bayue.ciic.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bayue.ciic.App;
import com.bayue.ciic.MainActivity;
import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.base.BaseLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                startActivity(new Intent(this, MainActivity.class));
                finish();
                App.bgActivity.finish();

                break;
            case R.id.tv_login_register:
                finish();
                startActivity(new Intent(LoginActivity.this,LoginRegisterActivity.class));
                break;
        }
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
