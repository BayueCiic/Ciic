package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class CanyuBean {

    /**
     * data : [{"activity_id":"4","create_time":"1231232","content":"活动中在是打发时间4","author":"6","count_user_number":"20","status":"1","title":"这是活动吧","activity_reg":"0","author_name":"这是个作者"},{"activity_id":"3","create_time":"1231232","content":"活动中在是打发时间3","author":"6","count_user_number":"20","status":"1","title":"这是活动吧","activity_reg":"0","author_name":"这是个作者"},{"activity_id":"2","create_time":"1231232","content":"活动中在是打发时间2","author":"6","count_user_number":"20","status":"1","title":"这是活动吧","activity_reg":"0","author_name":"这是个作者"},{"activity_id":"1","create_time":"1231232","content":"活动中在是打发时间1","author":"6","count_user_number":"20","status":"1","title":"这是活动吧","activity_reg":"0","author_name":"这是个作者"}]
     * code : 200
     */

    private int code;
    private List<DataBean> data;
    private String msg;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * activity_id : 4
         * create_time : 1231232
         * content : 活动中在是打发时间4
         * author : 6
         * count_user_number : 20
         * status : 1
         * title : 这是活动吧
         * activity_reg : 0
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
        private String author_name;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

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

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }
    }
}
