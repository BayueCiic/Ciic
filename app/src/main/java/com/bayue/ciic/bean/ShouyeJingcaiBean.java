package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */

public class ShouyeJingcaiBean {

    /**
     * data : [{"album_id":"59","album_name":"跑不1","create_time":"12312312","update_time":"12123122","album_describe":"精彩的瞬间","cover_photo":"http://img.pconline.com.cn/images/upload/upc/tx/auto5/1311/05/c13/28344052_1383641631048_800x600.jpg","enterprise_id":"","show_id":"","draft":"","author":"6","label":"1","author_name":"这是个作者"},{"album_id":"58","album_name":"蓝球比赛2","create_time":"212312312","update_time":"12123122","album_describe":"精彩的瞬间","cover_photo":"http://img.pcauto.com.cn/images/upload/upc/tx/auto5/1504/29/c55/6134966_1430294562712_1024.jpg","enterprise_id":"","show_id":"","draft":"","author":"6","label":"1","author_name":"这是个作者"},{"album_id":"57","album_name":"学校运动会3","create_time":"12312312","update_time":"12123122","album_describe":"精彩的瞬间","cover_photo":"http://img.pconline.com.cn/images/upload/upc/tx/auto5/1311/05/c13/28344052_1383641631048_800x600.jpg","enterprise_id":"","show_id":"","draft":"","author":"6","label":"1","author_name":"这是个作者"},{"album_id":"56","album_name":"马拉松比赛4","create_time":"12312312","update_time":"12123122","album_describe":"精彩的瞬间","cover_photo":"http://img.pcauto.com.cn/images/upload/upc/tx/auto5/1504/29/c55/6134966_1430294562712_1024.jpg","enterprise_id":"","show_id":"","draft":"","author":"6","label":"1","author_name":"这是个作者"},{"album_id":"55","album_name":"跑不5","create_time":"12312312","update_time":"12123122","album_describe":"精彩的瞬间","cover_photo":"http://img.pconline.com.cn/images/upload/upc/tx/auto5/1311/05/c13/28344052_1383641631048_800x600.jpg","enterprise_id":"","show_id":"","draft":"","author":"6","label":"3","author_name":"这是个作者"},{"album_id":"54","album_name":"饼球比赛6","create_time":"12312312","update_time":"12123122","album_describe":"精彩的瞬间","cover_photo":"http://img.pcauto.com.cn/images/upload/upc/tx/auto5/1504/29/c55/6134966_1430294562712_1024.jpg","enterprise_id":"","show_id":"","draft":"","author":"6","label":"1","author_name":"这是个作者"}]
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
         * album_id : 59
         * album_name : 跑不1
         * create_time : 12312312
         * update_time : 12123122
         * album_describe : 精彩的瞬间
         * cover_photo : http://img.pconline.com.cn/images/upload/upc/tx/auto5/1311/05/c13/28344052_1383641631048_800x600.jpg
         * enterprise_id :
         * show_id :
         * draft :
         * author : 6
         * label : 1
         * author_name : 这是个作者
         */

        private String album_id;
        private String album_name;
        private String create_time;
        private String update_time;
        private String album_describe;
        private String cover_photo;
        private String enterprise_id;
        private String show_id;
        private String draft;
        private String author;
        private String label;
        private String author_name;

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getAlbum_name() {
            return album_name;
        }

        public void setAlbum_name(String album_name) {
            this.album_name = album_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getAlbum_describe() {
            return album_describe;
        }

        public void setAlbum_describe(String album_describe) {
            this.album_describe = album_describe;
        }

        public String getCover_photo() {
            return cover_photo;
        }

        public void setCover_photo(String cover_photo) {
            this.cover_photo = cover_photo;
        }

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
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
