package com.bayue.ciic.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bayue.ciic.bean.VerificationBean;
import com.bayue.ciic.http.API;
import com.bayue.ciic.preferences.Preferences;
import com.bayue.ciic.utils.HTTPUtils;
import com.bayue.ciic.utils.ToastUtils;
import com.bayue.ciic.utils.ToolKit;
import com.bayue.ciic.view.MyPopupWindow;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.bayue.ciic.activity.GerenShezhi.compressImage2;

/**
 * Created by Administrator on 2017/6/28.
 */

public class GerenComplaint extends BaseActivity {

    @BindView(R.id.iv_goback)
    ImageView ivGoback;
    @BindView(R.id.fl_goback)
    FrameLayout flGoback;
    @BindView(R.id.tv_titletxt)
    TextView tvTitletxt;
    @BindView(R.id.iv_shezhi)
    ImageView ivShezhi;
    @BindView(R.id.et_complaint_txt)
    EditText etComplaintTxt;
    @BindView(R.id.ll_complaint_tianjiao)
    LinearLayout llComplaintTianjia;
    @BindView(R.id.iv_complaint_img1)
    ImageView ivComplaintImg1;
    @BindView(R.id.iv_complaint_img2)
    ImageView ivComplaintImg2;
    @BindView(R.id.iv_complaint_img3)
    ImageView ivComplaintImg3;
    @BindView(R.id.but_complaint_tijiao)
    Button butComplaintTijiao;

    MyPopupWindow popupWindow;

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
                    GalleryActivity.openActivity(GerenComplaint.this, false, 3,2);
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
        return R.layout.activity_geren_complaint;
    }

    @Override
    protected void initViews() {
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("投诉建议");
        ivShezhi.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.fl_goback, R.id.ll_complaint_tianjiao, R.id.but_complaint_tijiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_goback:
                finish();
                break;
            case R.id.ll_complaint_tianjiao:
                popupWindow=new MyPopupWindow(this,itemsOnClick);
                popupWindow.showAtLocation(this.findViewById(R.id.ll_complaint_tianjiao), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.but_complaint_tijiao:
                tiJiao();
                break;
        }
    }
    boolean b=false;
    File file;
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("resultCode===>>>>",resultCode+"");
        Log.e("requestCode===>>>>",requestCode+"");
        if(requestCode==1){
            Log.e("data=====",data+"");
            if(data==null){
                return;
            }
            Bundle bundle=data.getExtras();
            if(bundle==null){
                return;
            }
            Bitmap bitmap= (Bitmap) bundle.get("data");
            ivComplaintImg1.setImageBitmap(bitmap);
            ivComplaintImg1.setVisibility(View.VISIBLE);
            b=true;
            file= compressImage2(bitmap);
            fileMap.put("file",file);




            /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();

            //base64 encode
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);*/
//            pic = new String(encode);
//            Log.e("base64",pic);

        }else {
            Log.e("data=====",data+"");
            if(data==null){
                return;
            }
            List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);



//            BitmapFactory.Options opts = new BitmapFactory.Options();
//            opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
//            opts.inSampleSize = 4;
            for (int i = 0; i <photos.size() ; i++) {

                Log.e("图片==",photos.get(i));
                /*try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                switch (i){
                    case 0:
                        Bitmap bitmap1=BitmapFactory.decodeFile(photos.get(0),GerenShezhi.getBitmapOption(2));
                        file=compressImage2(bitmap1);
                        fileMap.put("file",file);
                        Glide.with(getBaseContext())
                                .load(file)
                                .error(R.mipmap.ic_launcher_round)
                                .into(ivComplaintImg1);
//                        ivComplaintImg1.setImageBitmap(bitmap);
                        ivComplaintImg1.setVisibility(View.VISIBLE);
                        b=true;
                        break;
                    case 1:
                        Bitmap bitmap2=BitmapFactory.decodeFile(photos.get(1),GerenShezhi.getBitmapOption(2));
                        file=compressImage2(bitmap2);
                        fileMap.put("file0",file);
                        Glide.with(getBaseContext())
                                .load(file)
                                .error(R.mipmap.ic_launcher_round)
                                .into(ivComplaintImg2);
//                        ivComplaintImg2.setImageBitmap(bitmap);
                        ivComplaintImg2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        Bitmap bitmap3=BitmapFactory.decodeFile(photos.get(2),GerenShezhi.getBitmapOption(2));
                        file=compressImage2(bitmap3);
                        fileMap.put("file1",file);
//                        ivComplaintImg3.setImageBitmap(bitmap);
                        Glide.with(getBaseContext())
                                .load(file)
                                .error(R.mipmap.ic_launcher_round)
                                .into(ivComplaintImg3);
                        ivComplaintImg3.setVisibility(View.VISIBLE);
                        break;
                }


              /*  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes = baos.toByteArray();

                //base64 encode
                byte[] encode = Base64.encode(bytes, Base64.DEFAULT);*/
//                pic = new String(encode);
//                Log.e("base64",pic);
    //            mTvShow.setText(encodeString);
            }
        }




//        Log.e(">>>>>>data>>>>>",data+"");

    }
    Map<String ,File>  fileMap=new HashMap<>();
    private void tiJiao(){
        String txt=etComplaintTxt.getText().toString();
        if(txt.isEmpty()&&!b ){
            ToastUtils.showShortToast("你没有添加任何资料");
            return;
        }
        Map<String , Object> map=new HashMap<>();
        map.put("token", Preferences.getString(this,Preferences.TOKEN));
        map.put("complain",txt);
        HTTPUtils.getFileDATA(API.BaseUrl + API.user.COMPLAINT, map,fileMap, new Callback() {
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
