package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class Qhuodong {

    /**
     * data : [{"activity_id":"14","title":"一个现实不是啊","create_time":"1499743689","activity_img":"http://scimg.jb51.net/allimg/151026/14-1510261033280-L.jpg","author":"U_eBWiGynUHUVpwZlKEE","enterprise_id":"1","create_time1":"2017/07/11","create_time2":"2017-07-11","reg_text":"进行中","enterprise_short_name":"","content":""}]
     * code : 200
     * count : 2
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
         * activity_id : 14
         * title : 一个现实不是啊
         * create_time : 1499743689
         * activity_img : http://scimg.jb51.net/allimg/151026/14-1510261033280-L.jpg
         * author : U_eBWiGynUHUVpwZlKEE
         * enterprise_id : 1
         * create_time1 : 2017/07/11
         * create_time2 : 2017-07-11
         * reg_text : 进行中
         * enterprise_short_name :
         * content :
         */

        private String activity_id;
        private String title;
        private String create_time;
        private String activity_img;
        private String author;
        private String enterprise_id;
        private String create_time1;
        private String create_time2;
        private String reg_text;
        private String enterprise_short_name;
        private String content;

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getActivity_img() {
            return activity_img;
        }

        public void setActivity_img(String activity_img) {
            this.activity_img = activity_img;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getCreate_time1() {
            return create_time1;
        }

        public void setCreate_time1(String create_time1) {
            this.create_time1 = create_time1;
        }

        public String getCreate_time2() {
            return create_time2;
        }

        public void setCreate_time2(String create_time2) {
            this.create_time2 = create_time2;
        }

        public String getReg_text() {
            return reg_text;
        }

        public void setReg_text(String reg_text) {
            this.reg_text = reg_text;
        }

        public String getEnterprise_short_name() {
            return enterprise_short_name;
        }

        public void setEnterprise_short_name(String enterprise_short_name) {
            this.enterprise_short_name = enterprise_short_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
