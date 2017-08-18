package com.bayue.ciic.bean;

/**
 * Created by Administrator on 2017/8/15.
 */

public class ShipinInfoBean {

    /**
     * data : {"video_id":"16","video_name":"Cesi","click_count":"","video_brief":"这是测试视频","author":"6","video_desc":"这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频","video_img":"http://ww1.sinaimg.cn/large/7b299ca4jw1dq7zq27q2gj.jpg","video_url":"http://media.w3.org/2010/05/sintel/trailer.ogv","add_time":"2017/04/01","enterprise_id":"","enterprise_short_name":"","author_name":"","author_img":"","thumb_count":0}
     * code : 200
     * count : 0
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEyIiwidXNlcm5hbWUiOiJDb2xpbiIsInBob25lIjoiMTg5MzA1MTcwNjAiLCJlbnRlcnByaXNlX2lkIjoiMTMiLCJ0eXBlIjoiMyIsInJvbGVfaWQiOiIxNSIsImlzX2FkbWluIjoiMSIsImFjY2lkIjoidV9tS25pbnhDTkJIRUFvQmlaQ0hMVGx0YllwVEZESkciLCJ0b2tlbl90eXBlIjoiMSJ9.Xzm_N1tvWj5J0VkUIl86Qd-OuIRtA4aElMDlFjU00Wc
     */
    private String msg;
    private DataBean data;
    private int code;
    private String count;
    private String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class DataBean {
        /**
         * video_id : 16
         * video_name : Cesi
         * click_count :
         * video_brief : 这是测试视频
         * author : 6
         * video_desc : 这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频
         * video_img : http://ww1.sinaimg.cn/large/7b299ca4jw1dq7zq27q2gj.jpg
         * video_url : http://media.w3.org/2010/05/sintel/trailer.ogv
         * add_time : 2017/04/01
         * enterprise_id :
         * enterprise_short_name :
         * author_name :
         * author_img :
         * thumb_count : 0
         */

        private String video_id;
        private String video_name;
        private String click_count;
        private String video_brief;
        private String author;
        private String video_desc;
        private String video_img;
        private String video_url;
        private String add_time;
        private String enterprise_id;
        private String enterprise_short_name;
        private String author_name;
        private String author_img;
        private int thumb_count;
        private int is_thumbs;
        private int is_collection;

        public int getIs_thumbs() {
            return is_thumbs;
        }

        public void setIs_thumbs(int is_thumbs) {
            this.is_thumbs = is_thumbs;
        }

        public int getIs_collection() {
            return is_collection;
        }

        public void setIs_collection(int is_collection) {
            this.is_collection = is_collection;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getVideo_name() {
            return video_name;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        public String getClick_count() {
            return click_count;
        }

        public void setClick_count(String click_count) {
            this.click_count = click_count;
        }

        public String getVideo_brief() {
            return video_brief;
        }

        public void setVideo_brief(String video_brief) {
            this.video_brief = video_brief;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getVideo_desc() {
            return video_desc;
        }

        public void setVideo_desc(String video_desc) {
            this.video_desc = video_desc;
        }

        public String getVideo_img() {
            return video_img;
        }

        public void setVideo_img(String video_img) {
            this.video_img = video_img;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getEnterprise_short_name() {
            return enterprise_short_name;
        }

        public void setEnterprise_short_name(String enterprise_short_name) {
            this.enterprise_short_name = enterprise_short_name;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getAuthor_img() {
            return author_img;
        }

        public void setAuthor_img(String author_img) {
            this.author_img = author_img;
        }

        public int getThumb_count() {
            return thumb_count;
        }

        public void setThumb_count(int thumb_count) {
            this.thumb_count = thumb_count;
        }
    }
}
