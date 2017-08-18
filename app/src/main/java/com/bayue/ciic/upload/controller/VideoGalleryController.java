package com.bayue.ciic.upload.controller;

import android.app.Activity;
import android.content.Intent;


import com.bayue.ciic.upload.adapter.VideoGalleryAdapter;
import com.bayue.ciic.upload.model.VideoGalleryDataAccessor;

import java.util.List;

/**
 * Created by zhukkun on 2/27/17.
 */
public class VideoGalleryController extends BaseUiController<VideoGalleryController.VideoSelectUi>{

    public static final String EXTRAL_LARGEST_COUNT = "largest_count";
    public static final int DEFAULT_LARGEST_COUNT = 5;

    private int largest_count = DEFAULT_LARGEST_COUNT;
    private int selected_count = 0;

    VideoGalleryDataAccessor dataAccesser;

    public VideoGalleryController(Activity context){
        dataAccesser = new VideoGalleryDataAccessor();
        dataAccesser.loadPhoneVideoData(context);
        dataAccesser.sortByDate();
    }

    @Override
    public void handleIntent(Intent intent) {
        largest_count = intent.getIntExtra(EXTRAL_LARGEST_COUNT, DEFAULT_LARGEST_COUNT);
        dataAccesser.setLargestCount(largest_count);
    }

    @Override
    protected void onUiDetached(VideoSelectUi ui) {

    }

    @Override
    protected void populateUi(VideoSelectUi ui) {
        ui.populateVideoData(dataAccesser.getLineContainerList());
        ui.initBottomBar(selected_count, largest_count);
    }

    @Override
    protected void onUiAttached(VideoSelectUi ui) {
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onSuspend() {

    }

    public interface VideoSelectUi extends Ui{

        void populateVideoData(List<VideoGalleryAdapter.LineContainer> datas);

        void initBottomBar(int selected_count, int largest_count);
    }

    public VideoGalleryDataAccessor getDataAccessor(){
        return dataAccesser;
    }

}
