package com.bayue.ciic.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseFragment;
import com.bayue.ciic.http.API;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
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
    @BindView(R.id.vp_zhibo)
    RecyclerView vpZhibo;
    Unbinder unbinder;

    @Override
    protected int getViewId() {
        return R.layout.frament_platfrom_zhibo;
    }

    @Override
    public void init() {
        vpZhibo.setLayoutManager(new GridLayoutManager(getContext(), 2));
        vpZhibo.setHasFixedSize(true);
        vpZhibo.setItemAnimator(new DefaultItemAnimator());
        vpZhibo.setAdapter(new Myadapter());
        vpZhibo.addItemDecoration(new SpaceItemDecoration(18));


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


    class Myadapter extends RecyclerView.Adapter<Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder {

            public MyHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platfrom_zhibo, parent, false);
            MyHolder holder = new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 6;
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
                outRect.right = 15;
            }
            if ((parent.getChildLayoutPosition(view) - 1) % 2 == 0) {
                outRect.left = 15;
                outRect.right = 0;

            }

        }

    }

    private void getData() {

        Map<String, Object> map = new HashMap();

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
                if (response.code() == 200) {
                    Gson gson = new Gson();


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
