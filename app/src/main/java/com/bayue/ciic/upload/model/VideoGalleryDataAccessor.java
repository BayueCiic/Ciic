package com.bayue.ciic.upload.model;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


import com.bayue.ciic.upload.VideoUtils;
import com.bayue.ciic.upload.adapter.VideoGalleryAdapter;
import com.bayue.ciic.upload.constant.UploadType;
import com.bayue.ciic.upload.controller.VideoGalleryController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zhukkun on 2/27/17.
 */
public class VideoGalleryDataAccessor {

    private List<VideoItem> rawVideoItems = new ArrayList<>();
    private List<VideoGalleryAdapter.LineContainer> lineContainerList = new ArrayList<>();;
    private List<VideoItem> selectedVideoItems = new ArrayList<>();

    private Cursor videoCursor;
    private int video_column_index;

    private int largest_count = VideoGalleryController.DEFAULT_LARGEST_COUNT;

    public void loadPhoneVideoData(Activity context){
        String[] proj = { MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.VideoColumns.DATE_TAKEN,
                MediaStore.Video.VideoColumns.DURATION
        };

        //videoCursor = new CursorLoader(context, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj, null, null, null).loadInBackground();

        videoCursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj, null, null, MediaStore.Video.VideoColumns.DATE_TAKEN + " DESC");
        if(videoCursor == null) return;

        int count = videoCursor.getCount();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < count; i++) {
            VideoItem videoItem = new VideoItem();
            videoItem.setType(UploadType.VIDEO); // 普通视频
            videoCursor.moveToPosition(i);

            video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            videoItem.setId(videoCursor.getLong(video_column_index)+ "");
            videoItem.setUriString(Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoItem.getId()).toString());

            video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME);
            videoItem.setDisplayName(videoCursor.getString(video_column_index));

            video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            videoItem.setFilePath(videoCursor.getString(video_column_index));

            video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION);
            long duration  = videoCursor.getLong(video_column_index);
            if (duration>0) {
                videoItem.setDuration(VideoUtils.getFormatDuration(duration));
            }


            video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.SIZE);
            videoItem.setSize(videoCursor.getLong(video_column_index));

            video_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATE_TAKEN);
            calendar.setTimeInMillis(videoCursor.getLong(video_column_index));
            videoItem.setDateTaken(calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) <10? "0"+ calendar.get(Calendar.MONTH) : calendar.get(Calendar.MONTH)));
            rawVideoItems.add(videoItem);
        }

        videoCursor.close();
    }

    public List<VideoItem> getRawVideoItems() {
        return rawVideoItems;
    }



    public boolean isSelected(int line, int column) {
        return selectedVideoItems.contains(lineContainerList.get(line).getVideoItems().get(column));
    }

    public void markSelected(int line, int column, boolean select){
        if(select && !isSelected(line, column) && selectedVideoItems.size()< largest_count){
           selectedVideoItems.add(lineContainerList.get(line).getVideoItems().get(column));
        }else if(!select && isSelected(line, column)){
           selectedVideoItems.remove(lineContainerList.get(line).getVideoItems().get(column));
        }
    }

    public int getLargestCount() {
        return largest_count;
    }

    public int getSeletedCount() {
        return selectedVideoItems.size();
    }

    public boolean canSelecteMore() {
        return getSeletedCount()<getLargestCount();
    }

    public ArrayList<VideoItem> getSelectedArray() {
       return (ArrayList<VideoItem>) selectedVideoItems;
    }

    public void setLargestCount(int largest_count) {
        this.largest_count = largest_count;
    }

    public List<VideoGalleryAdapter.LineContainer> getLineContainerList() {
        return lineContainerList;
    }

    public void sortByDate() {
        lineContainerList = new ArrayList<>();

        VideoGalleryAdapter.LineContainer lineContainer = new VideoGalleryAdapter.LineContainer();


        for (int i = 0; i < rawVideoItems.size(); i++) {
            VideoItem videoItem = rawVideoItems.get(i);
            String VideoCreateDate = getCreateDateString(videoItem);

            if(lineContainer.getDate() == null || !lineContainer.getDate().equals(VideoCreateDate)){
                if(i!=0){
                    lineContainerList.add(lineContainer);
                    lineContainer = new VideoGalleryAdapter.LineContainer();
                }

                lineContainer.setDate(VideoCreateDate);
                lineContainer.setDateTip(true);
                lineContainerList.add(lineContainer);

                lineContainer = new VideoGalleryAdapter.LineContainer();
            }

            if(lineContainer.getVideoItems().size()==3){
                lineContainerList.add(lineContainer);
                lineContainer = new VideoGalleryAdapter.LineContainer();
            }
            lineContainer.getVideoItems().add(videoItem);
            lineContainer.setDate(VideoCreateDate);
        }
        lineContainerList.add(lineContainer);
    }

    private String getCreateDateString(VideoItem videoItem) {
        return videoItem.getDateTaken();
    }


    public boolean isMoreThan_1G(int line, int column) {
        return VideoUtils.isSizeMoreThan_1Gb(lineContainerList.get(line).getVideoItems().get(column).getSize());
    }

    public boolean isMoreThan_5G(int line, int column) {
        return VideoUtils.isSizeMoreThan_5Gb(lineContainerList.get(line).getVideoItems().get(column).getSize());
    }
}
