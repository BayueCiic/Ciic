package com.bayue.ciic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.ciic.App;
import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
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
 * Created by Administrator on 2017/6/28.
 */

public class GerenXiugai extends BaseActivity {

    @BindView(R.id.iv_goback)
    ImageView ivGoback;
    @BindView(R.id.fl_goback)
    FrameLayout flGoback;
    @BindView(R.id.tv_titletxt)
    TextView tvTitletxt;
    @BindView(R.id.iv_shezhi)
    ImageView ivShezhi;
    @BindView(R.id.tv_shezhi)
    TextView tvShezhi;
    @BindView(R.id.fl_shezhi)
    FrameLayout flShezhi;
    @BindView(R.id.et_xiugai_old)
    EditText etXiugaiOld;
    @BindView(R.id.et_xiugai_new1)
    EditText etXiugaiNew1;
    @BindView(R.id.et_xiugai_new2)
    EditText etXiugaiNew2;
    @BindView(R.id.but_xiugai_tijiao)
    Button butXiugaiTijiao;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_geren_xiugai;
    }

    @Override
    protected void initViews() {
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("修改密码");
        ivShezhi.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fl_goback, R.id.but_xiugai_tijiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_goback:
                finish();
                break;
            case R.id.but_xiugai_tijiao:
                tiJiao();
                break;
        }
    }
    private void tiJiao(){

        String old=etXiugaiOld.getText().toString();
        String new1=etXiugaiNew1.getText().toString();
        String new2=etXiugaiNew2.getText().toString();
        if(old.isEmpty()){
            ToastUtils.showShortToast("请输入原来的密码");
            return;
        }
        if(new1.isEmpty()){
            ToastUtils.showShortToast("请输入新的密码");
            return;
        }
        if(!new1.equals(new2)){
            ToastUtils.showShortToast("新密码不一致性");
            return;
        }

        Map<String ,Object> map=new HashMap<>();
        map.put("oldpassword",old);
//        Log.e("oldpassword===",old);
        map.put("newpassword",new1);
//        Log.e("newpassword===",new1);
        map.put("password_two",new2);
//        Log.e("password_two===",new2);

        map.put("token", Preferences.getString(this,Preferences.TOKEN));

        HTTPUtils.getNetDATA(API.BaseUrl + API.user.AMEND, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        DensityUtil.showToast(GerenXiugai.this,"请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg=response.body().string();
                if(response.code()==200){
                    Gson gson=new Gson();
                    final VerificationBean bean=gson.fromJson(msg,VerificationBean.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if(bean.getCode()==200){
                                ToastUtils.showShortToast(bean.getData());
                                Preferences.saveString(getApplicationContext(),Preferences.TOKEN,"-1");
                                startActivity(new Intent(GerenXiugai.this,LoginBgActivity.class));
                                finish();
                                App.mainActivity.finish();
                            }else {
                                ToastUtils.showShortToast(bean.getMsg());
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
