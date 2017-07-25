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
import com.bayue.ciic.App;
import com.bayue.ciic.MainActivity;
import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseFragment;
import com.bayue.ciic.bean.PHuodongBean;
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
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/3.
 */

public class PlatfromHuodong extends BaseFragment {


    @BindView(R.id.iv_huodong_sanjiao)
    ImageView ivHuodongSanjiao;
    @BindView(R.id.tv_huodong)
    TextView tvHuodong;
    @BindView(R.id.tv_huodong_xiuxian)
    TextView tvHuodongXiuxian;
    @BindView(R.id.view_huodong_one)
    View viewHuodongOne;
    @BindView(R.id.tv_huodong_yepao)
    TextView tvHuodongYepao;
    @BindView(R.id.view_huodong_two)
    View viewHuodongTwo;
    @BindView(R.id.tv_huodong_name)
    TextView tvHuodongName;

    Unbinder unbinder;
    @BindView(R.id.tv_huodong_all)
    TextView tvHuodongAll;
    private int tag = 1;
    String label_id="";
    MainActivity main;
    List<PHuodongBean.DataBean> data = new ArrayList<>();

    Myadapter myadapter;
    @BindView(R.id.vp_huodong)
    PullToRefreshRecyclerView vpHuodong;

    @Override
    protected int getViewId() {
        return R.layout.frament_platfrom_huodong;
    }

    @Override
    public void init() {
        myadapter = new Myadapter();
        main= (MainActivity) App.mainActivity;

        vpHuodong.setLayoutManager(new LinearLayoutManager(getContext()));
        vpHuodong.setHasFixedSize(true);
        vpHuodong.setItemAnimator(new DefaultItemAnimator());
        vpHuodong.setAdapter(myadapter);
//       vpZhibo.addItemDecoration(new SpaceItemDecoration(18));

        vpHuodong.setPullRefreshEnabled(false);
        vpHuodong.setLoadingMoreEnabled(true);
        vpHuodong.displayLastRefreshTime(true);

        vpHuodong.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                vpHuodong.setLoadMoreComplete();
            }

            @Override
            public void onLoadMore() {
                tag++;
                getHuodongData();
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

    //恢复颜色1
    private void restoreColor1(TextView view) {
        tvHuodongName.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvHuodongXiuxian.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvHuodongYepao.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        tvHuodongAll.setTextColor(getResources().getColor(R.color.gerenTextcolor));
        view.setTextColor(getResources().getColor(R.color.black));
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
        getHuodongData();
    }

    @OnClick({R.id.tv_huodong_xiuxian, R.id.tv_huodong_yepao, R.id.tv_huodong_name, R.id.tv_huodong_all})
    public void onViewClicked(View view) {
        tag=1;
        switch (view.getId()) {
            case R.id.tv_huodong_xiuxian:
                restoreColor1(tvHuodongXiuxian);
                label_id=main.lists.get(0).getId();
                break;
            case R.id.tv_huodong_yepao:
                restoreColor1(tvHuodongYepao);
                label_id=main.lists.get(1).getId();
                break;
            case R.id.tv_huodong_name:
                restoreColor1(tvHuodongName);
                label_id=main.lists.get(2).getId();
                break;
            case R.id.tv_huodong_all:
                restoreColor1(tvHuodongAll);
                label_id="";
                break;
        }
        getHuodongData();
    }

    class Myadapter extends RecyclerView.Adapter<Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder {
            ImageView iv_huodong_img;
            TextView iv_huodong_title, iv_huodong_time,
                    iv_huodong_txt, iv_huodong_ing, iv_huodong_author;

            public MyHolder(View itemView) {
                super(itemView);
                iv_huodong_img = (ImageView) itemView.findViewById(R.id.iv_huodong_img);

                iv_huodong_title = (TextView) itemView.findViewById(R.id.iv_huodong_title);
                iv_huodong_time = (TextView) itemView.findViewById(R.id.iv_huodong_time);
                iv_huodong_txt = (TextView) itemView.findViewById(R.id.iv_huodong_txt);
                iv_huodong_ing = (TextView) itemView.findViewById(R.id.iv_huodong_ing);
                iv_huodong_author = (TextView) itemView.findViewById(R.id.iv_huodong_author);


            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platfrom_huodong, parent, false);
            MyHolder holder = new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            Glide.with(getContext())
                    .load(data.get(position).getActivity_img())
                    .asBitmap()
                    .into(holder.iv_huodong_img);
            holder.iv_huodong_title.setText(data.get(position).getTitle());
            holder.iv_huodong_time.setText(data.get(position).getCreate_time());
            holder.iv_huodong_txt.setText(data.get(position).getContent());
            if (data.get(position).getStatus().equals("1")) {
                holder.iv_huodong_ing.setText("已结束...");
            } else {
                holder.iv_huodong_ing.setText("进行中...");
            }
            holder.iv_huodong_author.setText(data.get(position).getAuthor());
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

    private void getHuodongData() {
        if(main.lists!=null)
            for (int i = 0; i <main.lists.size() ; i++) {
                if(label_id.equals(main.lists.get(i).getId())){
                    switch (i){
                        case 0:
                            restoreColor1(tvHuodongXiuxian);
                            break;
                        case 1:
                            restoreColor1(tvHuodongYepao);
                            break;
                        case 2:
                            restoreColor1(tvHuodongName);
                            break;
                    }
                }
            }
        if(label_id.equals("")||label_id.isEmpty()){
            restoreColor1(tvHuodongAll);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("p", tag);
        map.put("listRows", 8);
        map.put("label_id", label_id);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.patfrom.HUODOANG, map, new Callback() {
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
                    final PHuodongBean bean = gson.fromJson(msg, PHuodongBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<PHuodongBean.DataBean> lists = bean.getData();
                                if (tag == 1) {
                                    data.clear();
                                } else {
                                    if (vpHuodong != null)
                                        vpHuodong.setLoadMoreComplete();
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

}
