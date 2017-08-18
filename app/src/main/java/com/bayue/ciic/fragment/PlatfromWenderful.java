package com.bayue.ciic.fragment;

import android.content.Intent;
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

import com.androidkun.callback.PullToRefreshListener;
import com.bayue.ciic.App;
import com.bayue.ciic.MainActivity;
import com.bayue.ciic.R;
import com.bayue.ciic.activity.PhotoAlbum;
import com.bayue.ciic.base.BaseFragment;
import com.bayue.ciic.bean.PWenderfulBean;
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

public class PlatfromWenderful extends BaseFragment {


    @BindView(R.id.iv_wonderful_sanjiao)
    ImageView ivWonderfulSanjiao;
    @BindView(R.id.tv_wonderful_shipin)
    TextView tvWonderfulShipin;
    @BindView(R.id.tv_wonderful_xiuxian)
    TextView tvWonderfulXiuxian;
    @BindView(R.id.view_wonderful_one)
    View viewWonderfulOne;
    @BindView(R.id.tv_wonderful_yepao)
    TextView tvWonderfulYepao;
    @BindView(R.id.view_wonderful_two)
    View viewWonderfulTwo;
    @BindView(R.id.tv_wonderful_name)
    TextView tvWonderfulName;


    Unbinder unbinder;

    @BindView(R.id.tv_wonderful_all)
    TextView tvWonderfulAll;
    @BindView(R.id.vp_wonderful)
    RecyclerView vpWonderful;
    @BindView(R.id.swipe)
    SwipyRefreshLayout swipe;
    private int tag = 1;
    Myadapter myadapter;
    List<PWenderfulBean.DataBean> data = new ArrayList<>();
    MainActivity main;
    public static String label_id = "";

    @Override
    protected int getViewId() {
        return R.layout.frament_platfrom_wonderful;
    }

    @Override
    public void init() {
        main = (MainActivity) App.mainActivity;
        myadapter = new Myadapter();

        vpWonderful.setLayoutManager(new GridLayoutManager(getContext(), 2));
        vpWonderful.setHasFixedSize(true);
        vpWonderful.setItemAnimator(new DefaultItemAnimator());
        vpWonderful.setAdapter(myadapter);
        vpWonderful.addItemDecoration(new SpaceItemDecoration(18));

        tvWonderfulShipin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        swipe.setDirection(SwipyRefreshLayoutDirection.BOTH);

        swipe.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    tag=1;
                } else {
                    tag++;
                }
                getNewsData();
            }
        });
        getTagWenderful();
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
        getNewsData();
    }

    private void getNewsData() {
        if (lists != null)
            for (int i = 0; i < lists.size(); i++) {
                if (label_id.equals(lists.get(i).getId())) {
                    switch (i) {
                        case 0:
                            restoreColor1(tvWonderfulXiuxian);
                            break;
                        case 1:
                            restoreColor1(tvWonderfulYepao);
                            break;
                        case 2:
                            restoreColor1(tvWonderfulName);
                            break;
                    }
                }
            }
        if (label_id.equals("") || label_id.isEmpty()) {
            restoreColor1(tvWonderfulAll);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("p", tag);
        map.put("listRows", 8);
        map.put("label_id", label_id);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.patfrom.JINGCAI, map, new Callback() {
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
                    final PWenderfulBean bean = gson.fromJson(msg, PWenderfulBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<PWenderfulBean.DataBean> lists = bean.getData();
                                if (tag == 1) {
                                    data.clear();
                                }
                                data.addAll(lists);
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

    List<TagBean.DataBean> lists = new ArrayList<>();
    List<TagBean.DataBean> tags = new ArrayList<>();
    ;

    //获取精彩类型
    private void getTagWenderful() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "picture");
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
                                tags = lists;
                                if (lists.isEmpty() || lists == null) {
                                    return;
                                }

                                if (tvWonderfulXiuxian != null)
                                    tvWonderfulXiuxian.setText(lists.get(0).getName());


                                if (tvWonderfulYepao != null)
                                    tvWonderfulYepao.setText(lists.get(1).getName());

                                if (tvWonderfulName != null)
                                    tvWonderfulName.setText(lists.get(2).getName());
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
        Log.e("sdfadsf", ">>>>>>>");
        tvWonderfulAll.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvWonderfulName.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvWonderfulXiuxian.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvWonderfulYepao.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        view.setTextColor(getResources().getColor(R.color.black));
    }

    @OnClick({R.id.tv_wonderful_xiuxian, R.id.tv_wonderful_yepao, R.id.tv_wonderful_name, R.id.tv_wonderful_all})
    public void onViewClicked(View view) {
        tag = 1;
        switch (view.getId()) {
            case R.id.tv_wonderful_xiuxian:
                restoreColor1(tvWonderfulXiuxian);
                label_id = main.lists.get(0).getId();
                break;
            case R.id.tv_wonderful_yepao:
                restoreColor1(tvWonderfulYepao);
                label_id = main.lists.get(1).getId();
                break;
            case R.id.tv_wonderful_name:
                restoreColor1(tvWonderfulName);
                label_id = main.lists.get(2).getId();
                break;
            case R.id.tv_wonderful_all:
                restoreColor1(tvWonderfulAll);
                label_id = "";
                break;
        }
        getNewsData();
    }

    class Myadapter extends RecyclerView.Adapter<Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder {
            LinearLayout ll_item;

            ImageView iv_wenderful_img;
            TextView tv_wenderful_title, tv_wenderful_author, tv_wenderful_time;

            public MyHolder(View itemView) {
                super(itemView);
                ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);

                iv_wenderful_img = (ImageView) itemView.findViewById(R.id.iv_wenderful_img);

                tv_wenderful_title = (TextView) itemView.findViewById(R.id.tv_wenderful_title);
                tv_wenderful_author = (TextView) itemView.findViewById(R.id.tv_wenderful_author);
                tv_wenderful_time = (TextView) itemView.findViewById(R.id.tv_wenderful_time);

            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platfrom_wonderful, parent, false);
            MyHolder holder = new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), PhotoAlbum.class));
                }
            });
            Glide.with(getContext())
                    .load(data.get(position).getCover_photo())
                    .asBitmap()
                    .into(holder.iv_wenderful_img);
            holder.tv_wenderful_title.setText(data.get(position).getAlbum_name());
            holder.tv_wenderful_author.setText(data.get(position).getAuthor_name());
            holder.tv_wenderful_time.setText(data.get(position).getCreate_time());
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
            outRect.bottom = 20;

            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.left = 0;
                outRect.right =10;
            }
            if ((parent.getChildLayoutPosition(view) - 1) % 2 == 0) {
                outRect.left = 10;
                outRect.right = 0;

            }

        }

    }
}
