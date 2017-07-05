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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    @BindView(R.id.tv_zhibo_genduo)
    TextView tvZhiboGenduo;
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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platfrom_shouye, parent, false);
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
