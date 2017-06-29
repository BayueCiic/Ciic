package com.bayue.ciic.activity;

import android.os.Bundle;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void setTheme() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_geren_shezhi;
    }

    @Override
    protected void initViews() {
        ivGoback.setImageResource(R.mipmap.back_3x);
        tvTitletxt.setText("编辑资料");
        ivShezhi.setVisibility(View.INVISIBLE);
        tvShezhi.setText("保存");


        elvShezhi.setGroupIndicator(null);
        elvShezhi.setAdapter(new Myadapter());
        elvShezhi.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                    if(parent.isGroupExpanded(groupPosition)){

                        Log.e("展开",">>>>>>>>>>>>>>>");

                        ImageView iv= (ImageView) v.findViewById(R.id.iv_item_updown);
                        iv.setImageResource(R.mipmap.bianjiziliao_kaxia_3x);

                    }else {
                        Log.e("关闭",">>>>>>>>>>>>>>>");
                        ImageView iv= (ImageView) v.findViewById(R.id.iv_item_updown);
                        iv.setImageResource(R.mipmap.bianjiziliao_shou_3x);

                    }


                return false;
            }
        });
        elvShezhi.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                elvShezhi.collapseGroup(groupPosition);


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
                break;
            case R.id.rl_shezhi_toux:
                break;
        }
    }
    class Myadapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return 1;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 4;
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
               convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_shezhi,null);
            }


            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geren_shezhi_sub,null);
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
