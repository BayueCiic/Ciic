package com.bayue.ciic.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.ciic.App;
import com.bayue.ciic.MainActivity;
import com.bayue.ciic.R;
import com.bayue.ciic.activity.live.activity.VideoPlayerActivity;
import com.bayue.ciic.base.BaseFragment;
import com.bayue.ciic.bean.PZhiboBean;
import com.bayue.ciic.bean.ShouyeHuodongData;
import com.bayue.ciic.bean.ShouyeJingcaiBean;
import com.bayue.ciic.bean.ShouyeShipinBean;
import com.bayue.ciic.bean.ShouyeXinwenBean;
import com.bayue.ciic.bean.ShouyeZhiboBean;
import com.bayue.ciic.bean.TagBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/3.
 */

public class PlatfromShouye extends BaseFragment {


    static MainPlatfrom mainPlatfrom;
    @BindView(R.id.iv_shipin_sanjiao)
    ImageView ivShipinSanjiao;
    @BindView(R.id.tv_shipin_shipin)
    TextView tvShipinShipin;
    @BindView(R.id.tv_shipin_xiuxian)
    TextView tvShipinXiuxian;
    @BindView(R.id.view_shipin_one)
    View viewShipinOne;
    @BindView(R.id.tv_shipin_yepao)
    TextView tvShipinYepao;
    @BindView(R.id.view_shipin_two)
    View viewShipinTwo;
    @BindView(R.id.tv_shipin_name)
    TextView tvShipinName;
    @BindView(R.id.tv_shipin_genduo)
    TextView tvShipinGenduo;
    @BindView(R.id.tv_shipin_title)
    TextView tvShipinTitle;
    @BindView(R.id.tv_shipin_compere)
    TextView tvShipinCompere;
    @BindView(R.id.ll_zhibo)
    LinearLayout llZhibo;
    @BindView(R.id.vp_shouye)
    RecyclerView vpShouye;
    @BindView(R.id.iv_jingcai_sanjiao)
    ImageView ivJingcaiSanjiao;
    @BindView(R.id.tv_jingcai_shipin)
    TextView tvJingcaiShipin;
    @BindView(R.id.tv_jingcai_xiuxian)
    TextView tvJingcaiXiuxian;
    @BindView(R.id.view_jingcai_one)
    View viewJingcaiOne;
    @BindView(R.id.tv_jingcai_yepao)
    TextView tvJingcaiYepao;
    @BindView(R.id.view_jingcai_two)
    View viewJingcaiTwo;
    @BindView(R.id.tv_jingcai_name)
    TextView tvJingcaiName;
    @BindView(R.id.tv_jingcai_genduo)
    TextView tvJingcaiGenduo;
    @BindView(R.id.iv_jingcai1)
    ImageView ivJingcai1;
    @BindView(R.id.iv_jingcai2)
    ImageView ivJingcai2;
    @BindView(R.id.iv_jingcai3)
    ImageView ivJingcai3;
    @BindView(R.id.iv_huodong_sanjiao)
    ImageView ivHuodongSanjiao;
    @BindView(R.id.tv_huodong_shipin)
    TextView tvHuodongShipin;
    @BindView(R.id.tv_huodong_genduo)
    TextView tvHuodongGenduo;
    @BindView(R.id.tv_time_huodon1)
    TextView tvTimeHuodon1;
    @BindView(R.id.tv_name_huodon1)
    TextView tvNameHuodon1;
    @BindView(R.id.ll_huodon1)
    LinearLayout llHuodon1;
    @BindView(R.id.tv_time_huodon2)
    TextView tvTimeHuodon2;
    @BindView(R.id.tv_name_huodon2)
    TextView tvNameHuodon2;
    @BindView(R.id.ll_huodon2)
    LinearLayout llHuodon2;
    @BindView(R.id.tv_time_huodon3)
    TextView tvTimeHuodon3;
    @BindView(R.id.tv_name_huodon3)
    TextView tvNameHuodon3;
    @BindView(R.id.ll_huodon3)
    LinearLayout llHuodon3;
    @BindView(R.id.tv_time_huodon4)
    TextView tvTimeHuodon4;
    @BindView(R.id.tv_name_huodon4)
    TextView tvNameHuodon4;
    @BindView(R.id.ll_huodon4)
    LinearLayout llHuodon4;
    @BindView(R.id.iv_xinwen_sanjiao)
    ImageView ivXinwenSanjiao;
    @BindView(R.id.tv_xinwen_shipin)
    TextView tvXinwenShipin;
    @BindView(R.id.tv_xinwen_genduo)
    TextView tvXinwenGenduo;
    @BindView(R.id.tv_time_xinwen1)
    TextView tvTimeXinwen1;
    @BindView(R.id.tv_name_xinwen1)
    TextView tvNameXinwen1;
    @BindView(R.id.ll_xinwen1)
    LinearLayout llXinwen1;
    @BindView(R.id.tv_time_xinwen2)
    TextView tvTimeXinwen2;
    @BindView(R.id.tv_name_xinwen2)
    TextView tvNameXinwen2;
    @BindView(R.id.ll_xinwen2)
    LinearLayout llXinwen2;
    @BindView(R.id.tv_time_xinwen3)
    TextView tvTimeXinwen3;
    @BindView(R.id.tv_name_xinwen3)
    TextView tvNameXinwen3;
    @BindView(R.id.ll_xinwen3)
    LinearLayout llXinwen3;
    @BindView(R.id.tv_time_xinwen4)
    TextView tvTimeXinwen4;
    @BindView(R.id.tv_name_xinwen4)
    TextView tvNameXinwen4;
    @BindView(R.id.ll_xinwen4)
    LinearLayout llXinwen4;
    Unbinder unbinder;

    Myadapter myadapter;

    MainActivity  main;

    List<TagBean.DataBean> shipintags,jingcaitags;

    List<TagBean.DataBean> lists=new ArrayList<>();
    List<ShouyeShipinBean.DataBean> shipinData = new ArrayList<>();
    @BindView(R.id.iv_zhibo)
    ImageView ivZhibo;

    String  label_id="";


    public static  PlatfromShouye getPlatfromShouye(MainPlatfrom mainPlatfrom) {

        PlatfromShouye.mainPlatfrom = mainPlatfrom;

        return new PlatfromShouye();
    }

    @Override
    protected int getViewId() {
        return R.layout.frament_platfrom_shouye;
    }

    @Override
    public void init() {

        myadapter=new Myadapter();

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        vpShouye.setLayoutManager(linearLayoutManager);
        vpShouye.setHasFixedSize(true);
        vpShouye.setItemAnimator(new DefaultItemAnimator());
        vpShouye.setAdapter(myadapter);
        vpShouye.addItemDecoration(new SpaceItemDecoration(18));
        main= (MainActivity) App.mainActivity;

//        getTagData();
        getZhiboData();
        getShipinData();
        getJingcaiData();
        getHuodongData();
        getXinwenData();
        getTagShipin();
        getTagJingcai();
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

    @OnClick({R.id.iv_jingcai1, R.id.iv_jingcai2, R.id.iv_jingcai3, R.id.tv_shipin_xiuxian, R.id.tv_shipin_yepao, R.id.tv_shipin_name, R.id.tv_shipin_genduo, R.id.ll_zhibo, R.id.tv_jingcai_xiuxian, R.id.tv_jingcai_yepao, R.id.tv_jingcai_name, R.id.tv_jingcai_genduo, R.id.tv_huodong_genduo, R.id.ll_huodon1, R.id.ll_huodon2, R.id.ll_huodon3, R.id.ll_huodon4, R.id.tv_xinwen_genduo, R.id.ll_xinwen1, R.id.ll_xinwen2, R.id.ll_xinwen3, R.id.ll_xinwen4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shipin_xiuxian:
                restoreColor1(tvShipinXiuxian);
                if(tvShipinXiuxian!=null&&shipintags.size()>0)
                label_id=shipintags.get(0).getId();

                getShipinData();
//                PlatfromShipin.label_id=tags.get(0).getId();
//                mainPlatfrom.setCurrentItem(2);
                break;
            case R.id.tv_shipin_yepao:
                restoreColor1(tvShipinYepao);
                if(tvShipinXiuxian!=null&&shipintags.size()>1)
                label_id=shipintags.get(1).getId();

                getShipinData();
//                PlatfromShipin.label_id=tags.get(1).getId();
//                mainPlatfrom.setCurrentItem(2);
                break;
            case R.id.tv_shipin_name:
                restoreColor1(tvShipinName);
                if(tvShipinXiuxian!=null&&shipintags.size()>2)
                label_id=shipintags.get(2).getId();

                getShipinData();
//                PlatfromShipin.label_id=tags.get(2).getId();
//                mainPlatfrom.setCurrentItem(2);
                break;
            case R.id.tv_shipin_genduo:
                PlatfromShipin.label_id="";
                mainPlatfrom.setCurrentItem(2);
                break;
            case R.id.ll_zhibo:



//                main.createLiveRoom(true);

                break;
            case R.id.tv_jingcai_xiuxian:
                restoreColor2(tvJingcaiXiuxian);

                if(tvShipinXiuxian!=null&&jingcaitags.size()>0)
                    label_id=shipintags.get(0).getId();
//                if(tags!=null)
//                PlatfromWenderful.label_id=tags.get(0).getId();
//                mainPlatfrom.setCurrentItem(5);
                getJingcaiData();
                break;
            case R.id.tv_jingcai_yepao:
                restoreColor2(tvJingcaiYepao);
                if(tvShipinXiuxian!=null&&jingcaitags.size()>1)
                    label_id=shipintags.get(1).getId();
//                if(tags!=null)
//                PlatfromWenderful.label_id=tags.get(1).getId();
//                mainPlatfrom.setCurrentItem(5);
                getJingcaiData();
                break;
            case R.id.tv_jingcai_name:
                restoreColor2(tvJingcaiName);
                if(tvShipinXiuxian!=null&&jingcaitags.size()>2)
                    label_id=shipintags.get(2).getId();

//                if(tags!=null)
//                PlatfromWenderful.label_id=tags.get(2).getId();
//                mainPlatfrom.setCurrentItem(5);
                getJingcaiData();
                break;
            case R.id.tv_jingcai_genduo:
                PlatfromWenderful.label_id="";
                mainPlatfrom.setCurrentItem(5);

                break;
            case R.id.tv_huodong_genduo:
                mainPlatfrom.setCurrentItem(3);
                break;
            case R.id.iv_jingcai1:
                break;
            case R.id.iv_jingcai2:
                break;
            case R.id.iv_jingcai3:
                break;
            case R.id.ll_huodon1:
                break;
            case R.id.ll_huodon2:
                break;
            case R.id.ll_huodon3:
                break;
            case R.id.ll_huodon4:
                break;
            case R.id.tv_xinwen_genduo:
                mainPlatfrom.setCurrentItem(4);
                break;
            case R.id.ll_xinwen1:
                break;
            case R.id.ll_xinwen2:
                break;
            case R.id.ll_xinwen3:
                break;
            case R.id.ll_xinwen4:
                break;
        }
    }


    String   url ="http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
    /**
     * 用户可自定义过滤规则
     * @return
     */
    private boolean checkUrlValidate() {


        if(url.startsWith("http://flv")|| url.startsWith("http://pullhls") || url.startsWith("rtmp://")){
            ToastUtils.showShortToast("点播地址错误");
            return false;
        }
        if(url.endsWith(".mp4")|| url.endsWith(".flv") || url.endsWith(".m3u8")){
            return true;
        }
        ToastUtils.showShortToast("点播地址错误");
        return false;
    }




    //获取直播数据
    private void getZhiboData() {
        Map<String, Object> map = new HashMap<>();
        map.put("num", 1);
        map.put("label_id",label_id);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.patfrom.SHOUYE_ZHIBO, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("直播---请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final PZhiboBean bean = gson.fromJson(msg, PZhiboBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<PZhiboBean.DataBean> lists = bean.getData();
                                Log.e("list直播====size==",lists.size()+"");
                                if(ivZhibo!=null)
                                    Glide.with(getContext())
                                    .load(lists.get(0).getVideo_img())
                                    .asBitmap()
                                    .error(R.drawable.shouye_img_01_2x)
                                    .into(ivZhibo);

                                if(tvShipinTitle!=null)
                                tvShipinTitle.setText(lists.get(0).getTitle());
                                if(tvShipinCompere!=null)
                                tvShipinCompere.setText(lists.get(0).getAuthor_name());

//                                Log.e("TTTTTT",lists.get(0).getName()+lists.get(1).getName()+lists.get(2).getName());


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

    //获取视频数据
    private void getShipinData() {
        Map<String, Object> map = new HashMap<>();
        map.put("num", 6);
        map.put("label_id",label_id);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.patfrom.SHOUYE_SHIPING, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("视频----请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                Log.e("视频数据====",msg);
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final ShouyeShipinBean bean = gson.fromJson(msg, ShouyeShipinBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<ShouyeShipinBean.DataBean> lists = bean.getData();
                                Log.e("list视频====size==",lists.size()+"");
                                shipinData.clear();
                                shipinData.addAll(lists);
                                Log.e("平台首页的视频个数=====",shipinData.size()+"");
//                                Log.e("TTTTTT",lists.get(0).getName()+lists.get(1).getName()+lists.get(2).getName());
                                myadapter.notifyDataSetChanged();

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

    //获取精彩数据
    private void getJingcaiData() {
        Map<String, Object> map = new HashMap<>();
        map.put("num", 3);
        map.put("label_id",label_id);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.patfrom.SHOUYE_PHOTO, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("精彩----请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final ShouyeJingcaiBean bean = gson.fromJson(msg, ShouyeJingcaiBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<ShouyeJingcaiBean.DataBean> lists = bean.getData();
                                Log.e("list精彩====size==",lists.size()+"");
                                if(ivJingcai1!=null)
                                Picasso.with(getContext())
                                       .load(lists.get(0).getCover_photo())
                                       .into(ivJingcai1);
                                if(ivJingcai2!=null)
                                Picasso.with(getContext())
                                        .load(lists.get(1).getCover_photo())
                                        .into(ivJingcai2);
                                if(ivJingcai3!=null)
                                Picasso.with(getContext())
                                        .load(lists.get(2).getCover_photo())
                                        .into(ivJingcai3);

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

    //获取活动数据
    private void getHuodongData() {
        Map<String, Object> map = new HashMap<>();
        map.put("num", 4);
        map.put("label_id",label_id);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.patfrom.SHOUYE_HUODONG, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("活动-----请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final ShouyeHuodongData bean = gson.fromJson(msg, ShouyeHuodongData.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<ShouyeHuodongData.DataBean> lists = bean.getData();
                                Log.e("list活动====size==",lists.size()+"");
                                if(tvTimeHuodon1!=null)
                                tvTimeHuodon1.setText(lists.get(0).getCreate_time());
                                if(tvTimeHuodon2!=null)
                                tvTimeHuodon2.setText(lists.get(1).getCreate_time());
                                if(tvTimeHuodon3!=null)
                                tvTimeHuodon3.setText(lists.get(2).getCreate_time());
                                if(tvTimeHuodon4!=null)
                                tvTimeHuodon4.setText(lists.get(3).getCreate_time());

                                if(tvNameHuodon1!=null)
                                tvNameHuodon1.setText(lists.get(0).getTitle());
                                if(tvNameHuodon2!=null)
                                tvNameHuodon2.setText(lists.get(1).getTitle());
                                if(tvNameHuodon3!=null)
                                tvNameHuodon3.setText(lists.get(2).getTitle());
                                if(tvNameHuodon4!=null)
                                tvNameHuodon4.setText(lists.get(3).getTitle());


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

    //获取新闻数据
    private void getXinwenData() {
        Map<String, Object> map = new HashMap<>();
        map.put("num", 4);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.patfrom.SHOUYE_XINWEN, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("新闻----请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final ShouyeXinwenBean bean = gson.fromJson(msg, ShouyeXinwenBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<ShouyeXinwenBean.DataBean> lists = bean.getData();
                                Log.e("list新闻====size==",lists.size()+"");
                                if(tvTimeXinwen1!=null)
                                tvTimeXinwen1.setText(lists.get(0).getAdd_time());
                                if(tvTimeXinwen2!=null)
                                tvTimeXinwen2.setText(lists.get(1).getAdd_time());
                                if(tvTimeXinwen3!=null)
                                tvTimeXinwen3.setText(lists.get(2).getAdd_time());
                                if(tvTimeXinwen4!=null)
                                tvTimeXinwen4.setText(lists.get(3).getAdd_time());

                                if(tvNameXinwen1!=null)
                                tvNameXinwen1.setText(lists.get(0).getTitle());
                                if(tvNameXinwen2!=null)
                                tvNameXinwen2.setText(lists.get(1).getTitle());
                                if(tvNameXinwen3!=null)
                                tvNameXinwen3.setText(lists.get(2).getTitle());
                                if(tvNameXinwen4!=null)
                                tvNameXinwen4.setText(lists.get(3).getTitle());


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

    //获取视频类型
    private void getTagShipin() {
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
                                shipintags=lists;
                                if(shipintags.isEmpty()||shipintags==null){
                                    return;
                                }
                                if(tvShipinXiuxian!=null&&shipintags.size()>0){
                                    tvShipinXiuxian.setText(shipintags.get(0).getName());
                                }


                                if(tvShipinYepao!=null&&shipintags.size()>1)
                                    tvShipinYepao.setText(shipintags.get(1).getName());


                                if(tvShipinName!=null&&shipintags.size()>2)
                                    tvShipinName.setText(shipintags.get(2).getName());

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
    //获取精彩类型
    private void getTagJingcai() {
        Map<String, Object> map = new HashMap<>();
        map.put("type","picture");
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
                                jingcaitags=lists;
                                if(jingcaitags.isEmpty()||jingcaitags==null){
                                    return;
                                }

                                if(tvJingcaiXiuxian!=null&&jingcaitags.size()>0)
                                    tvJingcaiXiuxian.setText(jingcaitags.get(0).getName());


                                if(tvJingcaiYepao!=null&&jingcaitags.size()>1)
                                    tvJingcaiYepao.setText(jingcaitags.get(1).getName());

                                if(tvJingcaiName!=null&&jingcaitags.size()>2)
                                    tvJingcaiName.setText(jingcaitags.get(2).getName());
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

    //恢复颜色1
    private void restoreColor1(TextView view) {
        tvShipinXiuxian.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvShipinYepao.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvShipinName.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        view.setTextColor(getResources().getColor(R.color.black));
    }

    //恢复颜色2
    private void restoreColor2(TextView view) {
        tvJingcaiXiuxian.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvJingcaiYepao.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvJingcaiName.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        view.setTextColor(getResources().getColor(R.color.black));
    }



//    private void ZhiboData() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("label_id",1);
//        map.put("num",1);
//        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
//        HTTPUtils.getNetDATA(API.BaseUrl + API.patfrom.SHOUYE_ZHIBO, map, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                ToolKit.runOnMainThreadSync(new Runnable() {
//                    @Override
//                    public void run() {
//                        ToastUtils.showShortToast("请检查网络");
//
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                String msg = response.body().string();
//                if (response.code() == 200) {
//                    Gson gson = new Gson();
//                    final NewsBean bean = gson.fromJson(msg, NewsBean.class);
//
//                    if (bean.getCode() == 200) {
//                        ToolKit.runOnMainThreadSync(new Runnable() {
//                            @Override
//                            public void run() {
//                                if(tag!=1){
//                                    rlvNews.setLoadMoreComplete();
//                                }
//                                List<NewsBean.DataBean> lists = bean.getData();
//                                data.addAll(lists);
//                                myadapter.notifyDataSetChanged();
//                            }
//                        });
//                    } else {
//                        ToolKit.runOnMainThreadSync(new Runnable() {
//                            @Override
//                            public void run() {
//                                ToastUtils.showShortToast(bean.getMsg());
//                            }
//                        });
//                    }
//                } else {
//                    ToolKit.runOnMainThreadSync(new Runnable() {
//                        @Override
//                        public void run() {
//                            ToastUtils.showShortToast(response.message());
//                        }
//                    });
//                }
//            }
//        });
//    }


    class Myadapter extends RecyclerView.Adapter<Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder {

            ImageView iv_shipin_img;

            TextView tv_shipin_title,tv_shipin_author;
            View view;

            public MyHolder(View itemView) {
                super(itemView);
                view=itemView;
                iv_shipin_img= (ImageView) itemView.findViewById(R.id.iv_shipin_img);
                tv_shipin_title= (TextView) itemView.findViewById(R.id.tv_shipin_title);
                tv_shipin_author= (TextView) itemView.findViewById(R.id.tv_shipin_author);

            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platfrom_shouye, parent, false);
            MyHolder holder = new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {

            holder.tv_shipin_title.setText(shipinData.get(position).getVideo_name());
            holder.tv_shipin_author.setText(shipinData.get(position).getAuthor_name());
            Picasso.with(getContext())
                    .load(shipinData.get(position).getVideo_img())
                    .into(holder.iv_shipin_img);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("播放---","shi频");
                    //check
                    if(checkUrlValidate()) {
                        VideoPlayerActivity.startActivity(getContext(), shipinData.get(position).getVideo_id(),shipinData.get(position).getVideo_url(),shipinData.get(position).getVideo_img());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return shipinData.size();
        }
    }

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            /*outRect.left = space;
            outRect.bottom = 0;*/

            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 3 == 0) {
                outRect.left = 0;
                outRect.right = 10;
            }
            if ((parent.getChildLayoutPosition(view) - 1) % 3 == 0) {
                outRect.left = 5;
                outRect.right = 5;
            }
            if ((parent.getChildLayoutPosition(view) - 2) % 3 == 0) {
                outRect.left = 10;
                outRect.right = 0;
            }
        }

    }
}
