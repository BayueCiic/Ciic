package com.bayue.ciic.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bayue.ciic.R;
import com.bayue.ciic.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/30.
 */

public class PartyPlatfrom extends BaseFragment {
    @BindView(R.id.rlv_platfrom)
    RecyclerView rlvPlatfrom;
    Unbinder unbinder;

    @Override
    protected int getViewId() {
        return R.layout.frament_news_platfrom;
    }

    @Override
    public void init() {

        rlvPlatfrom.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlvPlatfrom.setHasFixedSize(true);
        rlvPlatfrom.setItemAnimator(new DefaultItemAnimator());
        rlvPlatfrom.setAdapter(new PartyPlatfrom.Myadapter());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    class Myadapter extends RecyclerView.Adapter<PartyPlatfrom.Myadapter.MyHolder> {

        public class MyHolder extends RecyclerView.ViewHolder{

            public MyHolder(View itemView) {
                super(itemView);
            }
        }
        @Override
        public PartyPlatfrom.Myadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_party_platfrom,parent,false);
            PartyPlatfrom.Myadapter.MyHolder holder=new PartyPlatfrom.Myadapter.MyHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(PartyPlatfrom.Myadapter.MyHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 8;
        }
    }

}
