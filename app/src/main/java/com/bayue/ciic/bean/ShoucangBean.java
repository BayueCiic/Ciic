package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class ShoucangBean {


    /**
     * data : [{"video_id":"15","cat_id":"0","video_name":"1231","click_count":null,"video_brief":"","author":"6","video_desc":"这是测试食品","video_img":"http://ww1.sinaimg.cn/large/7b299ca4jw1dq7zq27q2gj.jpg","video_url":"http://www.baidu.com","add_time":"1492391624","video_duration":"1","is_delete":"0","last_update":null,"author_name":"这是个作者"},{"video_id":"20","cat_id":"29","video_name":"Cesi","click_count":null,"video_brief":"这是测试视频","author":"6","video_desc":"这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频","video_img":"http://ww1.sinaimg.cn/large/7b299ca4jw1dq7zq27q2gj.jpg","video_url":"http://media.w3.org/2010/05/sintel/trailer.ogv","add_time":"1491042753","video_duration":"100","is_delete":"0","last_update":null,"author_name":"这是个作者"},{"video_id":"21","cat_id":"29","video_name":"Cesi","click_count":null,"video_brief":"这是测试视频","author":"6","video_desc":"这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频这是测试视频","video_img":"http://ww1.sinaimg.cn/large/7b299ca4jw1dq7zq27q2gj.jpg","video_url":"http://media.w3.org/2010/05/sintel/trailer.ogv","add_time":"1491042753","video_duration":"100","is_delete":"0","last_update":null,"author_name":"这是个作者"}]
     * code : 200
     */

    private String msg;
    private int code;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * video_id : 15
         * cat_id : 0
         * video_name : 1231
         * click_count : null
         * video_brief :
         * author : 6
         * video_desc : 这是测试食品
         * video_img : http://ww1.sinaimg.cn/large/7b299ca4jw1dq7zq27q2gj.jpg
         * video_url : http://www.baidu.com
         * add_time : 1492391624
         * video_duration : 1
         * is_delete : 0
         * last_update : null
         * author_name : 这是个作者
         */

        private boolean isShow;
        private boolean selected;
        private String video_id;
        private String cat_id;
        private String video_name;
        private Object click_count;
        private String video_brief;
        private String author;
        private String video_desc;
        private String video_img;
        private String video_url;
        private String add_time;
        private String video_duration;
        private String is_delete;
        private Object last_update;
        private String author_name;

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getVideo_name() {
            return video_name;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        public Object getClick_count() {
            return click_count;
        }

        public void setClick_count(Object click_count) {
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

        public String getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(String video_duration) {
            this.video_duration = video_duration;
        }

        public String getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(String is_delete) {
            this.is_delete = is_delete;
        }

        public Object getLast_update() {
            return last_update;
        }

        public void setLast_update(Object last_update) {
            this.last_update = last_update;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }
    }
}
