package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class PZhiboBean {

    /**
     * data : [{"room_id":"9","cid":"","video_img":"http://img1.3lian.com/img2011/w1/108/90/63.jpg","room_name":"房间1","author":"keefer","author_id":"","click_count":"","room_desc":"","push_url":"rtmp://pdl065914eb.live.126.net/live/fcbb70eedbe7402bb549e0879cee30a7?wsSecret=ef2ee542cffe6e3d3235b4081ac43d23&wsTime=1493885491","pull_url":"http://flvdl065914eb.live.126.net/live/fcbb70eedbe7402bb549e0879cee30a7.flv?netease=flvdl065914eb.live.126.net","h5_pull_url":"http://pullhlsdl065914eb.live.126.net/live/fcbb70eedbe7402bb549e0879cee30a7/playlist.m3u8","add_time":"2147483647","status":"","enterprise_id":"","author_name":""}]
     * code : 200
     * count : 8
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
         * room_id : 9
         * cid :
         * video_img : http://img1.3lian.com/img2011/w1/108/90/63.jpg
         * room_name : 房间1
         * author : keefer
         * author_id :
         * click_count :
         * room_desc :
         * push_url : rtmp://pdl065914eb.live.126.net/live/fcbb70eedbe7402bb549e0879cee30a7?wsSecret=ef2ee542cffe6e3d3235b4081ac43d23&wsTime=1493885491
         * pull_url : http://flvdl065914eb.live.126.net/live/fcbb70eedbe7402bb549e0879cee30a7.flv?netease=flvdl065914eb.live.126.net
         * h5_pull_url : http://pullhlsdl065914eb.live.126.net/live/fcbb70eedbe7402bb549e0879cee30a7/playlist.m3u8
         * add_time : 2147483647
         * status :
         * enterprise_id :
         * author_name :
         */

        private String room_id;
        private String cid;
        private String video_img;
        private String room_name;
        private String author;
        private String author_id;
        private String click_count;
        private String room_desc;
        private String push_url;
        private String pull_url;
        private String h5_pull_url;
        private String add_time;
        private String status;
        private String enterprise_id;
        private String author_name;

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

        public String getAuthor_id() {
            return author_id;
        }

        public void setAuthor_id(String author_id) {
            this.author_id = author_id;
        }

        public String getClick_count() {
            return click_count;
        }

        public void setClick_count(String click_count) {
            this.click_count = click_count;
        }

        public String getRoom_desc() {
            return room_desc;
        }

        public void setRoom_desc(String room_desc) {
            this.room_desc = room_desc;
        }

        public String getPush_url() {
            return push_url;
        }

        public void setPush_url(String push_url) {
            this.push_url = push_url;
        }

        public String getPull_url() {
            return pull_url;
        }

        public void setPull_url(String pull_url) {
            this.pull_url = pull_url;
        }

        public String getH5_pull_url() {
            return h5_pull_url;
        }

        public void setH5_pull_url(String h5_pull_url) {
            this.h5_pull_url = h5_pull_url;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }
    }
}
