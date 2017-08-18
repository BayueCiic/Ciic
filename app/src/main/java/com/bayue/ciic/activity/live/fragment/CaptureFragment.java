package com.bayue.ciic.activity.live.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.activity.live.activity.LiveRoomActivity;
import com.bayue.ciic.activity.live.base.BaseFragment;
import com.bayue.ciic.bean.StartliveBean;
import com.bayue.ciic.bean.ZhiboDataBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.liveStreaming.CapturePreviewContract;
import com.bayue.ciic.liveStreaming.CapturePreviewController;
import com.bayue.ciic.liveStreaming.PublishParam;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.util.network.NetworkUtils;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.bayue.ciic.view.ListPopupWindow;
import com.bayue.ciic.view.MyPopupWindow;
import com.bayue.ciic.widget.MixAudioLayout;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.netease.LSMediaCapture.view.NeteaseGLSurfaceView;
import com.netease.LSMediaCapture.view.NeteaseSurfaceView;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import jp.co.cyberagent.android.gpuimage.GPUImageFaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zhukkun on 1/5/17.
 * 直播采集推流Fragment
 */
public class CaptureFragment extends BaseFragment implements CapturePreviewContract.CapturePreviewUi {

    /**
     * Ui 基础控件
     */
    private ImageView btnFlash;
    private ImageView btnCancel;
    private ImageView btnAudio;
    private ImageView btnVideo, iv_portion_staff, iv_recorded_yes, iv_recorded_no;
    private ImageView btnCamSwitch, iv_live_all, iv_portion_company;
    public ImageView iv_live_cover;
    private Button btnStartLive;
    private View audioAnimate;
    private SeekBar focusSeekBar;
    private SeekBar filterSeekBar;

    private EditText et_live_title;

    private TextView tv_select_company, tv_select_staff, tv_live_leixing, tv_live_huodong;
    private LinearLayout ll_recorded_yes, ll_recorded_no, ll_live_xx, ll_portion_company, ll_portion_staff, ll_live_all;

    MyPopupWindow popupWindow;

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.but_popup_paizhao:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//                    controller.deletePreview();

                    liveActivity.startActivityForResult(intent, 1);

                    popupWindow.dismiss();
                    break;
                case R.id.but_popup_xiangce:
                    GalleryConfig config = new GalleryConfig.Build()
                            .limitPickPhoto(1)
                            .singlePhoto(false)
                            .hintOfPick("只可选一张")
//                            .filterMimeTypes(new String[]{"jpg","png"})
                            .build();
                    GalleryActivity.openActivity(liveActivity, 100, config);
//                    GalleryActivity.openActivity(GerenShezhi.this, true,2);
                    popupWindow.dismiss();
                    break;
            }
        }
    };


    MixAudioLayout mixAudioLayout = null; //伴音控制布局

    boolean canUse4GNetwork = false; //是否能使用4G网络进行直播

    /**
     * 滤镜模式的SurfaceView
     */
    private NeteaseGLSurfaceView filterSurfaceView;

    /**
     * 普通模式的SurfaceView
     */
    private NeteaseSurfaceView normalSurfaceView;

    /**
     * 控制器
     */
    CapturePreviewController controller;

    LiveRoomActivity liveActivity;

    LiveBottomBar liveBottomBar;

    private long lastClickTime;

    ListPopupWindow listPopupWindow;

    private List<ZhiboDataBean.DataBean.LabelBean> label=new ArrayList<>();
    private List<ZhiboDataBean.DataBean.ActivityBean> activity=new ArrayList<>();
    private List<ZhiboDataBean.DataBean.CompanyBean> company=new ArrayList<>();
    private List<ZhiboDataBean.DataBean.UidsBean> uids=new ArrayList<>();

    String label_id="";
    String activity_id="";
    String isRecord="1";
    String show_id="";

    public File file;

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(str.equals("活动")){
                tv_live_huodong.setText("   " + dates.get(position));
                activity_id=activity.get(position).getActivity_id();
            }else if(str.equals("类型")){
                tv_live_leixing.setText("   " + dates.get(position));
                label_id=label.get(position).getId();
            }
            listPopupWindow.dismiss();

        }
    };

    List<String> dates = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_capture, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        controller = getCaptureController();
        controller.handleIntent(getActivity().getIntent());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        liveActivity = (LiveRoomActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        liveActivity = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        controller.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onPause();
    }

    /**
     * 初始化Ui
     */
    private void initView() {


        Log.e("你干嘛？","来啦！！！！");



        if(Preferences.getImg().isEmpty()||Preferences.getImg()==null){

            try {
                URL url=new URL(Preferences.getImg());
                 file=new File(url.toURI());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }


        bindView();
        clickView();
        getTagHuodong();
        /*tv_live_huodong.setEnabled(false);
        tv_live_leixing.setEnabled(false);
        tv_select_company.setEnabled(false);
        tv_select_staff.setEnabled(false);
*/


    }

    /**
     * 绑定Ui控件
     */
    private void bindView() {
        btnFlash = (ImageView) findViewById(R.id.live_flash);
        btnCancel = (ImageView) findViewById(R.id.live_cancel);
        btnAudio = (ImageView) findViewById(R.id.live_audio_btn);
        btnVideo = (ImageView) findViewById(R.id.live_video_btn);
        btnCamSwitch = (ImageView) findViewById(R.id.live_camera_btn);
        focusSeekBar = (SeekBar) findViewById(R.id.live_seekbar_focus);
        filterSeekBar = (SeekBar) findViewById(R.id.live_seekbar_filter);
        btnStartLive = (Button) findViewById(R.id.btn_star_live);
        audioAnimate = findViewById(R.id.layout_audio_animate);
        filterSurfaceView = (NeteaseGLSurfaceView) findViewById(R.id.live_filter_view);
        normalSurfaceView = (NeteaseSurfaceView) findViewById(R.id.live_normal_view);



        et_live_title = findView(R.id.et_live_title);
        iv_portion_staff = findView(R.id.iv_portion_staff);
        iv_recorded_yes = findView(R.id.iv_recorded_yes);
        iv_recorded_no = findView(R.id.iv_recorded_no);
        iv_live_cover = findView(R.id.iv_live_cover);
        iv_live_all = findView(R.id.iv_live_all);
        iv_portion_company = findView(R.id.iv_portion_company);
        tv_select_company = findView(R.id.tv_select_company);
        tv_select_staff = findView(R.id.tv_select_staff);
        ll_recorded_no = findView(R.id.ll_recorded_no);
        ll_recorded_yes = findView(R.id.ll_recorded_yes);
        tv_live_huodong = findView(R.id.tv_live_huodong);
        tv_live_leixing = findView(R.id.tv_live_leixing);
        ll_live_xx = findView(R.id.ll_live_xx);
        ll_portion_company = findView(R.id.ll_portion_company);
        ll_portion_staff = findView(R.id.ll_portion_staff);
        ll_live_all = findView(R.id.ll_live_all);


        et_live_title.setText(Preferences.getTitle());
        Glide.with(getContext())
                .load(Preferences.getImg())
                .asBitmap()
                .into(iv_live_cover);



        dates.add("夜跑");
        dates.add("运动会");
        dates.add("休闲");



    }

    public void attachBottomBarToFragment(final LiveBottomBar liveBottomBar) {

        this.liveBottomBar = liveBottomBar;
        if (this.liveBottomBar == null) {
            return;
        }
        liveBottomBar.setMusicClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mixAudioLayout == null) {
                    mixAudioLayout = new MixAudioLayout(getContext());
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    liveBottomBar.addView(mixAudioLayout, layoutParams);
                } else {
                    mixAudioLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        liveBottomBar.setMsgClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                liveActivity.showInputPanel();
            }
        });

        liveBottomBar.setCaptureClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                controller.screenShot();
            }
        });

        liveBottomBar.getBeautyFilterBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveBottomBar.getNoFilterBtn().setSelected(false);
                liveBottomBar.getBeautyFilterBtn().setSelected(true);
                controller.switchFilterTo(new GPUImageFaceFilter());
            }
        });

        liveBottomBar.getNoFilterBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveBottomBar.getNoFilterBtn().setSelected(true);
                liveBottomBar.getBeautyFilterBtn().setSelected(false);
                controller.switchFilterTo(new GPUImageFilter());
            }
        });

    }


    String str="";
    List<Bean> companys=new ArrayList<>();
    List<Bean> uidss=new ArrayList<>();

    /**
     * 设置Ui点击事件
     */
    private void clickView(){

        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.switchFlash();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.switchAudio();
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.switchVideo();
            }
        });

        btnStartLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnStartLive.setText("准备中...");
                sendData();
            }
        });

        btnCamSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastClickTime > 1000) {
                    controller.switchCam();
                    lastClickTime = currentTime;
                }
            }
        });

        focusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                controller.setZoom(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        filterSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                controller.setFilterStrength(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        iv_live_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                controller.onDestroy();

                popupWindow = new MyPopupWindow(liveActivity, itemsOnClick);
                popupWindow.showAtLocation(iv_live_cover, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        tv_live_huodong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str="活动";
                dates.clear();
                for (int i = 0; i <activity.size() ; i++) {
                    dates.add(activity.get(i).getTitle());
                }
                Log.e("captureFragment>>>dates",dates.size()+"");
                listPopupWindow = new ListPopupWindow(liveActivity, dates, tv_live_huodong.getWidth(), onItemClickListener);
                listPopupWindow.showAsDropDown(tv_live_huodong, 0, 0);
            }
        });
        tv_live_leixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str="类型";
                dates.clear();
                Log.e("captureFragment>>>dates",label.size()+"");
                for (int i = 0; i <label.size() ; i++) {
                    dates.add(label.get(i).getName());
                }
                listPopupWindow = new ListPopupWindow(liveActivity, dates, tv_live_leixing.getWidth(), onItemClickListener);
                listPopupWindow.showAsDropDown(tv_live_leixing, 0, 0);
            }
        });

        ll_live_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hf(iv_live_all);
            }
        });
        ll_portion_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click=1;
                hf(iv_portion_company);

                lists.clear();
                for (int i = 0; i <company.size() ; i++) {
                    Bean bean=new Bean();
                    bean.setIs(true);
                    bean.setName(company.get(i).getUsername());
                    bean.setId(company.get(i).getId());
                    lists.add(bean);
                }
            }
        });
        ll_portion_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click=2;
                hf(iv_portion_staff);
                lists.clear();
                for (int i = 0; i <uids.size() ; i++) {
                    Bean bean=new Bean();
                    bean.setIs(true);
                    bean.setName(uids.get(i).getUsername());
                    bean.setId(uids.get(i).getId());
                    lists.add(bean);
                }

            }
        });

        ll_recorded_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hf2(iv_recorded_yes);
                isRecord="1";
            }
        });
        ll_recorded_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hf2(iv_recorded_no);
                isRecord="0";
            }
        });

        tv_select_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click=3;
                hf(iv_portion_company);
                lists.clear();
                if(companys==null||companys.isEmpty()){
                    for (int i = 0; i <company.size() ; i++) {
                        Bean bean=new Bean();
                        bean.setIs(true);
                        bean.setName(company.get(i).getUsername());
                        bean.setId(company.get(i).getId());
                        companys.add(bean);
                    }
                }
                lists.addAll(companys);
                dialog();
            }
        });
        tv_select_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click=4;
                hf(iv_portion_staff);
                lists.clear();
                if(uidss==null||uidss.isEmpty()) {
                    for (int i = 0; i < uids.size(); i++) {
                        Bean bean = new Bean();
                        bean.setIs(true);
                        bean.setName(uids.get(i).getUsername());
                        bean.setId(uids.get(i).getId());
                        uidss.add(bean);
                    }
                }

                lists.addAll(uidss);
                dialog();
            }
        });

    }

    //恢复图标
    private void hf(ImageView imageView) {

        iv_live_all.setBackgroundResource(R.drawable.living_icon2_1x);
        iv_portion_company.setBackgroundResource(R.drawable.living_icon2_1x);
        iv_portion_staff.setBackgroundResource(R.drawable.living_icon2_1x);
        imageView.setBackgroundResource(R.drawable.living_icon1_1x);
    }

    //恢复图标
    private void hf2(ImageView imageView) {
        iv_recorded_no.setBackgroundResource(R.drawable.living_icon2_1x);
        iv_recorded_yes.setBackgroundResource(R.drawable.living_icon2_1x);
        imageView.setBackgroundResource(R.drawable.living_icon1_1x);
    }

    /**
     * 设置显示的SurfaceView
     *
     * @param hasFilter 是否带滤镜功能
     */
    @Override
    public void setSurfaceView(boolean hasFilter) {
        if (hasFilter) {
            filterSurfaceView.setVisibility(View.VISIBLE);
            normalSurfaceView.setVisibility(View.GONE);
        } else {
            normalSurfaceView.setVisibility(View.VISIBLE);
            filterSurfaceView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置预览画面 大小
     *
     * @param hasFilter 是否有滤镜
     */
    @Override
    public void setPreviewSize(boolean hasFilter, int previewWidth, int previewHeight) {
        if (hasFilter) {
            filterSurfaceView.setPreviewSize(previewWidth, previewHeight);
        } else {
            normalSurfaceView.setPreviewSize(previewWidth, previewHeight);
        }
    }

    /**
     * 获取正在显示的SurfaceView
     *
     * @return
     */
    @Override
    public View getDisplaySurfaceView(boolean hasFilter) {
        if (hasFilter) {
            return filterSurfaceView;
        } else {
            return normalSurfaceView;
        }
    }

    /**
     * 设置直播开始按钮, 是否可点击
     *
     * @param clickable
     */
    @Override
    public void setStartBtnClickable(boolean clickable) {
        btnStartLive.setClickable(clickable);
    }

    /**
     * 正常开始直播
     */
    @Override
    public void onStartLivingFinished() {
        btnAudio.setVisibility(View.VISIBLE);
        ll_live_xx.setVisibility(View.GONE);
        btnStartLive.setVisibility(View.GONE);
        btnStartLive.setText("开始直播");
        if (liveActivity != null) {
            liveActivity.onStartLivingFinished();
        }
        DialogMaker.dismissProgressDialog();
    }

    /**
     * 停止直播完成时回调
     */
    @Override
    public void onStopLivingFinished() {
        //btnRestart.setVisibility(View.GONE);
    }

    /**
     * 设置audio按钮状态
     *
     * @param isPlay 是否正在开启
     */
    @Override
    public void setAudioBtnState(boolean isPlay) {
        if (isPlay) {
            btnAudio.setImageResource(R.drawable.btn_audio_on_n);
        } else {
            btnAudio.setImageResource(R.drawable.btn_audio_off_n);
        }
    }

    /**
     * 设置Video按钮状态
     *
     * @param isPlay 是否正在开启
     */
    @Override
    public void setVideoBtnState(boolean isPlay) {
        if (isPlay) {
            btnVideo.setImageResource(R.drawable.btn_camera_on_n);
        } else {
            btnVideo.setImageResource(R.drawable.btn_camera_off_n);
        }
    }

    @Override
    public void setFilterSeekBarVisible(boolean visible) {
        if (visible) {
            //fixme 如要显示滤镜强度进度条,解除注释
            //filterSeekBar.setVisibility(View.VISIBLE);
        } else {
            filterSeekBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void checkInitVisible(PublishParam mPublishParam) {
        if (mPublishParam.openVideo) {
            btnCamSwitch.setVisibility(View.VISIBLE);
            if (liveBottomBar != null) {
                liveBottomBar.getFilterView().setVisibility(View.VISIBLE);
                liveBottomBar.getCaptureView().setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 获取Ui对应的controller
     *
     * @return
     */
    private CapturePreviewController getCaptureController() {
        return new CapturePreviewController(getActivity(), this);
    }

    /**
     * 按下返回键
     */
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @Override
    public void showAudioAnimate(boolean b) {
        if (audioAnimate != null) {
            if (b) {
                audioAnimate.setVisibility(View.VISIBLE);
            } else {
                audioAnimate.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDisconnect() {
        btnAudio.setVisibility(View.GONE);
        btnStartLive.setVisibility(View.VISIBLE);

        //liveActivity为空,则已关闭直播页面
        if (liveActivity != null) {
            liveActivity.onLiveDisconnect();
            controller.liveStartStop();
        }
    }

    @Override
    public void normalFinish() {
        if (liveActivity != null) {
            liveActivity.normalFinishLive();
        }
    }

    @Override
    public void onStartInit() {
        btnStartLive.setText("准备中...");
    }

    @Override
    public void onCameraPermissionError() {
        showConfirmDialog("无法打开相机", "可能没有相关的权限,请开启权限后重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        }, null);
    }

    @Override
    public void onAudioPermissionError() {
        showConfirmDialog("无法开启录音", "可能没有相关的权限,请开启权限后重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        }, null);
    }

    public void destroyController() {
        controller.tryToStopLivingStreaming();
        controller.onDestroy();
    }

    int click = 0;
    boolean b=true;
    String id;
    String[] uid;

    private void dialog() {
        LayoutInflater inflaterDl = LayoutInflater.from(liveActivity);
        LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.dialog_live, null);

        //对话框
        final Dialog dialog = new AlertDialog.Builder(liveActivity).create();
        dialog.show();

        /*WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        Display display = liveActivity.getWindowManager().getDefaultDisplay();
        params.width = (int) (display.getWidth() * 0.8);
        //使用这种方式更改了dialog的框宽
        dialog.getWindow().setAttributes(params);*/

        dialog.getWindow().setContentView(layout);

        layout.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView tv_dialog_title= (TextView) layout.findViewById(R.id.tv_dialog_title);

        if(click==3){
            tv_dialog_title.setText("· 选择公司");
        }else if(click==4){
            tv_dialog_title.setText("· 选择职员");
        }

        ListView listView = (ListView) layout.findViewById(R.id.lv_dialog);
        final MyAdapter adapter=new MyAdapter();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(click==3){
                    if(companys.get(position).is()){
                        companys.get(position).setIs(!companys.get(position).is());
                    }else {
                        companys.get(position).setIs(!companys.get(position).is());
                    }
                    lists.clear();
                    lists.addAll(companys);
                }else if(click==4){
                    if(uidss.get(position).is()){
                        uidss.get(position).setIs(!uidss.get(position).is());
                    }else {
                        uidss.get(position).setIs(!uidss.get(position).is());
                    }
                    lists.clear();
                    lists.addAll(uidss);
                }

                adapter.notifyDataSetChanged();
            }
        });

        layout.findViewById(R.id.tv_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        layout.findViewById(R.id.tv_dialog_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer sb = new StringBuffer();

                for(int i = 0; i < lists.size(); i++){

                    if(lists.get(i).is()){

                        sb. append(lists.get(i).getId()+",");
                    }

                }
                String newStr = sb.toString();

                id =newStr.substring(0, newStr.length() - 1);

                Log.e("CaptureFragment>>>>>","id==="+id);
                dialog.dismiss();

            }
        });
        final ImageView iv_dialog_all= (ImageView) layout.findViewById(R.id.iv_dialog_all);
        if(b){
            iv_dialog_all.setBackgroundResource(R.drawable.living_icon6_2x);
        }else {
            iv_dialog_all.setBackgroundResource(R.drawable.living_icon6_2x);
        }
        layout.findViewById(R.id.ll_dialog_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b){
                    iv_dialog_all.setBackgroundResource(R.drawable.living_icon7_2x);
                    if(click==3){
                        for (int i = 0; i <companys.size() ; i++) {
                            companys.get(i).setIs(false);
                        }
                        lists.clear();
                        lists.addAll(companys);
                    }else if(click==4){
                        for (int i = 0; i <uidss.size() ; i++) {
                            uidss.get(i).setIs(false);
                        }
                        lists.clear();
                        lists.addAll(uidss);
                    }




                    b=false;
                }else {
                    iv_dialog_all.setBackgroundResource(R.drawable.living_icon6_2x);

                    if(click==3){
                        for (int i = 0; i <companys.size() ; i++) {
                            companys.get(i).setIs(true);
                        }
                        lists.clear();
                        lists.addAll(companys);
                    }else if(click==4){
                        for (int i = 0; i <uidss.size() ; i++) {
                            uidss.get(i).setIs(true);
                        }
                        lists.clear();
                        lists.addAll(uidss);
                    }



                    b=true;
                }
                adapter.notifyDataSetChanged();
            }
        });


    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {

                convertView = LayoutInflater.from(liveActivity).inflate(R.layout.item_dialog, null);
                viewHolder=new ViewHolder(convertView);
                convertView.setTag(viewHolder);

            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(lists.get(position).getName());

            if(lists.get(position).is()){
                viewHolder.is.setBackgroundResource(R.drawable.living_icon6_2x);
            }else{
                viewHolder.is.setBackgroundResource(R.drawable.living_icon7_2x);
            }

            return convertView;
        }

        class ViewHolder {

            TextView name;
            ImageView is;

            ViewHolder(View view) {
                name= (TextView) view.findViewById(R.id.name);
                is= (ImageView) view.findViewById(R.id.is);
            }
        }
    }

    private void getTagHuodong() {
        Map<String, Object> map = new HashMap<>();

        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.Zhibo.DATAS, map, new Callback() {
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
                    final ZhiboDataBean bean = gson.fromJson(msg, ZhiboDataBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                ZhiboDataBean.DataBean dataBean=bean.getData();

                                label.addAll(dataBean.getLabel());
                                activity.addAll(dataBean.getActivity());
                                company.addAll(dataBean.getCompany());
                                uids.addAll(dataBean.getUids());

                                tv_live_huodong.setEnabled(true);
                                tv_live_leixing.setEnabled(true);
                                tv_select_company.setEnabled(true);
                                tv_select_staff.setEnabled(true);


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
    private void sendData() {

        Map<String, Object> map = new HashMap<>();
        map.put("room_id",Preferences.getRoomid());
        map.put("title",et_live_title.getText().toString());
        map.put("label_id",label_id);
        map.put("show_id",show_id);
        map.put("activity_id",activity_id);
        map.put("isRecord",isRecord);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));

        Map<String,File> files=new HashMap<>();
        if(file!=null)
        files.put("file",file);

        HTTPUtils.getFileDATA(API.BaseUrl + API.Zhibo.STATR, map,files, new Callback() {
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
                    final StartliveBean bean = gson.fromJson(msg, StartliveBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {


                                if (!NetworkUtils.isNetworkConnected(false)) {
                                    showToast("无网络,请检查网络设置后重新直播");
                                    return;
                                }
                                if (!canUse4GNetwork && NetworkUtils.getNetworkType() == NetworkUtils.TYPE_MOBILE) {
                                    showConfirmDialog(null, "正在使用手机流量,是否开始直播?", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            controller.liveStartStop();
                                            controller.canUse4GNetwork(true);
                                            canUse4GNetwork = true;
                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                } else {
                                    controller.liveStartStop();
                                }


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


    List<Bean> lists=new ArrayList();

    class  Bean {

        String name;
        boolean is;
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean is() {
            return is;
        }

        public void setIs(boolean is) {
            this.is = is;
        }
    }

}
