package com.bayue.ciic.activity;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.bean.CanyuBean;
import com.bayue.ciic.bean.VerificationBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.DensityUtil;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

public class GerenCanyu extends BaseActivity {
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
    @BindView(R.id.cb_canyu_checkall)
    CheckBox cbCanyuCheckall;
    @BindView(R.id.tv_canyu_del)
    TextView tvCanyuDel;
    @BindView(R.id.rv_canyu)
    RecyclerView rvCanyu;

    List<CanyuBean.DataBean> list=new ArrayList<>();
    GerenCanyu.Myadapter myAdapter;
    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_geren_canyu;
    }

    @Override
    protected void initViews() {
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("参与的活动");
        ivShezhi.setVisibility(View.INVISIBLE);

        myAdapter=new GerenCanyu.Myadapter();
        rvCanyu.setLayoutManager(new LinearLayoutManager(this));
        rvCanyu.setHasFixedSize(true);
        rvCanyu.setItemAnimator(new DefaultItemAnimator());
        rvCanyu.setAdapter(myAdapter);
        getList();

        cbCanyuCheckall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for (int i = 0; i <list.size() ; i++) {
                        list.get(i).setSelected(isChecked);
                    }

                }else {
                    for (int i = 0; i <list.size() ; i++) {
                        list.get(i).setSelected(isChecked);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fl_goback, R.id.cb_canyu_checkall, R.id.tv_canyu_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_goback:
                finish();
                break;
            case R.id.cb_canyu_checkall:
                break;
            case R.id.tv_canyu_del:
                delItem();
                break;
        }
    }
    class Myadapter extends RecyclerView.Adapter<GerenCanyu.Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder{

            RelativeLayout rlTitle;
            ImageView ivSelected;
            TextView tvTitle,tvWriter,tvTime,tvState;


            public MyHolder(View itemView) {
                super(itemView);
                rlTitle= (RelativeLayout) itemView.findViewById(R.id.rl_canyu_title);
                ivSelected= (ImageView) itemView.findViewById(R.id.iv_canyu_selected);
                tvTitle= (TextView) itemView.findViewById(R.id.tv_canyu_title);
                tvWriter= (TextView) itemView.findViewById(R.id.tv_canyu_writer);
                tvTime= (TextView) itemView.findViewById(R.id.tv_canyu_time);
                tvState= (TextView) itemView.findViewById(R.id.tv_canyu_state);
            }
        }
        @Override
        public GerenCanyu.Myadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_canyu,parent,false);
            GerenCanyu.Myadapter.MyHolder holder=new GerenCanyu.Myadapter.MyHolder(v);

            return holder;
        }

        @Override
        public void onBindViewHolder(GerenCanyu.Myadapter.MyHolder holder, int position) {
            final CanyuBean.DataBean dataBean=list.get(position);
            if(dataBean.isSelected()){
                holder.ivSelected.setImageResource(R.mipmap.checkbox_2_3x);
            }else {
                holder.ivSelected.setImageResource(R.mipmap.checkbox_3x);
            }
            holder.tvTitle.setText(dataBean.getTitle());
            holder.tvWriter.setText(dataBean.getAuthor_name());
            holder.tvTime.setText(dataBean.getCreate_time());
            holder.tvState.setText(dataBean.getActivity_reg());
            holder.rlTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dataBean.isSelected()){
                        dataBean.setSelected(!dataBean.isSelected());

                    }else {
                        dataBean.setSelected(!dataBean.isSelected());

                    }
                    notifyDataSetChanged();
                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
    private void getList(){

        Map<String,Object> map=new HashMap<>();
        map.put("token", Preferences.getString(this,Preferences.TOKEN));

        HTTPUtils.getNetDATA(API.BaseUrl + API.user.CANYU, map, new Callback() {
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
                String msg=response.body().string();
                if(response.code()==200){
                    Gson gson=new Gson();
                    final CanyuBean bean=gson.fromJson(msg,CanyuBean.class);

                    if(bean.getCode()==200){
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                if(bean.getData()==null||bean.getData().isEmpty()){
                                  return;
                                }
                                List<CanyuBean.DataBean> lists=bean.getData();
                                for (int i = 0; i <lists.size() ; i++) {
                                    lists.get(i).setSelected(false);
//                                    list.add(lists.get(i));
//                                    myAdapter.notifyItemInserted(i);
//                                    myAdapter.notifyItemRangeChanged(i,list.size());
                                }
                                list.addAll(lists);
                                myAdapter.notifyDataSetChanged();
                            }
                        });
                    }else {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showShortToast(bean.getMsg());
                            }
                        });
                    }
                }else {
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

    private void delItem(){
        StringBuffer buffer=new StringBuffer();
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).isSelected()){
                buffer .append(list.get(i).getActivity_id()+",");
            }
        }
        if(buffer.length()<1){
            return;
        }
        String id=buffer.substring(0,buffer.length()-1);
        Log.e("id=====",id);
        Map<String ,Object> map=new HashMap<>();
        map.put("activity_id",id);
        map.put("token",Preferences.getString(this,Preferences.TOKEN));

        HTTPUtils.getNetDATA(API.BaseUrl + API.user.DEL, map, new Callback() {
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
                String msg=response.body().string();
                if(response.code()==200){
                    Gson gson=new Gson();
                    final VerificationBean bean=gson.fromJson(msg,VerificationBean.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                           if(bean.getCode()==200){
                               ToastUtils.showShortToast(bean.getData());
                               for (int i = 0; i <list.size() ; i++) {
                                   if(list.get(i).isSelected()){
                                       list.remove(i);
                                       myAdapter.notifyItemRemoved(i);
                                       myAdapter.notifyItemRangeChanged(i,list.size());

                                   }
                               }

                           }else{
                               ToastUtils.showShortToast(bean.getMsg());
                           }
                        }
                    });




                }else {
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
