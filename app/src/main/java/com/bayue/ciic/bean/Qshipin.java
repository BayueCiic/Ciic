package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class Qshipin {

    /**
     * data : [{"video_id":"29","video_name":"1231","click_count":"","video_img":"http://ww1.sinaimg.cn/large/7b299ca4jw1dq7zq27q2gj.jpg","video_url":"http://www.baidu.com","author":"U_eBWiGynUHUVpwZlKEE","video_duration":"00:01","enterprise_shot_name":"捌跃keji","enterprise_id":"21"}]
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
         * video_id : 29
         * video_name : 1231
         * click_count :
         * video_img : http://ww1.sinaimg.cn/large/7b299ca4jw1dq7zq27q2gj.jpg
         * video_url : http://www.baidu.com
         * author : U_eBWiGynUHUVpwZlKEE
         * video_duration : 00:01
         * enterprise_shot_name : 捌跃keji
         * enterprise_id : 21
         */

        private String video_id;
        private String video_name;
        private String click_count;
        private String video_img;
        private String video_url;
        private String author;
        private String video_duration;
        private String enterprise_shot_name;
        private String enterprise_id;

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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(String video_duration) {
            this.video_duration = video_duration;
        }

        public String getEnterprise_shot_name() {
            return enterprise_shot_name;
        }

        public void setEnterprise_shot_name(String enterprise_shot_name) {
            this.enterprise_shot_name = enterprise_shot_name;
        }

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }
    }
}
