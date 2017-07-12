package com.bayue.ciic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.bean.VerificationBean;
import com.bayue.ciic.bean.XxBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
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

public class GerenXx extends BaseActivity {

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
    @BindView(R.id.cb_xx_checkall)
    CheckBox cbXxCheckall;
    @BindView(R.id.tv_xx_del)
    TextView tvXxDel;
    @BindView(R.id.tv_xx_tag)
    TextView tvXxTag;
    @BindView(R.id.rv_xx)
    RecyclerView rvXx;

    GerenXx.Myadapter myadapter;

    List<XxBean.DataBean> data=new ArrayList<>();
    @Override
    protected void setTheme() {



    }

    @Override
    protected int getViewId() {
        return R.layout.activity_geren_xx;
    }

    @Override
    protected void initViews() {

        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("官方消息");
        ivShezhi.setVisibility(View.INVISIBLE);

        myadapter=new GerenXx.Myadapter();
        rvXx.setLayoutManager(new LinearLayoutManager(this));
        rvXx.setHasFixedSize(true);
        rvXx.setItemAnimator(new DefaultItemAnimator());
        rvXx.setAdapter(myadapter);
        getData();

        cbXxCheckall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i <data.size() ; i++) {
                    data.get(i).setSelected(isChecked);
                }
                myadapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fl_goback, R.id.tv_xx_del, R.id.tv_xx_tag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_goback:
                finish();
                break;
            case R.id.tv_xx_del:
                delItem();
                break;
            case R.id.tv_xx_tag:
                setTag();
                break;
        }
    }
    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", Preferences.getString(this, Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.user.XX, map, new Callback() {
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
                    final XxBean bean = gson.fromJson(msg, XxBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                List<XxBean.DataBean> lists = bean.getData();
                                for (int i = 0; i < lists.size(); i++) {
                                    lists.get(i).setSelected(false);
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
                buffer.append(data.get(i).getId() + ",");
            }
        }
        if(buffer.length()<1){
            return;
        }
        Log.e("buffer.length===",buffer.length()+"");
        String id = buffer.substring(0, buffer.length() - 1);
        Log.e("id=====", id);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("token", Preferences.getString(this, Preferences.TOKEN));

        HTTPUtils.getNetDATA(API.BaseUrl + API.user.DEL_XX, map, new Callback() {
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
                                if(bean.getData()==null||bean.getData().isEmpty()){
                                    return;
                                }
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

    private void setTag() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelected()) {
                buffer.append(data.get(i).getId() + ",");
                data.get(i).setStatus("1");
            }
        }
        if(buffer.length()<1){
            return;
        }
        String id = buffer.substring(0, buffer.length() - 1);
        Log.e("id=====", id);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("token", Preferences.getString(this, Preferences.TOKEN));

        HTTPUtils.getNetDATA(API.BaseUrl + API.user.TAG_XX, map, new Callback() {
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
                                myadapter.notifyDataSetChanged();
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

        public class MyHolder extends RecyclerView.ViewHolder{

            LinearLayout llSeleted,xxItem;
            ImageView ivSelected;
            TextView tvName,tvTime;

            public MyHolder(View itemView) {
                super(itemView);

                llSeleted= (LinearLayout) itemView.findViewById(R.id.ll_xx_selected);
                ivSelected= (ImageView) itemView.findViewById(R.id.iv_xx_selected);
                tvName= (TextView) itemView.findViewById(R.id.tv_xx_name);
                tvTime= (TextView) itemView.findViewById(R.id.tv_xx_time);
                xxItem= (LinearLayout) itemView.findViewById(R.id.xx_item);
            }
        }

        @Override
        public Myadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_xx,parent,false);
            MyHolder holder=new MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(Myadapter.MyHolder holder, int position) {
            final XxBean.DataBean bean=data.get(position);
            if(bean.isSelected()){
                holder.ivSelected.setImageResource(R.mipmap.checkbox_2_3x);
            }else{
                holder.ivSelected.setImageResource(R.mipmap.checkbox_3x);
            }
            holder.tvName.setText(bean.getTitle());
            if(bean.getStatus().equals("1")){
                holder.tvName.setTextColor(getResources().getColor(R.color.gerenTextcolor));
            }
            if(bean.getStatus().equals("0")){
                holder.tvName.setTextColor(getResources().getColor(R.color.black));
            }
            holder.tvTime.setText(bean.getCreate_time());
            holder.llSeleted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bean.setSelected(!bean.isSelected());
                    notifyDataSetChanged();
                }
            });
            holder.xxItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(GerenXx.this,GerenXxDetails.class).putExtra("id",bean.getId()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
