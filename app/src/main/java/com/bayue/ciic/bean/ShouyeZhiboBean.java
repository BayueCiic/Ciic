package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class ShouyeZhiboBean {


    /**
     * data : [{"title":"好","room_id":"9","cid":"","video_img":"http://img1.3lian.com/img2011/w1/108/90/63.jpg","room_name":"房间1","author":"6","author_name":"这是个作者"}]
     * code : 200
     * count : 0
     */
    private String msg;
    private int code;
    private String count;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 好
         * room_id : 9
         * cid :
         * video_img : http://img1.3lian.com/img2011/w1/108/90/63.jpg
         * room_name : 房间1
         * author : 6
         * author_name : 这是个作者
         */

        private String title;
        private String room_id;
        private String cid;
        private String video_img;
        private String room_name;
        private String author;
        private String author_name;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getVideo_img() {
            return video_img;
        }

        public void setVideo_img(String video_img) {
            this.video_img = video_img;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }
    }
}
