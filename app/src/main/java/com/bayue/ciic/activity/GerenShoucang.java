package com.bayue.ciic.activity;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.bean.ShoucangBean;
import com.bayue.ciic.bean.VerificationBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.bayue.ciic.utils.glide.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
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
 * Created by Administrator on 2017/7/3.
 */

public class GerenShoucang extends BaseActivity {
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
    @BindView(R.id.cb_shoucang_checkall)
    CheckBox cbShoucangCheckall;
    @BindView(R.id.tv_shoucang_del)
    TextView tvShoucangDel;
    @BindView(R.id.rv_shoucang)
    RecyclerView rvShoucang;

    RequestManager glideRequest;
    boolean b = false;

    Myadapter myadapter;

    List<ShoucangBean.DataBean> data = new ArrayList<>();
    @BindView(R.id.rl_shoucang_bianji)
    RelativeLayout rlShoucangBianji;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_geren_shouchang;
    }

    @Override
    protected void initViews() {
        glideRequest = Glide.with(this);
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("我的收藏");
        ivShezhi.setVisibility(View.INVISIBLE);
        tvShezhi.setText("编辑");

        myadapter = new Myadapter();
        rvShoucang.setLayoutManager(new GridLayoutManager(this, 2));
        rvShoucang.setHasFixedSize(true);
        rvShoucang.setItemAnimator(new DefaultItemAnimator());
        rvShoucang.setAdapter(myadapter);
        rvShoucang.addItemDecoration(new SpaceItemDecoration(15));
        getData();

        cbShoucangCheckall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setSelected(isChecked);
                }
                myadapter.notifyDataSetChanged();
            }
        });
        tvShezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b) {
                    tvShezhi.setText("编辑");
                    rlShoucangBianji.setVisibility(View.GONE);
                    for (int i = 0; i <data.size(); i++) {
                        data.get(i).setShow(false);
                    }
                    myadapter.notifyDataSetChanged();
                    b=!b;
                } else {
                    tvShezhi.setText("保存");
                    rlShoucangBianji.setVisibility(View.VISIBLE);
                    for (int i = 0; i <data.size() ; i++) {
                        data.get(i).setShow(true);
                    }
                    myadapter.notifyDataSetChanged();
                    b=!b;
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

    @OnClick({R.id.fl_goback, R.id.tv_shoucang_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_goback:
                finish();
                break;
            case R.id.tv_shoucang_del:
                delItem();
                break;
        }
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", Preferences.getString(this, Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.user.VIDEO, map, new Callback() {
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
                    final ShoucangBean bean = gson.fromJson(msg, ShoucangBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                if(bean.getData()==null||bean.getData().isEmpty()){
                                    return;
                                }

                                List<ShoucangBean.DataBean> lists = bean.getData();
                                for (int i = 0; i < lists.size(); i++) {
                                    lists.get(i).setSelected(false);
                                    lists.get(i).setShow(false);
//                                    list.add(lists.get(i));
//                                    myAdapter.notifyItemInserted(i);
//                                    myAdapter.notifyItemRangeChanged(i,list.size());
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

    private void delItem() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                buffer.append(data.get(i).getVideo_id() + ",");
            }
        }
        if(buffer.length()<1){
            return;
        }
        String id = buffer.substring(0, buffer.length() - 1);
        Log.e("id=====", id);
        Map<String, Object> map = new HashMap<>();
        map.put("video_id", id);
        map.put("token", Preferences.getString(this, Preferences.TOKEN));

        HTTPUtils.getNetDATA(API.BaseUrl + API.user.DEL_VIDEO, map, new Callback() {
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
                    final VerificationBean bean = gson.fromJson(msg, VerificationBean.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (bean.getCode() == 200) {
                                ToastUtils.showShortToast(bean.getData());
                                for (int i = 0; i < data.size(); i++) {
                                    if (data.get(i).isSelected()) {
                                        data.remove(i);
                                        myadapter.notifyItemRemoved(i);
                                        myadapter.notifyItemRangeChanged(i, data.size());
                                    }
                                }
                            } else {
                                ToastUtils.showShortToast(bean.getMsg());
                            }
                        }
                    });
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
            ImageView ivImg, ivSelected;
            TextView tvTime, tvAuthor, tvname;

            public MyHolder(View itemView) {
                super(itemView);
                ivImg = (ImageView) itemView.findViewById(R.id.iv_video_img);
                ivSelected = (ImageView) itemView.findViewById(R.id.iv_video_selected);
                tvTime = (TextView) itemView.findViewById(R.id.tv_video_time);
                tvAuthor = (TextView) itemView.findViewById(R.id.tv_video_author);
                tvname = (TextView) itemView.findViewById(R.id.tv_video_name);

            }
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_shoucang, parent, false);
            MyHolder holder = new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            final ShoucangBean.DataBean bean = data.get(position);
            glideRequest.load(bean.getVideo_img())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                    .transform(new GlideRoundTransform(GerenShoucang.this, 0))
                    .into(holder.ivImg);
            if (bean.isSelected()){
                holder.ivSelected.setImageResource(R.mipmap.checkbox_2_3x);
            } else {
                holder.ivSelected.setImageResource(R.mipmap.checkbox_3x);
            }
            if (bean.isShow()) {
                holder.ivSelected.setVisibility(View.VISIBLE);
            } else {
                holder.ivSelected.setVisibility(View.GONE);
            }
            holder.ivImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.isSelected()) {
                        bean.setSelected(!bean.isSelected());
                    } else {
                        bean.setSelected(!bean.isSelected());
                    }
                    notifyDataSetChanged();
                }
            });
            holder.ivSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.isSelected()) {
                        bean.setSelected(!bean.isSelected());
                    } else {
                        bean.setSelected(!bean.isSelected());
                    }
                    notifyDataSetChanged();
                }
            });
            holder.tvAuthor.setText(bean.getAuthor_name());
            Drawable drawable1 = getResources().getDrawable(R.mipmap.people_3_2x);
            drawable1.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
            holder.tvAuthor.setCompoundDrawables(drawable1, null, null, null);//只放左边

            holder.tvname.setText(bean.getVideo_name());


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
            outRect.left = space;
            outRect.bottom = space;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.left = 0;
            }
        }

    }

}
