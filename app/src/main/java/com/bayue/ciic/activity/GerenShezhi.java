package com.bayue.ciic.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.bean.CompanyBean;
import com.bayue.ciic.bean.VerificationBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.bayue.ciic.utils.glide.GlideCircleTransform;
import com.bayue.ciic.utils.glide.GlideRoundTransform;
import com.bayue.ciic.view.MyPopupWindow;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.io.ByteArrayOutputStream;
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

public class GerenShezhi extends BaseActivity {

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
    @BindView(R.id.iv_shezhi_toux)
    ImageView ivShezhiToux;
    @BindView(R.id.rl_shezhi_toux)
    RelativeLayout rlShezhiToux;
    @BindView(R.id.et_shezhi_name)
    EditText etShezhiName;
    @BindView(R.id.elv_shezhi)
    ExpandableListView elvShezhi;

    List<CompanyBean.DataBean> data = new ArrayList<>();

    Myadapter myadapter;
    String companyName = "";
    boolean updown=false;
    @BindView(R.id.tv_shezhi_shorname)
    TextView tvShezhiShorname;

    RequestManager glideRequest;

    MyPopupWindow popupWindow;

    String oldname;

    private View.OnClickListener itemsOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.but_popup_paizhao:
                    Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,1);
                    popupWindow.dismiss();
                    break;
                case R.id.but_popup_xiangce:
                    GalleryActivity.openActivity(GerenShezhi.this, true,2);
                    popupWindow.dismiss();
                    break;
            }
        }
    };


    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_geren_shezhi;
    }

    @Override
    protected void initViews() {
        glideRequest = Glide.with(this);
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("编辑资料");
        ivShezhi.setVisibility(View.INVISIBLE);
        tvShezhi.setText("保存");
        oldname=etShezhiName.getText().toString();
        myadapter = new Myadapter();
        elvShezhi.setGroupIndicator(null);
        elvShezhi.setAdapter(myadapter);
        getData();
        elvShezhi.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (parent.isGroupExpanded(groupPosition)) {

                    Log.e("展开", ">>>>>>>>>>>>>>>");

                    /*ImageView iv = (ImageView) v.findViewById(R.id.iv_item_updown);
                    iv.setImageResource(R.mipmap.bianjiziliao_kaxia_3x);*/
                    updown=false;

                } else {
                    Log.e("关闭", ">>>>>>>>>>>>>>>");
                    /*ImageView iv = (ImageView) v.findViewById(R.id.iv_item_updown);
                    iv.setImageResource(R.mipmap.bianjiziliao_shou_3x);*/
                    updown=true;
                }


                return false;
            }
        });
        elvShezhi.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                elvShezhi.collapseGroup(groupPosition);
                companyName = data.get(childPosition).getName();
                tvShezhiShorname.setText(data.get(childPosition).getShort_name());
                myadapter.notifyDataSetChanged();
                updown=false;
                b=true;
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fl_goback, R.id.fl_shezhi, R.id.rl_shezhi_toux})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_goback:
                finish();
                break;
            case R.id.fl_shezhi:
                tiJiao();
                break;
            case R.id.rl_shezhi_toux:
                popupWindow=new MyPopupWindow(this,itemsOnClick);
                popupWindow.showAtLocation(this.findViewById(R.id.rl_shezhi_toux), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", Preferences.getString(this, Preferences.TOKEN));
        HTTPUtils.getNetDATA(API.BaseUrl + API.user.COMPANY, map, new Callback() {
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
                    final CompanyBean bean = gson.fromJson(msg, CompanyBean.class);

                    if (bean.getCode() == 200) {
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                List<CompanyBean.DataBean> lists = bean.getData();

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

    boolean b=false;
    private void tiJiao(){
       String name=etShezhiName.getText().toString();
        if(oldname.equals(name)&& !b ){
            ToastUtils.showShortToast("你没有修改任何资料");
            return;
        }
        Map<String , Object> map=new HashMap<>();
        HTTPUtils.getNetDATA(API.BaseUrl + API.user.ALTER, map, new Callback() {
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
//                                for (int i = 0; i < data.size(); i++) {
//                                    if (data.get(i).isSelected()) {
//                                        data.remove(i);
//                                        myadapter.notifyItemRemoved(i);
//                                        myadapter.notifyItemRangeChanged(i, data.size());
//                                    }
//                                }
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null){
            return;
        }
        Log.e("resultCode===>>>>",resultCode+"");
        Log.e("requestCode===>>>>",requestCode+"");
        if(requestCode==1){

            Bundle bundle=data.getExtras();

//            Log.e("data=====1111===",bundle.getString("data"));
            Bitmap bitmap= (Bitmap) bundle.get("data");
            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
            glideRequest.
                    load(uri)
                    .placeholder(R.mipmap.bianjiziliao_toux2_3x)
                    .error(R.mipmap.bianjiziliao_toux2_3x)
                    .transform(new  GlideCircleTransform(this))
                    .into(ivShezhiToux);
            b=true;
            //            ivTupianXie.setImageBitmap(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();

            //base64 encode
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
//            pic = new String(encode);
//            Log.e("base64",pic);

        }else {

            List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
            opts.inSampleSize = 4;

            Bitmap bitmap = BitmapFactory.decodeFile(photos.get(0), opts);
//            ivTupianXie.setImageBitmap(bitmap);
            glideRequest.
                    load(photos.get(0))
                    .placeholder(R.mipmap.bianjiziliao_toux2_3x)
                    .error(R.mipmap.bianjiziliao_toux2_3x)
                    .transform(new GlideCircleTransform(this))
                    .into(ivShezhiToux);
            b=true;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();

            //base64 encode
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);

//            pic = new String(encode);
//            Log.e("base64",pic);
//            mTvShow.setText(encodeString);
        }




//        Log.e(">>>>>>data>>>>>",data+"");

    }

    class Myadapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return 1;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return data.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_shezhi, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.tv_item_companyname);
            textView.setText(companyName);

            ImageView imageView= (ImageView) convertView.findViewById(R.id.iv_item_updown);
            if(updown){
                imageView.setImageResource(R.mipmap.bianjiziliao_shou_3x);
            }else {
                imageView.setImageResource(R.mipmap.bianjiziliao_kaxia_3x);
            }


            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_shezhi_sub, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.tv_subitem_companyname);
            textView.setText(data.get(childPosition).getName());
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
