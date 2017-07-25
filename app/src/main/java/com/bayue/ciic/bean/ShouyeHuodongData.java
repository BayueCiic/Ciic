package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class ShouyeHuodongData {

    /**
     * data : [{"activity_id":"22","create_time":"1231232","content":"活动中在是打发时间9","author":"6","count_user_number":"20","status":"1","title":"这是活动吧","activity_reg":"","show_id":"","draft":"","enterprise_id":"25","activity_img":"http://scimg.jb51.net/allimg/151026/14-1510261033280-L.jpg","label":"1","author_name":"这是个作者"},{"activity_id":"21","create_time":"1231232","content":"活动中在是打发时间7","author":"6","count_user_number":"20","status":"1","title":"这是活动吧","activity_reg":"1","show_id":"","draft":"","enterprise_id":"25","activity_img":"http://scimg.jb51.net/allimg/151026/14-1510261033280-L.jpg","label":"1","author_name":"这是个作者"},{"activity_id":"20","create_time":"1231232","content":"活动中在是打发时间6","author":"6","count_user_number":"20","status":"1","title":"这是活动吧","activity_reg":"2","show_id":"","draft":"","enterprise_id":"1","activity_img":"http://scimg.jb51.net/allimg/151026/14-1510261033280-L.jpg","label":"1","author_name":"这是个作者"},{"activity_id":"19","create_time":"1231232","content":"活动中在是打发时间5","author":"6","count_user_number":"20","status":"","title":"这是活动吧","activity_reg":"","show_id":"","draft":"","enterprise_id":"1","activity_img":"http://scimg.jb51.net/allimg/151026/14-1510261033280-L.jpg","label":"1","author_name":"这是个作者"}]
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
         * activity_id : 22
         * create_time : 1231232
         * content : 活动中在是打发时间9
         * author : 6
         * count_user_number : 20
         * status : 1
         * title : 这是活动吧
         * activity_reg :
         * show_id :
         * draft :
         * enterprise_id : 25
         * activity_img : http://scimg.jb51.net/allimg/151026/14-1510261033280-L.jpg
         * label : 1
         * author_name : 这是个作者
         */

        private String activity_id;
        private String create_time;
        private String content;
        private String author;
        private String count_user_number;
        private String status;
        private String title;
        private String activity_reg;
        private String show_id;
        private String draft;
        private String enterprise_id;
        private String activity_img;
        private String label;
        private String author_name;

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCount_user_number() {
            return count_user_number;
        }

        public void setCount_user_number(String count_user_number) {
            this.count_user_number = count_user_number;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getActivity_reg() {
            return activity_reg;
        }

        public void setActivity_reg(String activity_reg) {
            this.activity_reg = activity_reg;
        }

        public String getShow_id() {
            return show_id;
        }

        public void setShow_id(String show_id) {
            this.show_id = show_id;
        }

        public String getDraft() {
            return draft;
        }

        public void setDraft(String draft) {
            this.draft = draft;
        }

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getActivity_img() {
            return activity_img;
        }

        public void setActivity_img(String activity_img) {
            this.activity_img = activity_img;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }
    }
}
