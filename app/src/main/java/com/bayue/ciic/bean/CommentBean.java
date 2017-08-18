package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class CommentBean {


    /**
     * data : [{"comment_id":"5","id_value":"0","content":"不知所措，不知所谓","status":"1","video_id":"20","add_time":"1490854437","comment_rank":"1","add_time1":"2017/03/30","add_time2":"2017-03-30","child_count":2,"author_name":"陆文龙","author_avatar":"http://zz.bayuenet.com/Uploads/user/2017-08-14/599131ffdde25.png","child":[{"comment_id":"11","id_value":"5","content":"不知所措，不知所谓","status":"1","video_id":"20","add_time":"1490854437","comment_rank":"2","add_time1":"2017/03/30","add_time2":"2017-03-30","author_name":"陆文龙","author_avatar":"http://zz.bayuenet.com/Uploads/user/2017-08-14/599131ffdde25.png"},{"comment_id":"12","id_value":"5","content":"不知所措，不知所谓","status":"1","video_id":"20","add_time":"1490854437","comment_rank":"2","add_time1":"2017/03/30","add_time2":"2017-03-30","author_name":"陆文龙","author_avatar":"http://zz.bayuenet.com/Uploads/user/2017-08-14/599131ffdde25.png"}]}]
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
         * comment_id : 5
         * id_value : 0
         * content : 不知所措，不知所谓
         * status : 1
         * video_id : 20
         * add_time : 1490854437
         * comment_rank : 1
         * add_time1 : 2017/03/30
         * add_time2 : 2017-03-30
         * child_count : 2
         * author_name : 陆文龙
         * author_avatar : http://zz.bayuenet.com/Uploads/user/2017-08-14/599131ffdde25.png
         * child : [{"comment_id":"11","id_value":"5","content":"不知所措，不知所谓","status":"1","video_id":"20","add_time":"1490854437","comment_rank":"2","add_time1":"2017/03/30","add_time2":"2017-03-30","author_name":"陆文龙","author_avatar":"http://zz.bayuenet.com/Uploads/user/2017-08-14/599131ffdde25.png"},{"comment_id":"12","id_value":"5","content":"不知所措，不知所谓","status":"1","video_id":"20","add_time":"1490854437","comment_rank":"2","add_time1":"2017/03/30","add_time2":"2017-03-30","author_name":"陆文龙","author_avatar":"http://zz.bayuenet.com/Uploads/user/2017-08-14/599131ffdde25.png"}]
         */

        private String comment_id;
        private String id_value;
        private String content;
        private String status;
        private String video_id;
        private String add_time;
        private String comment_rank;
        private String add_time1;
        private String add_time2;
        private int child_count;
        private String author_name;
        private String author_avatar;
        private List<ChildBean> child;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getId_value() {
            return id_value;
        }

        public void setId_value(String id_value) {
            this.id_value = id_value;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getComment_rank() {
            return comment_rank;
        }

        public void setComment_rank(String comment_rank) {
            this.comment_rank = comment_rank;
        }

        public String getAdd_time1() {
            return add_time1;
        }

        public void setAdd_time1(String add_time1) {
            this.add_time1 = add_time1;
        }

        public String getAdd_time2() {
            return add_time2;
        }

        public void setAdd_time2(String add_time2) {
            this.add_time2 = add_time2;
        }

        public int getChild_count() {
            return child_count;
        }

        public void setChild_count(int child_count) {
            this.child_count = child_count;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getAuthor_avatar() {
            return author_avatar;
        }

        public void setAuthor_avatar(String author_avatar) {
            this.author_avatar = author_avatar;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ChildBean {
            /**
             * comment_id : 11
             * id_value : 5
             * content : 不知所措，不知所谓
             * status : 1
             * video_id : 20
             * add_time : 1490854437
             * comment_rank : 2
             * add_time1 : 2017/03/30
             * add_time2 : 2017-03-30
             * author_name : 陆文龙
             * author_avatar : http://zz.bayuenet.com/Uploads/user/2017-08-14/599131ffdde25.png
             */

            private String comment_id;
            private String id_value;
            private String content;
            private String status;
            private String video_id;
            private String add_time;
            private String comment_rank;
            private String add_time1;
            private String add_time2;
            private String author_name;
            private String author_avatar;

            public String getComment_id() {
                return comment_id;
            }

            public void setComment_id(String comment_id) {
                this.comment_id = comment_id;
            }

            public String getId_value() {
                return id_value;
            }

            public void setId_value(String id_value) {
                this.id_value = id_value;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getVideo_id() {
                return video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getComment_rank() {
                return comment_rank;
            }

            public void setComment_rank(String comment_rank) {
                this.comment_rank = comment_rank;
            }

            public String getAdd_time1() {
                return add_time1;
            }

            public void setAdd_time1(String add_time1) {
                this.add_time1 = add_time1;
            }

            public String getAdd_time2() {
                return add_time2;
            }

            public void setAdd_time2(String add_time2) {
                this.add_time2 = add_time2;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public void setAuthor_name(String author_name) {
                this.author_name = author_name;
            }

            public String getAuthor_avatar() {
                return author_avatar;
            }

            public void setAuthor_avatar(String author_avatar) {
                this.author_avatar = author_avatar;
            }
        }
    }
}
