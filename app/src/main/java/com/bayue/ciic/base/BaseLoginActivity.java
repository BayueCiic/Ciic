package com.bayue.ciic.base;

import com.bayue.ciic.R;

/**
 * Created by Administrator on 2017/6/28.
 */

public class BaseLoginActivity extends BaseActivity {
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
}
