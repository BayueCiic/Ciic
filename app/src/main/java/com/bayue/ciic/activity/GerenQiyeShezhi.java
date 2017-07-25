package com.bayue.ciic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.bean.VerificationBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.bayue.ciic.utils.glide.GlideCircleTransform;
import com.bayue.ciic.view.MyPopupWindow;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
 * Created by Administrator on 2017/7/17.
 */

public class GerenQiyeShezhi extends BaseActivity {
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
    @BindView(R.id.et_shezhi_shortname)
    EditText etShezhiShortname;
    RequestManager glideRequest;

    MyPopupWindow popupWindow;

    String companyName = "";
    String shortName="";

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
                    GalleryActivity.openActivity(GerenQiyeShezhi.this, 100, config);
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
        return R.layout.activity_geren_qiye_shezhi;
    }

    @Override
    protected void initViews() {
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


        companyName=intent.getStringExtra("company");
        etShezhiName.setText(companyName);
        shortName=intent.getStringExtra("shortname");
        etShezhiShortname.setText(shortName);
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
    public static BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }
    boolean b=false;
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
                    .transform(new GlideCircleTransform(this))
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
            Bitmap bitmap= BitmapFactory.decodeFile(photos.get(0),getBitmapOption(2));
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
    private void tiJiao(){
        String name=etShezhiName.getText().toString();
        shortName=etShezhiShortname.getText().toString();
        if(name.equals(name)&& !b&&shortName.equals(shortName) ){
            ToastUtils.showShortToast("你没有修改任何资料");
            return;
        }
        Map<String , Object> map=new HashMap<>();
        map.put("token", Preferences.getString(this,Preferences.TOKEN));
        map.put("short_name",shortName);
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
}
