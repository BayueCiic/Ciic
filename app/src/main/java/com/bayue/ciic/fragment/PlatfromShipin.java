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
import android.widget.TextView;

import com.androidkun.callback.PullToRefreshListener;
import com.bayue.ciic.App;
import com.bayue.ciic.MainActivity;
import com.bayue.ciic.R;
import com.bayue.ciic.activity.live.activity.VideoPlayerActivity;
import com.bayue.ciic.base.BaseFragment;
import com.bayue.ciic.bean.PShipinBean;
import com.bayue.ciic.bean.PZhiboBean;
import com.bayue.ciic.bean.Qshipin;
import com.bayue.ciic.bean.ShipinBean;
import com.bayue.ciic.bean.TagBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.bayue.ciic.view.swipe.SwipyRefreshLayout;
import com.bayue.ciic.view.swipe.SwipyRefreshLayoutDirection;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

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

public class PlatfromShipin extends BaseFragment {


    @BindView(R.id.iv_shipin_sanjiao)
    ImageView ivShipinSanjiao;
    @BindView(R.id.tv_shipin)
    TextView tvShipin;
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

    Unbinder unbinder;
    List<PShipinBean.DataBean> data = new ArrayList<>();
    List<Qshipin.DataBean> datas=new ArrayList<>();
    Myadapter myadapter;
    Myadapter2 myadapter2;
    MainActivity main;

    public static String label_id = "";
    @BindView(R.id.vp_shipin)
    RecyclerView vpShipin;
    @BindView(R.id.swipe)
    SwipyRefreshLayout swipe;
    private int tag = 1;


    @BindView(R.id.tv_shipin_all)
    TextView tvShipinAll;

    boolean isp = true;

    public PlatfromShipin() {
    }

    public PlatfromShipin(boolean isp) {
        this.isp = isp;
    }

    @Override
    protected int getViewId() {
        return R.layout.frament_platfrom_shipin;
    }

    @Override
    public void init() {
        main = (MainActivity) App.mainActivity;
        myadapter=new Myadapter();
        myadapter2=new Myadapter2();
        vpShipin.setLayoutManager(new GridLayoutManager(getContext(), 2));
        vpShipin.setHasFixedSize(true);
        vpShipin.setItemAnimator(new DefaultItemAnimator());
        if(isp){
            vpShipin.setAdapter(myadapter);
        }else {
            vpShipin.setAdapter(myadapter2);
        }
        vpShipin.addItemDecoration(new SpaceItemDecoration(18));
        swipe.setDirection(SwipyRefreshLayoutDirection.BOTH);

        swipe.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    tag=1;
                } else {
                    tag++;
                }
                if (isp) {
                    getZhiboData();
                } else {
                    getShipinData();
                }
            }
        });
        getTagShipin();

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

    @Override
    public void onStart() {
        super.onStart();
        tag = 1;
        if (isp) {
            getZhiboData();
        } else {
            getShipinData();
        }
    }

    private void getZhiboData() {
        if (lists != null)
            for (int i = 0; i < lists.size(); i++) {
                if (label_id.equals(lists.get(i).getId())) {
                    switch (i) {
                        case 0:
                            restoreColor1(tvShipinXiuxian);
                            break;
                        case 1:
                            restoreColor1(tvShipinYepao);
                            break;
                        case 2:
                            restoreColor1(tvShipinName);
                            break;
                    }
                }
            }
        if (label_id.equals("") || label_id.isEmpty()) {
            restoreColor1(tvShipinAll);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("p", tag);
        map.put("listRows", 8);
        map.put("label_id", label_id);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.patfrom.SHIPIN, map, new Callback() {
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
                Log.e("视频===", msg);
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final PShipinBean bean = gson.fromJson(msg, PShipinBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<PShipinBean.DataBean> lists = bean.getData();
                                if (tag == 1) {
                                    data.clear();
                                }
                                data.addAll(lists);
//                                myadapter.notifyAll();
//                                Log.e("TTTTTT",lists.get(0).getName()+lists.get(1).getName()+lists.get(2).getName());
                                myadapter.notifyDataSetChanged();
                                swipe.setRefreshing(false);

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

    //获取企业视频数据
    private void getShipinData() {
        Map<String, Object> map = new HashMap<>();
        if (lists != null)
            for (int i = 0; i < lists.size(); i++) {
                if (label_id.equals(lists.get(i).getId())) {
                    switch (i) {
                        case 0:
                            restoreColor1(tvShipinXiuxian);
                            break;
                        case 1:
                            restoreColor1(tvShipinYepao);
                            break;
                        case 2:
                            restoreColor1(tvShipinName);
                            break;
                    }
                }
            }
        if (label_id.equals("") || label_id.isEmpty()) {
            restoreColor1(tvShipinAll);
        }

        map.put("row", 8);
        map.put("p",tag);
        map.put("label",label_id);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.Company.SHOUYE_SHIPING, map, new Callback() {
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
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final Qshipin bean = gson.fromJson(msg, Qshipin.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<Qshipin.DataBean> lists = bean.getData();
                                if(lists==null)
                                    return;
                                Log.e("list视频====size==", lists.size() + "");
                                datas.clear();
                                datas.addAll(lists);
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

    List<TagBean.DataBean> lists = new ArrayList<>();

    //获取视频类型
    private void getTagShipin() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "video");
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
                                if (lists.isEmpty() || lists == null) {
                                    return;
                                }

                                if (tvShipinXiuxian != null) {
                                    tvShipinXiuxian.setText(lists.get(0).getName());
                                }


                                if (tvShipinYepao != null)
                                    tvShipinYepao.setText(lists.get(1).getName());


                                if (tvShipinName != null)
                                    tvShipinName.setText(lists.get(2).getName());

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
        tvShipinAll.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvShipinName.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvShipinXiuxian.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvShipinYepao.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        view.setTextColor(getResources().getColor(R.color.black));
    }

    @OnClick({R.id.tv_shipin_xiuxian, R.id.tv_shipin_yepao, R.id.tv_shipin_name, R.id.tv_shipin_all})
    public void onViewClicked(View view) {
        tag = 1;
        switch (view.getId()) {
            case R.id.tv_shipin_xiuxian:
                restoreColor1(tvShipinXiuxian);
                label_id = main.lists.get(0).getId();

                break;
            case R.id.tv_shipin_yepao:
                restoreColor1(tvShipinYepao);
                label_id = main.lists.get(1).getId();
                break;
            case R.id.tv_shipin_name:
                restoreColor1(tvShipinName);
                label_id = main.lists.get(2).getId();
                break;
            case R.id.tv_shipin_all:
                restoreColor1(tvShipinAll);
                label_id = "";
                break;
        }
        if (isp) {
            getZhiboData();
        } else {
            getShipinData();
        }
    }


    class Myadapter extends RecyclerView.Adapter<Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder {
            ImageView iv_video_img;
            TextView tv_video_time, tv_video_author, tv_video_name;

            public MyHolder(View itemView) {
                super(itemView);

                iv_video_img = (ImageView) itemView.findViewById(R.id.iv_video_img);
                tv_video_time = (TextView) itemView.findViewById(R.id.tv_video_time);
                tv_video_author = (TextView) itemView.findViewById(R.id.tv_video_author);
                tv_video_name = (TextView) itemView.findViewById(R.id.tv_video_name);
            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_shoucang, parent, false);
            MyHolder holder = new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            Glide.with(getContext())
                    .load(data.get(position).getVideo_img())
                    .asBitmap()
                    .into(holder.iv_video_img);
            holder.tv_video_time.setText(data.get(position).getVideo_duration());
            holder.tv_video_name.setText(data.get(position).getVideo_name());
            holder.tv_video_author.setText(data.get(position).getAuthor_name());
            holder.iv_video_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoPlayerActivity.startActivity(getContext(), data.get(position).getVideo_id(),data.get(position).getVideo_url(),data.get(position).getVideo_img());
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class Myadapter2 extends RecyclerView.Adapter<Myadapter2.MyHolder2> {

        public class MyHolder2 extends RecyclerView.ViewHolder {
            ImageView iv_video_img;
            TextView tv_video_time, tv_video_author, tv_video_name;

            public MyHolder2(View itemView) {
                super(itemView);

                iv_video_img = (ImageView) itemView.findViewById(R.id.iv_video_img);
                tv_video_time = (TextView) itemView.findViewById(R.id.tv_video_time);
                tv_video_author = (TextView) itemView.findViewById(R.id.tv_video_author);
                tv_video_name = (TextView) itemView.findViewById(R.id.tv_video_name);
            }
        }

        @Override
        public MyHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_shoucang, parent, false);
            MyHolder2 holder = new MyHolder2(v);
            return holder;
        }



        @Override
        public void onBindViewHolder(Myadapter2.MyHolder2 holder, final int position) {
            Glide.with(getContext())
                    .load(datas.get(position).getVideo_img())
                    .asBitmap()
                    .into(holder.iv_video_img);
            holder.tv_video_time.setText(datas.get(position).getVideo_duration());
            holder.tv_video_name.setText(datas.get(position).getVideo_name());
            holder.tv_video_author.setText(datas.get(position).getAuthor());
            holder.iv_video_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoPlayerActivity.startActivity(getContext(), datas.get(position).getVideo_id(),datas.get(position).getVideo_url(),datas.get(position).getVideo_img());
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
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
//            outRect.left = 0;
            outRect.bottom = 20;

            //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.left = 0;
                outRect.right = 10;
            }
            if ((parent.getChildLayoutPosition(view) - 1) % 2 == 0) {
                outRect.left = 10;
                outRect.right = 0;

            }

        }

    }
}
