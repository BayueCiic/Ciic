package com.bayue.ciic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.base.BaseLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/26.
 */

public class LoginRegisterActivity extends BaseLoginActivity {
    @BindView(R.id.iv_registered_company)
    ImageView ivRegisteredCompany;
    @BindView(R.id.tv_registered_company)
    TextView tvRegisteredCompany;
    @BindView(R.id.ll_registered_company)
    LinearLayout llRegisteredCompany;
    @BindView(R.id.iv_registered_staff)
    ImageView ivRegisteredStaff;
    @BindView(R.id.tv_registered_staff)
    TextView tvRegisteredStaff;
    @BindView(R.id.ll_registered_staff)
    LinearLayout llRegisteredStaff;
    @BindView(R.id.iv_registered_common)
    ImageView ivRegisteredCommon;
    @BindView(R.id.tv_registered_common)
    TextView tvRegisteredCommon;
    @BindView(R.id.ll_registered_common)
    LinearLayout llRegisteredCommon;
    @BindView(R.id.but_registered_next)
    Button butRegisteredNext;
    @BindView(R.id.tv_registered_backlogin)
    TextView tvRegisteredBacklogin;

    int i=0;
    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_login_registered;
    }

    @Override
    protected void initViews() {
           /* butRegisteredNext.setClickable(false);
        butRegisteredNext.setFocusable(false);*/
        butRegisteredNext.setEnabled(false);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_registered_company, R.id.ll_registered_staff, R.id.ll_registered_common, R.id.but_registered_next, R.id.tv_registered_backlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_registered_company:
                restoreView();
                i=1;
                ivRegisteredCompany.setImageResource(R.mipmap.zhuce_qiye023x);
                tvRegisteredCompany.setTextColor(getResources().getColor(R.color.themecolor));

                break;
            case R.id.ll_registered_staff:
                restoreView();
                i=2;
                ivRegisteredStaff.setImageResource(R.mipmap.zhucezhiyuan023x);
                tvRegisteredStaff.setTextColor(getResources().getColor(R.color.themecolor));

                break;
            case R.id.ll_registered_common:
                restoreView();
                i=3;
                ivRegisteredCommon.setImageResource(R.mipmap.zhuce_yonghu023x);
                tvRegisteredCommon.setTextColor(getResources().getColor(R.color.themecolor));

                break;
            case R.id.but_registered_next:
                switch (i){
                    case 1:
                        startActivity(new Intent(this,LoginRegisterCompanyActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(this,LoginRegisterStaffActivity.class));

                        break;
                    case 3:
                        startActivity(new Intent(this,LoginRegisterCommonActivity.class));

                        break;
                    case 5:
                        return;
                }

                Log.e("可以点击吗？","------------------");
                finish();
                break;
            case R.id.tv_registered_backlogin:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
        }
    }
    private void restoreView(){
        butRegisteredNext.setBackgroundColor(getResources().getColor(R.color.themecolor));
        butRegisteredNext.setEnabled(true);

        ivRegisteredCompany.setImageResource(R.mipmap.zhuce_qiye3x);
        ivRegisteredStaff.setImageResource(R.mipmap.zhuce_zhiyuan3x);
        ivRegisteredCommon.setImageResource(R.mipmap.zhuce_yonghu3x);
        tvRegisteredCompany.setTextColor(getResources().getColor(R.color.registeredButtenColor));
        tvRegisteredStaff.setTextColor(getResources().getColor(R.color.registeredButtenColor));
        tvRegisteredCommon.setTextColor(getResources().getColor(R.color.registeredButtenColor));
    }
}
