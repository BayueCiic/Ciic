package com.bayue.ciic;

import android.app.Activity;
import android.app.Application;

import com.bayue.ciic.utils.Utils;

/**
 * Created by Administrator on 2017/6/26.
 */

public class App extends Application {
    public static Activity bgActivity;
    public static Activity mainActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
