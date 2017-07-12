package com.bayue.ciic.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/10.
 */

public class PhotoDetails extends BaseActivity {

    @BindView(R.id.banner_img)
    Banner bannerImg;
    @BindView(R.id.iv_details_left)
    ImageView ivDetailsLeft;
    @BindView(R.id.iv_details_right)
    ImageView ivDetailsRight;

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_photo_details;
    }

    int mCurrentPosition;

    @Override
    protected void initViews() {

        Log.e(setImagView().size() + "<<<<<<", mCurrentPosition + ">>>>>>>>>>");

        bannerImg.setBannerStyle(BannerConfig.NUM_INDICATOR);

        bannerImg.setImageLoader(new GlideImageLoader());

        bannerImg.setImages(setImags());

        bannerImg.setBannerAnimation(Transformer.DepthPage);

        bannerImg.isAutoPlay(false);

        bannerImg.setDelayTime(2000);

        bannerImg.setIndicatorGravity(BannerConfig.CENTER);


//        bannerImg.onPageSelected(3);
        bannerImg.start();

        bannerImg.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private List<Integer> setImags(){

        List<Integer> imags=new ArrayList<>();

        imags.add(R.mipmap.shouye_img10_2x);
        imags.add(R.mipmap.shouye_img03_3x);
        imags.add(R.mipmap.shouye_img04_3x);
        imags.add(R.mipmap.shouye_img05_3x);
        imags.add(R.mipmap.shouye_img07_3x);
        imags.add(R.mipmap.shouye_img08_3x);
        imags.add(R.mipmap.shouye_img09_3x);

        return imags;

    }

    private List<ImageView> setImagView() {

        List<ImageView> data = new ArrayList<>();
        ImageView imageView0 = new ImageView(this, null);
        imageView0.setImageResource(R.mipmap.shouye_img10_2x);
        data.add(imageView0);

        ImageView imageView1 = new ImageView(this, null);
        imageView1.setImageResource(R.mipmap.shouye_img03_3x);
        data.add(imageView1);

        ImageView imageView2 = new ImageView(this, null);
        imageView2.setImageResource(R.mipmap.shouye_img04_3x);
        data.add(imageView2);

        ImageView imageView3 = new ImageView(this, null);
        imageView3.setImageResource(R.mipmap.shouye_img05_3x);
        data.add(imageView3);

        ImageView imageView4 = new ImageView(this, null);
        imageView4.setImageResource(R.mipmap.shouye_img07_3x);
        data.add(imageView4);

        ImageView imageView5 = new ImageView(this, null);
        imageView5.setImageResource(R.mipmap.shouye_img08_3x);
        data.add(imageView5);

        ImageView imageView6 = new ImageView(this, null);
        imageView6.setImageResource(R.mipmap.shouye_img09_3x);
        data.add(imageView6);

        return data;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_details_left, R.id.iv_details_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_details_left:
//                if(mCurrentPosition==0){
//                    return;
//                }
                Log.e("图片页面选择","========");
                bannerImg.toRealPosition(3);
//                bannerImg.start();
                break;
            case R.id.iv_details_right:
                break;
        }
    }
    class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             常用的图片加载库：
             Universal Image Loader：一个强大的图片加载库，包含各种各样的配置，最老牌，使用也最广泛。
             Picasso: Square出品，必属精品。和OkHttp搭配起来更配呦！
             Volley ImageLoader：Google官方出品，可惜不能加载本地图片~
             Fresco：Facebook出的，天生骄傲！不是一般的强大。
             Glide：Google推荐的图片加载库，专注于流畅的滚动。
             */

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

            //Picasso 加载图片简单用法
//            Picasso.with(context).load((Uri) path).into(imageView);

            //用fresco加载图片简单用法
           /* Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);*/
        }
       /* //提供createImageView 方法，如果不用可以不重写这个方法，方便fresco自定义ImageView
        @Override
        public ImageView createImageView(Context context) {
            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
            return simpleDraweeView;
        }*/
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
