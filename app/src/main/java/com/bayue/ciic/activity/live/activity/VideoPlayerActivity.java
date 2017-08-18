package com.bayue.ciic.activity.live.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.bean.CommentBean;
import com.bayue.ciic.bean.ShipinInfoBean;
import com.bayue.ciic.bean.ZanBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.nim.session.input.InputConfig;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.util.VcloudFileUtils;
import com.bayue.ciic.util.network.NetworkUtils;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.bayue.ciic.utils.glide.GlideCircleTransform;
import com.bayue.ciic.utils.glide.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 拉流界面
 */
public class VideoPlayerActivity extends BaseActivity {

    public final static String VIDEO_ID = "video_id";
    public final static String VIDEO_URL = "video_url";
    public final static String VIDEO_IMGURL = "video_imgurl";

    @BindView(R.id.iv_video_photo)
    ImageView ivVideoPhoto;
    @BindView(R.id.tv_video_name)
    TextView tvVideoName;
    @BindView(R.id.tv_video_title)
    TextView tvVideoTitle;
    @BindView(R.id.tv_video_time)
    TextView tvVideoTime;
    @BindView(R.id.tv_video_number)
    TextView tvVideoNumber;
    @BindView(R.id.ll_number_shipin)
    LinearLayout llNumberShipin;
    @BindView(R.id.tv_video_zan)
    TextView tvVideoZan;
    @BindView(R.id.ll_zan_shipin)
    LinearLayout llZanShipin;
    @BindView(R.id.tv_video_cang)
    TextView tvVideoCang;
    @BindView(R.id.ll_cang_shipin)
    LinearLayout llCangShipin;
    @BindView(R.id.tv_video_fen)
    TextView tvVideoFen;
    @BindView(R.id.ll_feng_shipin)
    LinearLayout llFengShipin;
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
    @BindView(R.id.iv_video_zan)
    ImageView ivVideoZan;
    @BindView(R.id.elv_shipin)
    ExpandableListView elvShipin;


    private String mUrl; // 拉流地址


    JCVideoPlayerStandard  player;

    TextView send;

    ExpandableListView elv_shezhi;

    Myadapter myadapter;

    RequestManager glideRequest;

    String imgurl;
    String url;
    String video_id="";
    int number=0;
    boolean iscang = false, iszan = false;

    /**
     * 静态方法 启动直播Activity
     *
     * @param context 上下文
     * @param id      视频id
     */
    public static void startActivity(final Context context, String id, String url, String imgurl) {
        final Intent intent = new Intent();
        intent.setClass(context, VideoPlayerActivity.class);
        intent.putExtra(VIDEO_ID, id);
        intent.putExtra(VIDEO_URL, url);
        intent.putExtra(VIDEO_IMGURL, imgurl);
        DialogMaker.showProgressDialog(context, "加载中", false);

        //检查是否有网络  如果有就跳转播放界面
        new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                if (NetworkUtils.isNetworkConnected(true)) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            context.startActivity(intent);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(context, "当前无网络,请检查网络后开启", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                DialogMaker.dismissProgressDialog();
            }
        }).start();

    }

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_live_watch;
    }

    @Override
    protected void initViews() {
        initAudienceParam();


        glideRequest = Glide.with(this);
        ImageView ivGoback = (ImageView) findViewById(R.id.iv_goback);
        TextView tvTitletxt = (TextView) findViewById(R.id.tv_titletxt);
        ImageView ivShezhi = (ImageView) findViewById(R.id.iv_shezhi);
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("视频");
        ivShezhi.setVisibility(View.INVISIBLE);
        final FrameLayout flGoback = (FrameLayout) findViewById(R.id.fl_goback);
        flGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        bindView();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VcloudFileUtils.getInstance(getApplicationContext()).init();
    }

    private InputConfig inputConfig = new InputConfig(false, false, false);

    private String cacheInputString = "";

    private void bindView() {



        player = (JCVideoPlayerStandard) findViewById(R.id.player);
        player.setUp(url
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");

        glideRequest.
                load(imgurl)
                .placeholder(R.drawable.shouye_img_01_2x)
                .error(R.drawable.shouye_img_01_2x)
                .transform(new GlideRoundTransform(this,0))
                .into(player.thumbImageView);
        player.startVideo();

        send = (TextView) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                puid="0";
                InputActivity.startActivityForResult(VideoPlayerActivity.this, cacheInputString,

                        inputConfig, new InputActivity.InputActivityProxy() {
                            @Override
                            public void onSendMessage(String text) {


                                content=text;
                                Log.e("提交评论！！","______________________");
                                addComment();
                            }
                        });


            }
        });

        elv_shezhi = (ExpandableListView) findViewById(R.id.elv_shipin);

        myadapter = new Myadapter();
        elv_shezhi.setGroupIndicator(null);
        elv_shezhi.setAdapter(myadapter);
        elv_shezhi.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {



                puid=commentData.get(groupPosition).getComment_id();

                return true;
            }
        });

    }


    //zhibo控制器
    private void initAudienceParam() {
        Intent intent = getIntent();
        video_id = intent.getStringExtra(VIDEO_ID);
        url = intent.getStringExtra(VIDEO_URL);
        imgurl = intent.getStringExtra(VIDEO_IMGURL);
        getData();
        getComment();
    }

    private void getData() {

        Map<String, Object> map = new HashMap<>();
        map.put("token", Preferences.getString(this, Preferences.TOKEN));
        map.put("video_id", video_id);

        HTTPUtils.getNetDATA(API.BaseUrl + API.SHIPING_INFO, map, new Callback() {
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
                    final ShipinInfoBean bean = gson.fromJson(msg, ShipinInfoBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                ShipinInfoBean.DataBean dataBean = bean.getData();

                                glideRequest.
                                        load(dataBean.getAuthor_img())
                                        .placeholder(R.mipmap.bianjiziliao_toux2_3x)
                                        .error(R.mipmap.bianjiziliao_toux2_3x)
                                        .transform(new GlideCircleTransform(VideoPlayerActivity.this))
                                        .into(ivVideoPhoto);

                                tvVideoName.setText(dataBean.getEnterprise_short_name() + "-" + dataBean.getAuthor_name());
                                tvVideoTitle.setText(dataBean.getVideo_name());
                                tvVideoTime.setText(dataBean.getAdd_time());
                                tvVideoNumber.setText(dataBean.getClick_count() + "人以观看");
                                number=dataBean.getThumb_count();
                                tvVideoZan.setText(number+"");
                                iszan = dataBean.getIs_thumbs() == 0 ? false : true;
                                iscang = dataBean.getIs_collection() == 0 ? false : true;

                                Log.e("视频详情》》》","_______________"+iszan+number);
                                if(iszan){
                                    ivVideoZan.setImageResource(R.mipmap.dianzan_2x);
                                }else {
                                    ivVideoZan.setImageResource(R.mipmap.zhibo_dianzan_3x);
                                }
                                if(iscang){
                                    tvVideoCang.setText("取消收藏");
                                }else{
                                    tvVideoCang.setText("收藏");
                                }
                                /*imgurl=dataBean.getVideo_img();
                                url=dataBean.getVideo_url();*/


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




    }

    @Override
    public void onBackPressed() {
        if (player.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        player.releaseAllVideos();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }




    @Override
    protected void onStop() {
        super.onStop();


    }



    @OnClick({R.id.ll_number_shipin, R.id.ll_zan_shipin, R.id.ll_cang_shipin, R.id.ll_feng_shipin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_number_shipin:
                break;
            case R.id.ll_zan_shipin:
                getThumb();
                break;
            case R.id.ll_cang_shipin:
                getCang();
                break;
            case R.id.ll_feng_shipin:
                fen();
                break;
        }
    }

    class Myadapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return commentData.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return commentData.get(groupPosition).getChild().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discuss, null);
            }
            ImageView iv_touxiang_discuss= (ImageView) convertView.findViewById(R.id.iv_touxiang_discuss);
            TextView tv_name_discuss= (TextView) convertView.findViewById(R.id.tv_name_discuss);
            TextView tv_time_discuss= (TextView) convertView.findViewById(R.id.tv_time_discuss);
            TextView tv_content_discuss= (TextView) convertView.findViewById(R.id.tv_content_discuss);
            TextView tv_child_discuss= (TextView) convertView.findViewById(R.id.tv_child_discuss);

            glideRequest.
                    load(commentData.get(groupPosition).getAuthor_avatar())
                    .placeholder(R.mipmap.bianjiziliao_toux2_3x)
                    .error(R.mipmap.bianjiziliao_toux2_3x)
                    .transform(new GlideCircleTransform(VideoPlayerActivity.this))
                    .into(iv_touxiang_discuss);
           tv_name_discuss.setText(commentData.get(groupPosition).getAuthor_name());
            tv_time_discuss.setText(commentData.get(groupPosition).getAdd_time2());
            tv_content_discuss.setText(commentData.get(groupPosition).getContent());
            tv_child_discuss.setText("回复("+commentData.get(groupPosition).getChild_count()+")");

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discuss_child, null);
            }

            TextView tv_name_child= (TextView) convertView.findViewById(R.id.tv_name_child);
            TextView tv_time_child= (TextView) convertView.findViewById(R.id.tv_time_child);
            TextView tv_content_child= (TextView) convertView.findViewById(R.id.tv_content_child);

            tv_name_child.setText(commentData.get(groupPosition).getChild().get(childPosition).getAuthor_name());
            tv_time_child.setText(commentData.get(groupPosition).getChild().get(childPosition).getAdd_time2());
            tv_content_child.setText(commentData.get(groupPosition).getChild().get(childPosition).getContent());


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    private void getThumb() {

        Map<String, Object> map = new HashMap<>();
        map.put("token", Preferences.getString(this, Preferences.TOKEN));
        map.put("video_id", video_id);
        map.put("type", 1);

        HTTPUtils.getNetDATA(API.BaseUrl + API.THUMB, map, new Callback() {
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
                    final ZanBean bean = gson.fromJson(msg, ZanBean.class);
                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                if(iszan){
                                    iszan=!iszan;
                                    number--;
                                    tvVideoZan.setText(number+"");

                                }else {

                                    number++;
                                    tvVideoZan.setText(number+"");
                                    iszan=!iszan;

                                }
                                Log.e("点赞",bean.getData()+iszan);
                                if(iszan){
                                    ivVideoZan.setImageResource(R.mipmap.dianzan_2x);
                                }else {
                                    ivVideoZan.setImageResource(R.mipmap.zhibo_dianzan_3x);
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

    private void getCang() {

        Map<String, Object> map = new HashMap<>();
        map.put("token", Preferences.getString(this, Preferences.TOKEN));
        map.put("video_id", video_id);
        map.put("type", 1);

        HTTPUtils.getNetDATA(API.BaseUrl + API.CANG, map, new Callback() {
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
                    final ZanBean bean = gson.fromJson(msg, ZanBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                if(iscang){
                                    iscang=!iscang;
                                }else {
                                    iscang=!iscang;
                                }
                                if(iscang){
                                    tvVideoCang.setText("取消收藏");
                                }else{
                                    tvVideoCang.setText("收藏");
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

    private void fen() {
        LayoutInflater inflaterDl = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout) inflaterDl.inflate(R.layout.dialog_fen, null);

        //对话框
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        Display display = getWindowManager().getDefaultDisplay();
        params.width = (int) (display.getWidth() * 0.90);

        //使用这种方式更改了dialog的框宽
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setContentView(layout);

        LinearLayout ll_weixin = (LinearLayout) layout.findViewById(R.id.ll_weixin);
        LinearLayout ll_qq = (LinearLayout) layout.findViewById(R.id.ll_qq);
        LinearLayout ll_weibo = (LinearLayout) layout.findViewById(R.id.ll_weibo);
        Button but_cancle = (Button) layout.findViewById(R.id.but_cancel);

        ll_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ll_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ll_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        but_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    List<CommentBean.DataBean> commentData=new ArrayList<>();


    private void getComment() {

        Map<String, Object> map = new HashMap<>();
        map.put("token", Preferences.getString(this, Preferences.TOKEN));
        map.put("video_id", 16);

        HTTPUtils.getNetDATA(API.BaseUrl + API.COMMENT, map, new Callback() {
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
                    final CommentBean bean = gson.fromJson(msg, CommentBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                commentData.clear();
                                commentData.addAll(bean.getData());

                                myadapter.notifyDataSetChanged();
                                //遍历所有group,将所有项设置成默认展开
                                if(puid.equals("-1")){
                                    int groupCount = elv_shezhi.getCount();
                                    for (int i=0; i<groupCount; i++)
                                    {
                                        elv_shezhi.expandGroup(i);
                                    };
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
    String content="";
    String puid="-1";

    private void addComment(){

        Map<String, Object> map = new HashMap<>();
        map.put("token", Preferences.getString(this, Preferences.TOKEN));
        map.put("id", "16");
        map.put("puid",puid);
        map.put("content",content);
        Log.e("评论内容————",content);
        map.put("ip_address",VideoPlayerActivity.getHostIP());

        HTTPUtils.getNetDATA(API.BaseUrl + API.COMMENT_ADD, map, new Callback() {
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
                    final ZanBean bean = gson.fromJson(msg, ZanBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showShortToast(bean.getData());
                                getComment();
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

    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }
}
