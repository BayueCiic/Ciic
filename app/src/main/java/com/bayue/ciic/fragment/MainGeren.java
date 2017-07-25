package com.bayue.ciic.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.ciic.MainActivity;
import com.bayue.ciic.R;
import com.bayue.ciic.activity.GerenCanyu;
import com.bayue.ciic.activity.GerenComplaint;
import com.bayue.ciic.activity.GerenGuanyu;
import com.bayue.ciic.activity.GerenNews;
import com.bayue.ciic.activity.GerenParty;
import com.bayue.ciic.activity.GerenQiyeShezhi;
import com.bayue.ciic.activity.GerenShezhi;
import com.bayue.ciic.activity.GerenShipin;
import com.bayue.ciic.activity.GerenShoucang;
import com.bayue.ciic.activity.GerenWonderful;
import com.bayue.ciic.activity.GerenXiugai;
import com.bayue.ciic.activity.GerenXx;
import com.bayue.ciic.activity.GerenZhibo;
import com.bayue.ciic.activity.Gerenlianxi;
import com.bayue.ciic.activity.LiveWatch;
import com.bayue.ciic.activity.LoginActivity;
import com.bayue.ciic.activity.LoginBgActivity;
import com.bayue.ciic.base.BaseFragment;
import com.bayue.ciic.bean.DetailsBean;
import com.bayue.ciic.bean.GerenBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.bayue.ciic.utils.glide.GlideCircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/27.
 */

public class MainGeren extends BaseFragment {
    @BindView(R.id.fl_shezhi)
    FrameLayout flShezhi;
    @BindView(R.id.iv_geren_toux)
    ImageView ivGerenToux;
    @BindView(R.id.tv_geren_toux)
    TextView tvGerenToux;
    @BindView(R.id.tv_geren_companyname)
    TextView tvGerenCompanyname;
    @BindView(R.id.tv_geren_mininame)
    TextView tvGerenMininame;
    @BindView(R.id.ll_geren_huodong)
    LinearLayout llGerenHuodong;
    @BindView(R.id.ll_geren_xinwen)
    LinearLayout llGerenXinwen;
    @BindView(R.id.ll_geren_shipin)
    LinearLayout llGerenShipin;
    @BindView(R.id.ll_geren_zhibo)
    LinearLayout llGerenZhibo;
    @BindView(R.id.ll_geren_jingcai)
    LinearLayout llGerenJingcai;
    @BindView(R.id.ll_geren_canyu)
    LinearLayout llGerenCanyu;
    @BindView(R.id.ll_geren_shoucang)
    LinearLayout llGerenShoucang;
    @BindView(R.id.ll_geren_tousu)
    LinearLayout llGerenTousu;
    @BindView(R.id.rl_geren_xx)
    RelativeLayout rlGerenXx;
    @BindView(R.id.rl_geren_guanyu)
    RelativeLayout rlGerenGuanyu;
    @BindView(R.id.rl_geren_lianxi)
    RelativeLayout rlGerenLianxi;
    @BindView(R.id.rl_geren_xiugai)
    RelativeLayout rlGerenXiugai;
    @BindView(R.id.rl_geren_tuichu)
    RelativeLayout rlGerenTuichu;
    Unbinder unbinder;
    MainActivity main;
    @BindView(R.id.tv_titletxt)
    TextView tvTitletxt;
    boolean b=false;

    String img="",name="",company="",shortname="",companyid="";


    RequestManager glideRequest;
    @Override
    protected int getViewId() {
        return R.layout.frament_main_geren;
    }

    @Override
    public void init() {
        main = (MainActivity) getActivity();
//        hide();
        glideRequest= Glide.with(main);
        tvTitletxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b){
                    hide();
                    b=false;
                }else {
                    show();
                    b=true;
                }
            }
        });

       if(Preferences.getEnterprise_id().equals("1")&&Preferences.getAdmin().equals("1")){

       }else if((!Preferences.getEnterprise_id().equals("1"))&&(Preferences.getAdmin().equals("1")||Preferences.getAdmin().equals("2"))){
           hide3();
       }else {
           hide();
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fl_shezhi, R.id.iv_geren_toux, R.id.ll_geren_huodong, R.id.ll_geren_xinwen, R.id.ll_geren_shipin, R.id.ll_geren_zhibo, R.id.ll_geren_jingcai, R.id.ll_geren_canyu, R.id.ll_geren_shoucang, R.id.ll_geren_tousu, R.id.rl_geren_xx, R.id.rl_geren_guanyu, R.id.rl_geren_lianxi, R.id.rl_geren_xiugai, R.id.rl_geren_tuichu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_shezhi:
                Intent intent;
                if(Preferences.getAdmin().equals("2")){
                    intent=new Intent(main, GerenQiyeShezhi.class);

                }else {
                    intent=new Intent(main, GerenShezhi.class);
                }
                intent.putExtra("img",img);
                intent.putExtra("name",name);
                intent.putExtra("company",company);
                intent.putExtra("shortname",shortname);
                intent.putExtra("companyid",companyid);
                Log.e("船公司==id=",companyid+"");
                main.startActivity(intent);
                break;
            case R.id.iv_geren_toux:
                break;
            case R.id.ll_geren_huodong:
                main.startActivity(new Intent(main, GerenParty.class));
                break;
            case R.id.ll_geren_xinwen:
                main.startActivity(new Intent(main, GerenNews.class));
                break;
            case R.id.ll_geren_shipin:
                main.startActivity(new Intent(main, GerenShipin.class));
                break;
            case R.id.ll_geren_zhibo:
                main.startActivity(new Intent(main, GerenZhibo.class));
                break;
            case R.id.ll_geren_jingcai:
                main.startActivity(new Intent(main, GerenWonderful.class));
                break;
            case R.id.ll_geren_canyu:
                main.startActivity(new Intent(main, GerenCanyu.class));
                break;
            case R.id.ll_geren_shoucang:
                main.startActivity(new Intent(main, GerenShoucang.class));
                break;
            case R.id.ll_geren_tousu:
                main.startActivity(new Intent(main, GerenComplaint.class));
                break;
            case R.id.rl_geren_xx:
                main.startActivity(new Intent(main, GerenXx.class));
                break;
            case R.id.rl_geren_guanyu:
                main.startActivity(new Intent(main, GerenGuanyu.class));
                break;
            case R.id.rl_geren_lianxi:
                main.startActivity(new Intent(main, Gerenlianxi.class));
                break;
            case R.id.rl_geren_xiugai:
                main.startActivity(new Intent(main, GerenXiugai.class));
                break;
            case R.id.rl_geren_tuichu:
                exit();
//                main.startActivity(new Intent(main, LiveWatch.class));
                break;
        }
    }

    private void show() {

        llGerenHuodong.setVisibility(View.VISIBLE);
        llGerenXinwen.setVisibility(View.VISIBLE);
        llGerenShipin.setVisibility(View.VISIBLE);
        llGerenZhibo.setVisibility(View.VISIBLE);
        llGerenJingcai.setVisibility(View.VISIBLE);

    }

    private void hide() {

        llGerenHuodong.setVisibility(View.GONE);
        llGerenXinwen.setVisibility(View.GONE);
        llGerenShipin.setVisibility(View.GONE);
        llGerenZhibo.setVisibility(View.GONE);
        llGerenJingcai.setVisibility(View.GONE);

    }

    private void hide3(){
        llGerenXinwen.setVisibility(View.GONE);
        llGerenZhibo.setVisibility(View.GONE);
        llGerenJingcai.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", Preferences.getString(main, Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.user.GEREN, map, new Callback() {
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
                Log.e("msg========",msg);
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final GerenBean bean = gson.fromJson(msg, GerenBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                if(bean.getData()!=null){
                                    if(bean.getData()==null){
                                        return;
                                    }
                                    Log.e("uri========",bean+"");
                                    Log.e("个人图片==url=",bean.getData().getUseravatar());
                                    img=bean.getData().getUseravatar();
                                    name=bean.getData().getUsername();
                                    company=bean.getData().getEnterpriseName();
                                    shortname=bean.getData().getEnterpriseShortName();
                                    companyid=bean.getData().getEnterprise_id();

                                    Log.e("个人公司==id=",companyid+"");
                                    if(ivGerenToux!=null){
                                        glideRequest.load(bean.getData().getUseravatar())
                                                .placeholder(R.mipmap.bianjiziliao_toux2_3x)
                                                .error(R.mipmap.bianjiziliao_toux2_3x)
                                                .transform(new GlideCircleTransform(main))
                                                .into(ivGerenToux);
                                        tvGerenToux.setText(shortname+"-"+name);
                                        tvGerenCompanyname.setText(company);
                                        tvGerenMininame.setText(shortname);
                                    }

                                }
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

    private void exit(){
        LayoutInflater inflaterDl = LayoutInflater.from(main);
        LinearLayout layout = (LinearLayout)inflaterDl.inflate(R.layout.dialog_exit, null );

        //对话框
        final Dialog dialog = new AlertDialog.Builder(main).create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes() ;
        Display display =main.getWindowManager().getDefaultDisplay();
        params.width =(int) (display.getWidth()*0.8);

        //使用这种方式更改了dialog的框宽
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setContentView(layout);

        layout.findViewById(R.id.but_dailog_quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        layout.findViewById(R.id.but_dailog_queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.saveString(getContext(),Preferences.TOKEN,"-1");
                dialog.dismiss();
                main.startActivity(new Intent(main, LoginBgActivity.class));
                main.finish();
            }
        });


    }
}
