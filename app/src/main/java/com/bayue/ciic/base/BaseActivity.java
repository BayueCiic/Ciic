package com.bayue.ciic.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/26.
 */

public abstract class BaseActivity extends AutoLayoutActivity {

    protected abstract void setTheme();
    protected abstract int getViewId();
    protected abstract void initViews();

    Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setTheme();
        setContentView(getViewId());
        unbinder= ButterKnife.bind(this);
        initViews();

    }
    //全屏
    private void fullScreen(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup)findViewById(Window.ID_ANDROID_CONTENT);
        View statusBarView = mContentView.getChildAt(0);
        //移除假的 View
        if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height ==getStatusBarHeight(this)) {
            mContentView.removeView(statusBarView);
        }
        //不预留空间
        if (mContentView.getChildAt(0) != null) {
            ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
        }
    }
    public static int getStatusBarHeight(Context context)
    {
        int result = 0;
        try
        {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0)
            {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        } catch (Resources.NotFoundException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
