package com.bayue.ciic.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    String companyId;
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
                    GalleryConfig config = new GalleryConfig.Build()
                            .limitPickPhoto(1)
                            .singlePhoto(false)
                            .hintOfPick("只可选一张")
//                            .filterMimeTypes(new String[]{"jpg","png"})
                            .build();
                    GalleryActivity.openActivity(GerenShezhi.this, 100, config);
//                    GalleryActivity.openActivity(GerenShezhi.this, true,2);
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
        getData();
        glideRequest = Glide.with(this);
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("编辑资料");
        ivShezhi.setVisibility(View.INVISIBLE);
        tvShezhi.setText("保存");


        Intent intent=getIntent();
        glideRequest.
                load(intent.getStringExtra("img"))
                .placeholder(R.mipmap.bianjiziliao_toux2_3x)
                .error(R.mipmap.bianjiziliao_toux2_3x)
                .transform(new  GlideCircleTransform(this))
                .into(ivShezhiToux);

        etShezhiName.setText(intent.getStringExtra("name"));
        companyName=intent.getStringExtra("company");
        companyId=intent.getStringExtra("companyid");
        Log.e("公司name===",companyName+"");
        Log.e("公司id===",companyId+"");
        tvShezhiShorname.setText(intent.getStringExtra("shortname"));
        oldname=etShezhiName.getText().toString();
        myadapter = new Myadapter();
        elvShezhi.setGroupIndicator(null);
        elvShezhi.setAdapter(myadapter);





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
                companyName = data.get(childPosition).getUsername();
                companyId=data.get(childPosition).getId();
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
                                Log.e("+++++++++++",data.size()+"");
                                Log.e(">>>>>>",data.get(1).getUsername());
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
        map.put("token",Preferences.getString(this,Preferences.TOKEN));
        map.put("id",companyId);
        map.put("username",name);

        Map<String ,File>  fileMap=new HashMap<>();
        if(file!=null){
            fileMap.put("file",file);
            Log.e("图片名===",file.getName());
        }
        HTTPUtils.getFileDATA(API.BaseUrl + API.user.ALTER, map,fileMap, new Callback() {
            @Override
            public void onFailure(Call call, IOException e){
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
                Log.e("返回值===",msg);
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final VerificationBean bean = gson.fromJson(msg, VerificationBean.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (bean.getCode() == 200) {
                                ToastUtils.showShortToast(bean.getData());
                                finish();
//                                for (int i = 0; i < data.size(); i++) {
//                                    if (data.get(i).isSelected()) {
//                                        data.remove(i);
//                                        myadapter.notifyItemRemoved(i);
//                                        myadapter.notifyItemRangeChanged(i, data.size());
//                                    }
//                                }
                            } else {
                                ToastUtils.showShortToast(bean.getMsg()+">>>>>>>>>>>");
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

    File file;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null){
            return;
        }
        Log.e("resultCode===>>>>",resultCode+"");
        Log.e("requestCode===>>>>",requestCode+"");
        if(requestCode==1){

            Bundle bundle=data.getExtras();

                if(bundle==null){
                    return;
                }
//            Log.e("data=====1111===",bundle.getString("data"));
            Bitmap bitmap= (Bitmap) bundle.get("data");
            Log.e("bitmap===bbbbb====",bitmap+"");
            file=compressImage2(bitmap);
//            Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
//            file=saveBitmap(bitmap);
//            bitmap=compressImage(bitmap);

            Log.e("file===????????====",file+"");
            glideRequest.
                    load(file)
                    .placeholder(R.mipmap.bianjiziliao_toux2_3x)
                    .error(R.mipmap.bianjiziliao_toux2_3x)
                    .transform(new  GlideCircleTransform(this))
                    .into(ivShezhiToux);
            b=true;
            /*//            ivTupianXie.setImageBitmap(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();

            //base64 encode
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
//            pic = new String(encode);
//            Log.e("base64",pic);*/

        }else {

            List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);

            file=new File(photos.get(0));
            Bitmap bitmap=BitmapFactory.decodeFile(photos.get(0),getBitmapOption(2));
            Log.e("file原大小=====",file.length()+"");

            file=compressImage2(bitmap);
            Log.e("file现在大小=====",file.length()+"");

            /*BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
            opts.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeFile(photos.get(0), opts);
//            file=saveBitmap(bitmap);*/

            Log.e("图片file=111111===",file+"");
//            ivTupianXie.setImageBitmap(bitmap);
            glideRequest.
                    load(photos.get(0))
                    .placeholder(R.mipmap.bianjiziliao_toux2_3x)
                    .error(R.mipmap.bianjiziliao_toux2_3x)
                    .transform(new GlideCircleTransform(this))
                    .into(ivShezhiToux);
            b=true;
           /* ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();

            //base64 encode
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);*/

//            pic = new String(encode);
//            Log.e("base64",pic);
//            mTvShow.setText(encodeString);
        }

//        Log.e(">>>>>>data>>>>>",data+"");

    }
    public static BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
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
            textView.setText(data.get(childPosition).getUsername());
            Log.e(">>>>>>",data.get(childPosition).getUsername());
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static File compressImage2(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);

        File file = new File(Environment.getExternalStorageDirectory(), date.getTime()+".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
//                BAFLogger.e(TAG,e.getMessage());
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
//            BAFLogger.e(TAG,e.getMessage());
            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        Log.e("图片file=77777===",file+"");
        return file;
    }
    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps==null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }

}
