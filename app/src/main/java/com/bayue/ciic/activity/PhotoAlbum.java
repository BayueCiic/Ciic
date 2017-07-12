package com.bayue.ciic.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.fragment.PlatfromWenderful;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/10.
 */

public class PhotoAlbum extends BaseActivity {


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
    @BindView(R.id.iv_wonderful_sanjiao)
    ImageView ivWonderfulSanjiao;
    @BindView(R.id.tv_album_name)
    TextView tvAlbumName;
    @BindView(R.id.vp_album)
    RecyclerView vpAlbum;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_photo_album;
    }

    @Override
    protected void initViews() {
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("精彩瞬间");
        ivShezhi.setVisibility(View.INVISIBLE);

        vpAlbum.setLayoutManager(new GridLayoutManager(this, 2));
        vpAlbum.setHasFixedSize(true);
        vpAlbum.setItemAnimator(new DefaultItemAnimator());
        vpAlbum.setAdapter(new PhotoAlbum.Myadapter());
        vpAlbum.addItemDecoration(new PhotoAlbum.SpaceItemDecoration(18));

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

    class Myadapter extends RecyclerView.Adapter<PhotoAlbum.Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder {
            LinearLayout ll_item;
            public MyHolder(View itemView) {
                super(itemView);
                ll_item= (LinearLayout) itemView.findViewById(R.id.ll_item);

            }
        }

        @Override
        public PhotoAlbum.Myadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_album, parent, false);
            PhotoAlbum.Myadapter.MyHolder holder = new PhotoAlbum.Myadapter.MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(PhotoAlbum.Myadapter.MyHolder holder, int position) {
            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(PhotoAlbum.this, PhotoDetails.class));
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
