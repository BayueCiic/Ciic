package com.bayue.ciic.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.activity.PhotoAlbum;
import com.bayue.ciic.activity.PhotoDetails;
import com.bayue.ciic.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    @BindView(R.id.vp_wonderful)
    RecyclerView vpWonderful;
    Unbinder unbinder;

    @Override
    protected int getViewId() {
        return R.layout.frament_platfrom_wonderful;
    }

    @Override
    public void init() {
        vpWonderful.setLayoutManager(new GridLayoutManager(getContext(), 2));
        vpWonderful.setHasFixedSize(true);
        vpWonderful.setItemAnimator(new DefaultItemAnimator());
        vpWonderful.setAdapter(new Myadapter());
        vpWonderful.addItemDecoration(new SpaceItemDecoration(18));

        tvWonderfulShipin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


    class Myadapter extends RecyclerView.Adapter<Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder {
            LinearLayout ll_item;
            public MyHolder(View itemView) {
                super(itemView);
                ll_item= (LinearLayout) itemView.findViewById(R.id.ll_item);

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
}
