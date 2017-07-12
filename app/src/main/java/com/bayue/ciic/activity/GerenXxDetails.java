package com.bayue.ciic.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.bean.CompanyBean;
import com.bayue.ciic.bean.DetailsBean;
import com.bayue.ciic.bean.VerificationBean;
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

/**
 * Created by Administrator on 2017/7/5.
 */

public class GerenXxDetails extends BaseActivity {
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
    @BindView(R.id.tv_details_name)
    TextView tvDetailsName;
    @BindView(R.id.tv_details_time)
    TextView tvDetailsTime;
    @BindView(R.id.tv_details)
    TextView tvDetails;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_geren_details;
    }

    @Override
    protected void initViews() {
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("活动管理");
        ivShezhi.setVisibility(View.INVISIBLE);
        tvShezhi.setText("删除");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fl_goback, R.id.fl_shezhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_goback:
                finish();
                break;
            case R.id.fl_shezhi:
                delDialog();
                break;
        }
    }
    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("id",getIntent().getStringExtra("id"));
        map.put("token", Preferences.getString(this, Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.user.DETAILS, map, new Callback() {
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
                    final DetailsBean bean = gson.fromJson(msg, DetailsBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                          tvDetailsName.setText(bean.getData().getTitle());
                          tvDetailsTime.setText(bean.getData().getCreate_time());
                          tvDetails.setText("      "+bean.getData().getMessage_content());

                            }
                        });
                    } else {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showShortToast(bean.getMsg());
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

    private void delItem() {


        Map<String, Object> map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        map.put("token", Preferences.getString(this, Preferences.TOKEN));

        HTTPUtils.getNetDATA(API.BaseUrl + API.user.DEL_XX, map, new Callback() {
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
                    final VerificationBean bean = gson.fromJson(msg, VerificationBean.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (bean.getCode() == 200) {
                                ToastUtils.showShortToast(bean.getData());
                                finish();
                            } else {
                                ToastUtils.showShortToast(bean.getMsg());
                            }
                        }
                    });
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

    private void delDialog(){
        LayoutInflater inflaterDl = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout)inflaterDl.inflate(R.layout.dialog_exit, null );

        //对话框
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes() ;
        Display display =this.getWindowManager().getDefaultDisplay();
        params.width =(int) (display.getWidth()*0.8);

        //使用这种方式更改了dialog的框宽
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setContentView(layout);

        TextView title= (TextView) layout.findViewById(R.id.tv_dialog_title);
        title.setText("确定要删除？");
        layout.findViewById(R.id.but_dailog_quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        layout.findViewById(R.id.but_dailog_queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delItem();
                dialog.dismiss();
            }
        });


    }
}
