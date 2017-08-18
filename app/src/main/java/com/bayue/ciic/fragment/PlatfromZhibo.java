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
import com.bayue.ciic.base.BaseFragment;
import com.bayue.ciic.bean.PZhiboBean;
import com.bayue.ciic.bean.ShouyeZhiboBean;
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

public class PlatfromZhibo extends BaseFragment {


    @BindView(R.id.iv_shipin_sanjiao)
    ImageView ivShipinSanjiao;
    @BindView(R.id.tv_zhibo)
    TextView tvZhibo;
    @BindView(R.id.tv_zhibo_xiuxian)
    TextView tvZhiboXiuxian;
    @BindView(R.id.view_zhibo_one)
    View viewZhiboOne;
    @BindView(R.id.tv_zhibo_yepao)
    TextView tvZhiboYepao;
    @BindView(R.id.view_zhibo_two)
    View viewZhiboTwo;
    @BindView(R.id.tv_zhibo_name)
    TextView tvZhiboName;

    Unbinder unbinder;

    List<PZhiboBean.DataBean> data = new ArrayList<>();
    Myadapter myadapter;


    String label_id = "";
    @BindView(R.id.tv_zhibo_all)
    TextView tvZhiboAll;
    @BindView(R.id.vp_zhibo)
    RecyclerView vpZhibo;
    @BindView(R.id.swipe)
    SwipyRefreshLayout swipe;

    private int tag = 1;

    MainActivity main;

    boolean isp=true;
    public PlatfromZhibo(){}
    public PlatfromZhibo(boolean isp){
        this.isp=isp;
    }

    @Override
    protected int getViewId() {
        return R.layout.frament_platfrom_zhibo;
    }

    @Override
    public void init() {
        main = (MainActivity) App.mainActivity;
        myadapter = new Myadapter();

        vpZhibo.setLayoutManager(new GridLayoutManager(getContext(), 2));
        vpZhibo.setHasFixedSize(true);
        vpZhibo.setItemAnimator(new DefaultItemAnimator());
        vpZhibo.setAdapter(myadapter);
        vpZhibo.addItemDecoration(new SpaceItemDecoration(18));



        swipe.setDirection(SwipyRefreshLayoutDirection.BOTH);

        swipe.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    tag=1;
                } else {
                    tag++;
                }

                if(isp){
                    getZhiboData();
                }else {
                    getZhiboData2();
                }
            }
        });


        /*if(isp){
            getZhiboData();
        }else {

        }*/
        getTagZhibo();

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
        if(isp){
            getZhiboData();
        }else {
            getZhiboData2();
        }
    }

    @OnClick({R.id.tv_zhibo_xiuxian, R.id.tv_zhibo_yepao, R.id.tv_zhibo_name, R.id.tv_zhibo_all})
    public void onViewClicked(View view) {
        tag = 1;
        switch (view.getId()) {
            case R.id.tv_zhibo_xiuxian:
                restoreColor1(tvZhiboXiuxian);
                label_id = main.lists.get(0).getId();

                break;
            case R.id.tv_zhibo_yepao:
                restoreColor1(tvZhiboYepao);
                label_id = main.lists.get(1).getId();
                break;
            case R.id.tv_zhibo_name:
                restoreColor1(tvZhiboName);
                label_id = main.lists.get(2).getId();
                break;
            case R.id.tv_zhibo_all:
                restoreColor1(tvZhiboAll);
                label_id = "";
                break;
        }
        if (isp) {
            getZhiboData();
        } else {
            getZhiboData2();
        }

    }



    class Myadapter extends RecyclerView.Adapter<Myadapter.MyHolder> {


        public class MyHolder extends RecyclerView.ViewHolder {
            ImageView iv_video_img,iv_video_is;
            TextView tv_video_name, tv_video_author, tv_video_number;
            View view;

            public MyHolder(View itemView) {
                super(itemView);
                view = itemView;
                iv_video_is= (ImageView) itemView.findViewById(R.id.iv_video_is);
                iv_video_img = (ImageView) itemView.findViewById(R.id.iv_video_img);
                tv_video_name = (TextView) itemView.findViewById(R.id.tv_video_name);
                tv_video_author = (TextView) itemView.findViewById(R.id.tv_video_author);
                tv_video_number = (TextView) itemView.findViewById(R.id.tv_video_number);

            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platfrom_zhibo, parent, false);
            MyHolder holder = new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            Glide.with(getContext())
                    .load(data.get(position).getVideo_img())
                    .asBitmap()
                    .into(holder.iv_video_img);
            if(data.get(position).getStatus().equals("1")||data.get(position).getStatus().equals("3")){
                holder.iv_video_is.setBackgroundResource(R.mipmap.zhibo_1x);

            }else {
                holder.iv_video_is.setBackgroundResource(R.mipmap.weikaibo);
            }
            holder.tv_video_name.setText(data.get(position).getTitle());
            holder.tv_video_author.setText(data.get(position).getAuthor());
            holder.tv_video_number.setText(data.get(position).getClick_count() + "人在看");
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("PlatfromZhibo>>>>", "观看直播");
                    main.createLiveRoom(data.get(position).getPull_url(), data.get(position).getRoom_id(), true);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
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
//            outRect.right=10;
            outRect.bottom = 20;

            //由于每行都只有2个，所以第一个都是2的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.left = 0;
                outRect.right = 10;
            }
            Log.e(parent.getChildLayoutPosition(view) + "", "???????");
            if ((parent.getChildLayoutPosition(view) - 1) % 2 == 0) {
                outRect.left = 10;
                outRect.right = 0;

            }

        }

    }

    //恢复颜色1
    private void restoreColor1(TextView view) {
        tvZhiboAll.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvZhiboXiuxian.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvZhiboYepao.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvZhiboName.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        view.setTextColor(getResources().getColor(R.color.black));
    }


    private void getZhiboData() {
        if (lists != null)
            for (int i = 0; i < lists.size(); i++) {
                if (label_id.equals(lists.get(i).getId())) {
                    switch (i) {
                        case 0:
                            restoreColor1(tvZhiboXiuxian);
                            break;
                        case 1:
                            restoreColor1(tvZhiboYepao);
                            break;
                        case 2:
                            restoreColor1(tvZhiboName);
                            break;
                    }
                }
            }
        if (label_id.equals("") || label_id.isEmpty()) {
            restoreColor1(tvZhiboAll);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("p", tag);
        map.put("listRows", 6);
        map.put("label_id", label_id);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        Log.e("tag====" + tag, "Id=-----=" + label_id);
        HTTPUtils.getNetDATA(API.BaseUrl + API.patfrom.ZHIBO, map, new Callback() {
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
                Log.e(">>>>>>", msg);
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final PZhiboBean bean = gson.fromJson(msg, PZhiboBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<PZhiboBean.DataBean> lists = bean.getData();
                                if (tag == 1) {
                                    data.clear();
                                }

                                data.addAll(lists);
                                Log.e("data=直播===" + "tag====" + tag, "-------" + data.size());
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

    //获取企业直播数据
    private void getZhiboData2() {
        Map<String, Object> map = new HashMap<>();
        if (lists != null)
            for (int i = 0; i < lists.size(); i++) {
                if (label_id.equals(lists.get(i).getId())) {
                    switch (i) {
                        case 0:
                            restoreColor1(tvZhiboXiuxian);
                            break;
                        case 1:
                            restoreColor1(tvZhiboYepao);
                            break;
                        case 2:
                            restoreColor1(tvZhiboName);
                            break;
                    }
                }
            }
        if (label_id.equals("") || label_id.isEmpty()) {
            restoreColor1(tvZhiboAll);
        }
        map.put("row", 8);
        map.put("p",tag);
        map.put("label",label_id);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.Company.SHOUYE_ZHIBO, map, new Callback() {
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
                                if (tag == 1) {
                                    data.clear();
                                }

                                data.addAll(lists);

//                                Log.e("TTTTTT",lists.get(0).getName()+lists.get(1).getName()+lists.get(2).getName());

                                myadapter.notifyDataSetChanged();
                                swipe.setRefreshing(false);
                                Log.e("data=企业直播===", "-------" + data.size());

                            }
                        });
                    }else {
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

    //获取直播类型
    private void getTagZhibo() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "direct");
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

                                if (tvZhiboXiuxian != null) {
                                    tvZhiboXiuxian.setText(lists.get(0).getName());
                                }


                                if (tvZhiboYepao != null)
                                    tvZhiboYepao.setText(lists.get(1).getName());


                                if (tvZhiboName != null)
                                    tvZhiboName.setText(lists.get(2).getName());

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
}
