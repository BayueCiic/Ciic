package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */

public class NewsBean {


    /**
     * data : [{"news_id":"8","title":"这是新闻8","describe":"这是新闻是多少","author":"6","add_time":"1498789063","enterprise_id":"43","draft":null,"new_img":null,"author_name":"这是个作者"},{"news_id":"7","title":"这是新闻7","describe":"这是新闻是多少","author":"12的","add_time":"1498789063","enterprise_id":null,"draft":"43","new_img":null,"author_name":null}]
     * code : 200
     * count : 10
     */

    private int code;
    private String count;
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
         * news_id : 8
         * title : 这是新闻8
         * describe : 这是新闻是多少
         * author : 6
         * add_time : 1498789063
         * enterprise_id : 43
         * draft : null
         * news_img : null
         * author_name : 这是个作者
         */

        private String news_id;
        private String title;
        private String describe;
        private String author;
        private String add_time;
        private String enterprise_id;
        private Object draft;
        private Object news_img;
        private String author_name;

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
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

        public Object getDraft() {
            return draft;
        }

        public void setDraft(Object draft) {
            this.draft = draft;
        }

        public Object getNew_img() {
            return news_img;
        }

        public void setNew_img(Object new_img) {
            this.news_img = new_img;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }
    }
}
