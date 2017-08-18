package com.bayue.ciic;




import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bayue.ciic.activity.LoginBgActivity;
import com.bayue.ciic.activity.live.activity.LiveRoomActivity;
import com.bayue.ciic.activity.live.activity.VideoPlayerActivity;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.bean.PushBean;
import com.bayue.ciic.bean.TagBean;
import com.bayue.ciic.fragment.MainCompany;
import com.bayue.ciic.fragment.MainGeren;
import com.bayue.ciic.fragment.MainPlatfrom;
import com.bayue.ciic.http.API;
import com.bayue.ciic.liveStreaming.PublishParam;
import com.bayue.ciic.nim.config.perference.UserPreferences;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.server.DemoServerHttpClient;
import com.bayue.ciic.server.entity.RoomInfoEntity;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.google.gson.Gson;
import com.netease.nim.uikit.cache.DataCacheManager;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.tangxiaolv.telegramgallery.Utils.AndroidUtilities.showToast;

public class MainActivity extends BaseActivity {


    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.ll_main_platform)
    LinearLayout llMainPlatform;
    @BindView(R.id.ll_main_company)
    LinearLayout llMainCompany;
    @BindView(R.id.ll_main_geren)
    LinearLayout llMainGeren;


    MainCompany company;
    MainPlatfrom platfrom;
    MainGeren geren;

    public static final String QUALITY_HD = "HD";
    public static final String QUALITY_SD = "SD";
    public static final String QUALITY_LD = "LD";

    private String quality = QUALITY_SD;
//    private String roomId="9140732";
    private String push_url;
    private boolean open_audio = true;
    private boolean open_video = true;
    private boolean useFilter = true; //默认开启滤镜
    private boolean faceBeauty = false; //默认关闭美颜

    private boolean cancelEnterRoom = false;

    public  static List<TagBean.DataBean> lists;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(){
        Log.e("本机IP地址——————", VideoPlayerActivity.getHostIP());
        bPermission=checkPublishPermission();
        App.mainActivity=this;
        getTagData();
        platfrom=MainPlatfrom.getMainPlatform(this);
        company=new MainCompany();
        geren=new MainGeren();
        setFrament(platfrom,llMainPlatform);

//        Log.e("Token======", Preferences.getString(this,Preferences.TOKEN));
        Log.e(Preferences.getAccid()+"===111====", Preferences.getIm_token());
        loginNim(Preferences.getAccid(), Preferences.getIm_token());
    }

    /**   6.0权限处理     **/
    private boolean bPermission = false;
    private final int WRITE_PERMISSION_REQ_CODE = 100;
    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        (String[]) permissions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    public void createLiveRoom(String url,String roomid , boolean b) {

        //检测相机与录音权限
        if(!bPermission){
//            showToast("请先允许app所需要的权限");
            ToastUtils.showShortToast("请先允许app所需要的权限");
            AskForPermission();
            return;
        }

        cancelEnterRoom = false;
//        String url="rtmp://pbb8f2e48.live.126.net/live/33836dc760184b7893498e074a7f2cb8?wsSecret=e483ece4e987612b94435232c282151d&wsTime=1495788505";

        DialogMaker.showProgressDialog(MainActivity.this, null, "创建房间中", true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancelEnterRoom = true;

            }
        }).setCanceledOnTouchOutside(false);

        RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
//        roomInfoEntity.setRoomid(7928165);
//        roomInfoEntity.setPushUrl("rtmp://pdl065914eb.live.126.net/live/9f7a054373ef47bdbad62602d0dfef14?wsSecret=ec82b5278c8d3d28a8932180ee0d6ef9&wsTime=1489809122");

        roomInfoEntity.setRoomid(Integer.parseInt(roomid));

        roomInfoEntity.setPushUrl(url);

        DemoCache.setRoomInfoEntity(roomInfoEntity);

        Log.e(url+"",">+++++++++>>>>"+roomid);
        PublishParam publishParam = new PublishParam();
        publishParam.pushUrl = url;
        publishParam.definition = "SD";
        publishParam.openAudio = true;
        publishParam.openVideo = true;
        publishParam.useFilter = true;
        publishParam.faceBeauty = false;



        if(!cancelEnterRoom) {
            if(b){

            LiveRoomActivity.startAudience(MainActivity.this,  roomid, url, true);

            }else {

                LiveRoomActivity.startLive(MainActivity.this, roomid, publishParam);
            }


        }
//        DialogMaker.dismissProgressDialog();

//        DemoServerHttpClient.getInstance().createRoom(MainActivity.this, new DemoServerHttpClient.DemoServerHttpCallback<RoomInfoEntity>() {
//            @Override
//            public void onSuccess(RoomInfoEntity roomInfoEntity) {
//
//                roomId = roomInfoEntity.getRoomid()+"";
//                push_url = roomInfoEntity.getPushUrl();
//                DemoCache.setRoomInfoEntity(roomInfoEntity);
//
//                PublishParam publishParam = new PublishParam();
//                /*publishParam.pushUrl = push_url;
//                publishParam.definition = quality;
//                publishParam.openVideo = open_video;
//                publishParam.openAudio = open_audio;
//                publishParam.useFilter = useFilter;
//                publishParam.faceBeauty = faceBeauty;*/
//
//                String url="rtmp://pdl065914eb.live.126.net/live/f3cf3aa0981c465fb77ae9003fbf44f3?wsSecret=c5666367a61b9aaae1e6795414b9eff8&wsTime=1495856168"
//                publishParam.pushUrl = url;
//                publishParam.definition = "SD";
//                publishParam.openAudio = true;
//                publishParam.openVideo = true;
//                publishParam.useFilter = true;
//                publishParam.faceBeauty = false;
//
//                if(!cancelEnterRoom) {
//
//                    LiveRoomActivity.startLive(MainActivity.this, roomId, publishParam);
//                }
//                DialogMaker.dismissProgressDialog();
//            }
//
//            @Override
//            public void onFailed(int code, String errorMsg) {
//                Log.e("开始直播-----",errorMsg);
////                showToast(errorMsg);
//
//
//                DialogMaker.dismissProgressDialog();
//            }
//        });




    }

    /**
     * 登录云信服务器
     * @param account
     * @param token
     */
    private AbortableFuture<LoginInfo> loginRequest;

    public void loginNim(final String account, final String token) {
        ToastUtils.showShortToast("云迅登录。。。。。。。。。。。。。。。。。。。。。。。");
        // 登录
        loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token, readAppKey()));

        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
//                LogUtil.i(TAG, "login success");
                ToastUtils.showShortToast("云迅登录成功");
                Log.e("AppKey====",readAppKey());
                onLoginDone();
                DemoCache.setAccount(account);
                saveLoginInfo(account, token);

                // 初始化消息提醒
                NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

                // 构建缓存
                DataCacheManager.buildDataCacheAsync();

               /* // 进入主界面
                MainActivity.start(LoginActivity.this);
                finish();*/
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                Log.e("Code====",code+"");
                ToastUtils.showShortToast("云迅登录失败"+code);
                if (code == 302 || code == 404) {
                    Toast.makeText(MainActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
                ToastUtils.showShortToast("云迅登录异常");
                Log.e("云迅登录异常====","~~~~~~~~~");
//                Toast.makeText(MainActivity.this, R.string.login_exception, Toast.LENGTH_LONG).show();
                onLoginDone();
            }
        });
    }

//    private boolean checkWritePermission() {
//        if(bWritePermission){
//            return true;
//        }else{
//            requestBasicPermission();
//            Toast.makeText(MainActivity.this, "授权读写存储卡权限后,才能正常使用", Toast.LENGTH_LONG).show();
//            return false;
//        }
//    }

    private void onLoginDone() {
        loginRequest = null;
        DialogMaker.dismissProgressDialog();
    }

    private void saveLoginInfo(final String account, final String token) {

        com.bayue.ciic.nim.config.perference.Preferences.saveUserAccount(account);
        com.bayue.ciic.nim.config.perference.Preferences.saveUserToken(token);
    }
    private  String readAppKey() {
        try {
            ApplicationInfo appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void AskForPermission() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        builder.setTitle("权限设置");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                startActivity(intent);
            }
        });
        builder.create().show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //用户可能刚设置完权限回到Activity,  此时再次检查权限
        if(!bPermission) {
            bPermission = checkPublishPermission();
        }
    }

    @OnClick({R.id.ll_main_platform, R.id.ll_main_company, R.id.ll_main_geren})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_main_platform:
                setFrament(platfrom,llMainPlatform);
                llMainPlatform.setEnabled(false);
                llMainCompany.setEnabled(true);
                llMainGeren.setEnabled(true);
                break;
            case R.id.ll_main_company:

                if(Preferences.getEnterprise_id().equals("0")){
                    remind();
                    return;

                }

                setFrament(company,llMainCompany);
                llMainPlatform.setEnabled(true);
                llMainCompany.setEnabled(false);
                llMainGeren.setEnabled(true);
                break;
            case R.id.ll_main_geren:
                setFrament(geren,llMainGeren);
                llMainPlatform.setEnabled(true);
                llMainCompany.setEnabled(true);
                llMainGeren.setEnabled(false);
                break;
        }
    }
    private void setFrament(Fragment fragm, LinearLayout ll){
        restoreColor();

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        ll.setBackgroundColor(getResources().getColor(R.color.clickColor));
        getFragmentManager().popBackStack();
//        Log.e("数量yyyyyyy", getFragmentManager().getBackStackEntryCount()+"");
       /* if(platfrom!=null)
        transaction.hide(platfrom);
        if(company!=null)
        transaction.hide(company);
        if(geren!=null)
        transaction.hide(geren);
            transaction.add(R.id.fl_main,fragm);
            Log.e("","添加--------");

            transaction.show(fragm);
*/
        Log.e("transaction===========",fragm+"");
        transaction.replace(R.id.fl_main,fragm);
//        transaction.addToBackStack(null);//将fragment加入返回栈
        transaction.commit();
    }
   /* public void switchContent(Fragment from, Fragment to, int position) {
        if (mContent != to) {
            mContent = to;
            FragmentTransaction transaction = fm.beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(from)
                        .add(R.id.content_frame, to, tags[position]).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }*/

    private void restoreColor(){

        llMainPlatform.setBackgroundColor(getResources().getColor(R.color.themecolor));
        llMainCompany.setBackgroundColor(getResources().getColor(R.color.themecolor));
        llMainGeren.setBackgroundColor(getResources().getColor(R.color.themecolor));
    }

    public void addFrament(Fragment fragm,String tag){

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
//      ll.setBackgroundColor(getResources().getColor(R.color.clickColor));
        getFragmentManager().popBackStack();
        Log.e("数量", getFragmentManager().getBackStackEntryCount()+"");
        transaction.add(R.id.fl_main,fragm,tag);
        transaction.addToBackStack(null);//将fragment加入返回栈
        transaction.commit();
    }
    //改写物理按键——返回的逻辑
    private long firstTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (mWebView.canGoBack()) {
//                mWebView.goBack();//返回上一页面
//                return true;
//            } else {
//                System.exit(0);//退出程序
//            }
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;//更新firstTime
                return true;
            } else {
                //两次按键小于2秒时，退出应用
//                App.bgActivity.finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void remind(){
        LayoutInflater inflaterDl = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout)inflaterDl.inflate(R.layout.dialog_remind, null );

        //对话框
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes() ;
        Display display =this.getWindowManager().getDefaultDisplay();
        params.width =(int) (display.getWidth()*0.8);

        //使用这种方式更改了dialog的框宽
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setContentView(layout);

        layout.findViewById(R.id.but_dialog_queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    //获取数据类型
    private void getTagData() {
        Map<String, Object> map = new HashMap<>();
        map.put("type","video");
//        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.TAG, map, new Callback() {
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
                    final TagBean bean = gson.fromJson(msg, TagBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                 lists = bean.getData();
                                Log.e("list==size==",lists.size()+"");
                                if(setTag!=null)
                                setTag.setTag(lists);

//                                Log.e("TTTTTT", lists.get(0).getName() + lists.get(1).getName() + lists.get(2).getName());

                            }
                        });
                    } else {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
//                                ToastUtils.showShortToast(bean.getMsg());
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

    SetTag setTag;

    public interface SetTag{
        void setTag(List<TagBean.DataBean> lists);

    }

    public void setTag(SetTag setTag){
        this.setTag=setTag;
    }
}
