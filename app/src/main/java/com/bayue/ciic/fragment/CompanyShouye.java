package com.bayue.ciic.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.activity.live.activity.VideoPlayerActivity;
import com.bayue.ciic.base.BaseFragment;
import com.bayue.ciic.bean.PZhiboBean;
import com.bayue.ciic.bean.ShouyeHuodongData;
import com.bayue.ciic.bean.ShouyeShipinBean;
import com.bayue.ciic.bean.ShouyeZhiboBean;
import com.bayue.ciic.bean.TagBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

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
 * Created by Administrator on 2017/7/7.
 */

public class CompanyShouye extends BaseFragment {
    @BindView(R.id.iv_shipin_sanjiao)
    ImageView ivShipinSanjiao;
    @BindView(R.id.tv_shipin_shipin)
    TextView tvShipinShipin;
    @BindView(R.id.tv_shipin_xiuxian)
    TextView tvShipinXiuxian;
    @BindView(R.id.view_shipin_one)
    View viewShipinOne;
    @BindView(R.id.tv_shipin_yepao)
    TextView tvShipinYepao;
    @BindView(R.id.view_shipin_two)
    View viewShipinTwo;
    @BindView(R.id.tv_shipin_name)
    TextView tvShipinName;
    @BindView(R.id.tv_shipin_genduo)
    TextView tvShipinGenduo;
    @BindView(R.id.tv_shipin_title)
    TextView tvShipinTitle;
    @BindView(R.id.tv_shipin_compere)
    TextView tvShipinCompere;
    @BindView(R.id.ll_zhibo)
    LinearLayout llZhibo;
    @BindView(R.id.vp_shouye)
    RecyclerView vpShouye;
    @BindView(R.id.iv_huodong_sanjiao)
    ImageView ivHuodongSanjiao;
    @BindView(R.id.tv_huodong_shipin)
    TextView tvHuodongShipin;
    @BindView(R.id.tv_huodong_genduo)
    TextView tvHuodongGenduo;
    @BindView(R.id.tv_time_huodon1)
    TextView tvTimeHuodon1;
    @BindView(R.id.tv_name_huodon1)
    TextView tvNameHuodon1;
    @BindView(R.id.ll_huodon1)
    LinearLayout llHuodon1;
    @BindView(R.id.tv_time_huodon2)
    TextView tvTimeHuodon2;
    @BindView(R.id.tv_name_huodon2)
    TextView tvNameHuodon2;
    @BindView(R.id.ll_huodon2)
    LinearLayout llHuodon2;
    @BindView(R.id.tv_time_huodon3)
    TextView tvTimeHuodon3;
    @BindView(R.id.tv_name_huodon3)
    TextView tvNameHuodon3;
    @BindView(R.id.ll_huodon3)
    LinearLayout llHuodon3;
    @BindView(R.id.tv_time_huodon4)
    TextView tvTimeHuodon4;
    @BindView(R.id.tv_name_huodon4)
    TextView tvNameHuodon4;
    @BindView(R.id.ll_huodon4)
    LinearLayout llHuodon4;
    Unbinder unbinder;

    static MainCompany mainCompany;
    @BindView(R.id.iv_zhibo)
    ImageView ivZhibo;

    Myadapter myadapter;

    public static CompanyShouye getCompanyShouye(MainCompany mainCompany) {


        CompanyShouye.mainCompany = mainCompany;
        mainCompany = mainCompany;
        return new CompanyShouye();
    }

    @Override
    protected int getViewId() {
        return R.layout.frament_company_shouye;
    }

    @Override
    public void init() {
        myadapter=new Myadapter();
        vpShouye.setLayoutManager(new GridLayoutManager(getContext(), 3));
        vpShouye.setHasFixedSize(true);
        vpShouye.setItemAnimator(new DefaultItemAnimator());
        vpShouye.setAdapter(myadapter);
        vpShouye.addItemDecoration(new SpaceItemDecoration(18));

        getTagShipin();
        getZhiboData();
        getShipinData();
        getHuodongData();
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

    @OnClick({R.id.tv_shipin_genduo, R.id.ll_zhibo, R.id.tv_huodong_genduo, R.id.ll_huodon1, R.id.ll_huodon2, R.id.ll_huodon3, R.id.ll_huodon4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shipin_genduo:
                mainCompany.setCurrentItem(2);
                break;
            case R.id.ll_zhibo:
                break;
            case R.id.tv_huodong_genduo:
                mainCompany.setCurrentItem(3);
                break;
            case R.id.ll_huodon1:
                break;
            case R.id.ll_huodon2:
                break;
            case R.id.ll_huodon3:
                break;
            case R.id.ll_huodon4:
                break;
        }
    }

    List<TagBean.DataBean> lists = new ArrayList<>();
    List<TagBean.DataBean> tags = new ArrayList<>();

    //获取视频类型
    private void getTagShipin() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "video");
//        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.TAG, map, new Callback() {
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
                    final TagBean bean = gson.fromJson(msg, TagBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                lists = bean.getData();
                                tags = lists;
                                if (lists.isEmpty() || lists == null) {
                                    return;
                                }
                                if (tvShipinXiuxian != null) {
                                    tvShipinXiuxian.setText(lists.get(0).getName());
                                }


                                if (tvShipinYepao != null)
                                    tvShipinYepao.setText(lists.get(1).getName());


                                if (tvShipinName != null)
                                    tvShipinName.setText(lists.get(2).getName());

                            }
                        });
                    } else {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
//                                ToastUtils.showShortToast(bean.getMsg());
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


    //获取直播数据
    private void getZhiboData() {
        Map<String, Object> map = new HashMap<>();
        map.put("row", 1);
        map.put("p",1);
        map.put("label",0);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.Company.SHOUYE_ZHIBO, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("直播---请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final PZhiboBean bean = gson.fromJson(msg, PZhiboBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<PZhiboBean.DataBean> lists = bean.getData();
                                Log.e("list直播====size==", lists.size() + "");
                                if (ivZhibo != null)
                                    Glide.with(getContext())
                                            .load(lists.get(0).getVideo_img())
                                            .asBitmap()
                                            .error(R.drawable.shouye_img_01_2x)
                                            .into(ivZhibo);

                                if (tvShipinTitle != null)
                                    tvShipinTitle.setText(lists.get(0).getTitle());
                                if (tvShipinCompere != null)
                                    tvShipinCompere.setText(lists.get(0).getAuthor());

//                                Log.e("TTTTTT",lists.get(0).getName()+lists.get(1).getName()+lists.get(2).getName());


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
    List<PZhiboBean.DataBean> shipinData = new ArrayList<>();
    //获取视频数据
    private void getShipinData() {
        Map<String, Object> map = new HashMap<>();
        map.put("row", 1);
        map.put("p",1);
        map.put("label",0);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.Company.SHOUYE_SHIPING, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("视频----请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final PZhiboBean bean = gson.fromJson(msg, PZhiboBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<PZhiboBean.DataBean> lists = bean.getData();
                                if(lists==null)
                                    return;
                                Log.e("list视频====size==", lists.size() + "");
                                shipinData.clear();
                                shipinData.addAll(lists);
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

    //获取活动数据
    private void getHuodongData() {
        Map<String, Object> map = new HashMap<>();
        map.put("row", 1);
        map.put("p",1);
        map.put("label",0);
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.Company.SHOUYE_HUODONG, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToolKit.runOnMainThreadSync(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShortToast("活动-----请检查网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final ShouyeHuodongData bean = gson.fromJson(msg, ShouyeHuodongData.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {

                                List<ShouyeHuodongData.DataBean> lists = bean.getData();
                                if(lists==null)
                                    return;
                                if(lists.size()==0){
                                    return;
                                }
                                Log.e("list活动====size==",lists.size()+"");
                                if(tvTimeHuodon1!=null&&lists.size()>0)
                                    tvTimeHuodon1.setText(lists.get(0).getCreate_time());
                                if(tvTimeHuodon2!=null&&lists.size()>1)
                                    tvTimeHuodon2.setText(lists.get(1).getCreate_time());
                                if(tvTimeHuodon3!=null&&lists.size()>2)
                                    tvTimeHuodon3.setText(lists.get(2).getCreate_time());
                                if(tvTimeHuodon4!=null&&lists.size()>3)
                                    tvTimeHuodon4.setText(lists.get(3).getCreate_time());

                                if(tvNameHuodon1!=null&&lists.size()>0)
                                    tvNameHuodon1.setText(lists.get(0).getTitle());
                                if(tvNameHuodon2!=null&&lists.size()>1)
                                    tvNameHuodon2.setText(lists.get(1).getTitle());
                                if(tvNameHuodon3!=null&&lists.size()>2)
                                    tvNameHuodon3.setText(lists.get(2).getTitle());
                                if(tvNameHuodon4!=null&&lists.size()>3)
                                    tvNameHuodon4.setText(lists.get(3).getTitle());


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
            ImageView iv_shipin_img;

            TextView tv_shipin_title,tv_shipin_author;
            View view;
            public MyHolder(View itemView) {
                super(itemView);
                view=itemView;
                iv_shipin_img= (ImageView) itemView.findViewById(R.id.iv_shipin_img);
                tv_shipin_title= (TextView) itemView.findViewById(R.id.tv_shipin_title);
                tv_shipin_author= (TextView) itemView.findViewById(R.id.tv_shipin_author);

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
            holder.tv_shipin_title.setText(shipinData.get(position).getTitle());
            holder.tv_shipin_author.setText(shipinData.get(position).getAuthor_name());
            Picasso.with(getContext())
                    .load(shipinData.get(position).getVideo_img())
                    .into(holder.iv_shipin_img);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("播放---","shi频");
                    //check
                   /* if(checkUrlValidate()) {
                        VideoPlayerActivity.startActivity(getContext(), url);
                    }*/
                }
            });
        }

        @Override
        public int getItemCount() {
            return shipinData.size();
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
