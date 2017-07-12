package com.bayue.ciic.base;

import android.view.KeyEvent;
import android.widget.Toast;

import com.bayue.ciic.App;
import com.bayue.ciic.R;

/**
 * Created by Administrator on 2017/6/28.
 */

public class BaseLoginActivity extends BaseActivity {

    //改写物理按键——返回的逻辑
    private long firstTime = 0;
    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return 0;
    }

    @Override
    protected void initViews() {

    }
    public void delActivity(){
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (mWebView.canGoBack()) {
//                mWebView.goBack();//返回上一页面
//                return true;
//            } else {
//                System.exit(0);//退出程序
//            }
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {                                         //如果两次按键时间间隔大于2秒，则不退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;//更新firstTime
                return true;
            } else {
                //两次按键小于2秒时，退出应用
//                App.bgActivity.finish();
                finish();
                System.exit(0);
//                finish();

            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
