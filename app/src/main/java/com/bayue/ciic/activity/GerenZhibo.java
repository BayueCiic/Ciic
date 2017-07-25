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
import com.bayue.ciic.bean.ShipinBean;
import com.bayue.ciic.bean.ZhiboBean;
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
 * Created by Administrator on 2017/7/11.
 */

public class GerenZhibo extends BaseActivity {
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
    @BindView(R.id.rlv_zhibo)
    PullToRefreshRecyclerView rlvZhibo;

    Myadapter myadapter;

    List<ZhiboBean.DataBean> data=new ArrayList<>();
    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {

        return R.layout.activity_geren_zhibo;
    }

    @Override
    protected void initViews() {
        getData();
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("直播管理");
        ivShezhi.setVisibility(View.INVISIBLE);

        myadapter=new Myadapter();
        rlvZhibo.setLayoutManager(new LinearLayoutManager(this));
        rlvZhibo.setHasFixedSize(true);
        rlvZhibo.setItemAnimator(new DefaultItemAnimator());
        rlvZhibo.setAdapter(myadapter);
        //是否开启下拉刷新功能
        rlvZhibo.setPullRefreshEnabled(false);
        //是否开启上拉加载功能
        rlvZhibo.setLoadingMoreEnabled(true);
        //设置是否显示上次刷新的时间
        rlvZhibo.displayLastRefreshTime(true);

        rlvZhibo.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                tag++;
                getData();
            }
        });
    }

    int tag = 1;//页数

    //获取数据
    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("p", tag);
        map.put("listRows", 10);
        map.put("token", Preferences.getString(this, Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.user.ZHIBO, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("请检查网络");
                        if (tag != 1) {

                            rlvZhibo.setRefreshComplete();

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
                    final ZhiboBean bean = gson.fromJson(msg, ZhiboBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                List<ZhiboBean.DataBean>  lists = bean.getData();
                                if (tag != 1) {
                                    rlvZhibo.setLoadMoreComplete();
                                }
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

    class Myadapter extends RecyclerView.Adapter<Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder {
            ImageView iv_photo;
            TextView tv_name,tv_time;

            public MyHolder(View itemView) {
                super(itemView);
                iv_photo= (ImageView) itemView.findViewById(R.id.iv_photo);
                tv_name= (TextView) itemView.findViewById(R.id.tv_name);
                tv_time= (TextView) itemView.findViewById(R.id.tv_time);

            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_zhibo, parent, false);
            MyHolder holder = new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

            Glide.with(getBaseContext())
                    .load(data.get(position).getVideo_img());
            holder.tv_name.setText(data.get(position).getRoom_name());
            holder.tv_time.setText(data.get(position).getAuthor_name());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}
