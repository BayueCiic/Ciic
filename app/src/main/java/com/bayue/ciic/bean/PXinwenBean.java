package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class PXinwenBean {


    /**
     * data : [{"news_id":"33","title":"这是新闻1213123","describe":"","author":"43","add_time":"1500349832","draft":"","news_img":"","author_name":"装修公司23551"},{"news_id":"14","title":"这是新闻8","describe":"这是新闻是多少","author":"6","add_time":"1498789063","draft":"","news_img":"","author_name":"这是个作者"},{"news_id":"13","title":"这是新闻7","describe":"这是新闻是多少","author":"12的","add_time":"1498789063","draft":"","news_img":"","author_name":""},{"news_id":"6","title":"这是新闻6","describe":"这是新闻是多少","author":"12是","add_time":"1498789063","draft":"","news_img":"","author_name":""},{"news_id":"5","title":"这是新闻5","describe":"这是新闻是多少","author":"12是啊","add_time":"1498789063","draft":"","news_img":"","author_name":""},{"news_id":"4","title":"这是新闻4","describe":"这是新闻是多少","author":"12啊","add_time":"1498789063","draft":"","news_img":"","author_name":""},{"news_id":"3","title":"这是新闻3","describe":"这是新闻是多少","author":"12哦","add_time":"1498789063","draft":"","news_img":"","author_name":""},{"news_id":"2","title":"这是新闻2","describe":"这是新闻是多少","author":"12屁","add_time":"1498789063","draft":"","news_img":"","author_name":""},{"news_id":"1","title":"这是新闻1","describe":"这是新闻是多少","author":"12ewq","add_time":"1498789063","draft":"","news_img":"","author_name":""},{"news_id":"32","title":"这是新闻8","describe":"这是新闻是多少","author":"6","add_time":"1498789063","draft":"","news_img":"","author_name":"这是个作者"}]
     * code : 200
     * count : 33
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
         * news_id : 33
         * title : 这是新闻1213123
         * describe :
         * author : 43
         * add_time : 1500349832
         * draft :
         * news_img :
         * author_name : 装修公司23551
         */

        private String news_id;
        private String title;
        private String describe;
        private String author;
        private String add_time;
        private String draft;
        private String news_img;
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

        public String getDraft() {
            return draft;
        }

        public void setDraft(String draft) {
            this.draft = draft;
        }

        public String getNews_img() {
            return news_img;
        }

        public void setNews_img(String news_img) {
            this.news_img = news_img;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }
    }
}
