package com.bayue.ciic.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.bean.NewsBean;
import com.bayue.ciic.bean.XxBean;
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
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/28.
 */

public class GerenNews extends BaseActivity {

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
    @BindView(R.id.rlv_news)
    PullToRefreshRecyclerView rlvNews;

    List<NewsBean.DataBean> data=new ArrayList<>();

    Myadapter myadapter;
    @Override
    protected void setTheme() {
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_geren_news;
    }

    @Override
    protected void initViews() {
        getData();
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("新闻管理");
        ivShezhi.setVisibility(View.INVISIBLE);

        myadapter=new Myadapter();
        rlvNews.setLayoutManager(new LinearLayoutManager(this));
        rlvNews.setHasFixedSize(true);
        rlvNews.setItemAnimator(new DefaultItemAnimator());
        rlvNews.setAdapter(myadapter);

        rlvNews.setPullRefreshEnabled(false);
        rlvNews.setLoadingMoreEnabled(true);
        rlvNews.displayLastRefreshTime(true);

        rlvNews.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                rlvNews.setLoadMoreComplete();
            }

            @Override
            public void onLoadMore() {
                tag++;
                getData();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.fl_goback)
    public void onViewClicked() {
        finish();
    }
    int tag=1;//页数
    //获取数据
    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("p",tag);
        map.put("listRows",10);
        map.put("token", Preferences.getString(this, Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.user.NEWS, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("请检查网络");
                        if(tag!=1){

                            rlvNews.setRefreshComplete();

                            myadapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final NewsBean bean = gson.fromJson(msg, NewsBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                if(tag!=1){
                                    rlvNews.setLoadMoreComplete();
                                }
                                List<NewsBean.DataBean> lists = bean.getData();
                                data.addAll(lists);
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
            ImageView iv_photo;
            TextView tv_name,tv_author,tv_time,tv_xiangqing;

            public MyHolder(View itemView) {
                super(itemView);
                iv_photo= (ImageView) itemView.findViewById(R.id.iv_photo);
                tv_name= (TextView) itemView.findViewById(R.id.tv_name);
                tv_author= (TextView) itemView.findViewById(R.id.tv_author);
                tv_time= (TextView) itemView.findViewById(R.id.tv_time);
                tv_xiangqing= (TextView) itemView.findViewById(R.id.tv_xiangqing);

            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_wonderful, parent, false);
            MyHolder holder = new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            Glide.with(getBaseContext())
                    .load(data.get(position).getNew_img())
                    .asBitmap()
                    .into(holder.iv_photo);
            holder.tv_name.setText(data.get(position).getTitle());
            holder.tv_author.setText(data.get(position).getAuthor_name());
            holder.tv_time.setText(data.get(position).getAdd_time());
            holder.tv_xiangqing.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }


}
