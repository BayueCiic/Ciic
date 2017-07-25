package com.bayue.ciic.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseFragment;
import com.bayue.ciic.bean.PXinwenBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/3.
 */

public class PlatfromNews extends BaseFragment {


    @BindView(R.id.iv_news_sanjiao)
    ImageView ivNewsSanjiao;
    @BindView(R.id.tv_news)
    TextView tvNews;

    Unbinder unbinder;
    private int tag=1;
    Myadapter myadapter;
    List<PXinwenBean.DataBean> data = new ArrayList<>();
    @BindView(R.id.vp_news)
    PullToRefreshRecyclerView vpNews;

    @Override
    protected int getViewId() {
        return R.layout.frament_platfrom_news;
    }

    @Override
    public void init() {
        myadapter = new Myadapter();
        vpNews.setLayoutManager(new LinearLayoutManager(getContext()));
        vpNews.setHasFixedSize(true);
        vpNews.setItemAnimator(new DefaultItemAnimator());
        vpNews.setAdapter(myadapter);
//        vpZhibo.addItemDecoration(new SpaceItemDecoration(18));

        vpNews.setPullRefreshEnabled(false);
        vpNews.setLoadingMoreEnabled(true);
        vpNews.displayLastRefreshTime(true);

        vpNews.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                vpNews.setLoadMoreComplete();
            }

            @Override
            public void onLoadMore() {
                tag++;
                getNewsData();
            }
        });
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
        tag=1;
        getNewsData();
    }

    private void getNewsData() {
        Map<String, Object> map = new HashMap<>();
        map.put("p", tag);
        map.put("listRows", 20);
        map.put("label_id", "");
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.patfrom.XINWEN, map, new Callback() {
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
                    final PXinwenBean bean = gson.fromJson(msg, PXinwenBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<PXinwenBean.DataBean> lists = bean.getData();
                                if (tag == 1) {
                                    data.clear();
                                } else {
                                    if (vpNews != null)
                                        vpNews.setLoadMoreComplete();
                                }
                                data.addAll(lists);
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

    class Myadapter extends RecyclerView.Adapter<Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder {
            ImageView iv_news_img;
            TextView tv_news_title, tv_news_txt, tv_news_time, tv_news_author;

            public MyHolder(View itemView) {
                super(itemView);
                iv_news_img = (ImageView) itemView.findViewById(R.id.iv_news_img);
                tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
                tv_news_txt = (TextView) itemView.findViewById(R.id.tv_news_txt);
                tv_news_time = (TextView) itemView.findViewById(R.id.tv_news_time);
                tv_news_author = (TextView) itemView.findViewById(R.id.tv_news_author);
            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platfrom_news, parent, false);
            MyHolder holder = new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

            Glide.with(getContext())
                    .load(data.get(position).getNews_img())
                    .asBitmap()
                    .into(holder.iv_news_img);
            holder.tv_news_title.setText(data.get(position).getTitle());
            holder.tv_news_txt.setText(data.get(position).getDescribe());
            holder.tv_news_author.setText(data.get(position).getAuthor_name());
            holder.tv_news_time.setText(data.get(position).getAdd_time());
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
            outRect.left = 0;
            outRect.bottom = 30;

            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.left = 0;
//                outRect.right = 10;
            }
            if ((parent.getChildLayoutPosition(view) - 1) % 2 == 0) {
                outRect.left = 40;
//                outRect.right = 0;

            }

        }

    }
}
