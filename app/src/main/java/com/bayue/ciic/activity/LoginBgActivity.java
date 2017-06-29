package com.bayue.ciic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bayue.ciic.App;
import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/26.
 */

public class LoginBgActivity extends BaseActivity {


    @BindView(R.id.butten)
    Button butten;
    @BindView(R.id.ll)
    LinearLayout ll;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_login_bg;
    }

    @Override
    protected void initViews() {
        App.bgActivity = this;
        Log.e("到了吗？？", "============");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*Intent mainIntent = new Intent(LoginActivity.this,   AndroidNews.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();*/
                startActivity(new Intent(LoginBgActivity.this, LoginActivity.class));
//                overridePendingTransition(R.animator.slide_left_in,0);
            }
        }, 10);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.butten)
    public void onViewClicked() {


    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.animator.slide_left_in,R.animator.slide_right_out);

    }
}
