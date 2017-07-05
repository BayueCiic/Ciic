package com.bayue.ciic.bean;

/**
 * Created by Administrator on 2017/7/5.
 */

public class DetailsBean {

    /**
     * data : {"id":"1","user_id":"8","message_content":"恭喜成功获得","title":"这个消息","create_time":"123123123","type":"1","status":"1"}
     * code : 200
     */
    private String msg;
    private DataBean data;
    private int code;

    public String getMsg(){
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

    public static class DataBean {
        /**
         * id : 1
         * user_id : 8
         * message_content : 恭喜成功获得
         * title : 这个消息
         * create_time : 123123123
         * type : 1
         * status : 1
         */

        private String id;
        private String user_id;
        private String message_content;
        private String title;
        private String create_time;
        private String type;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getMessage_content() {
            return message_content;
        }

        public void setMessage_content(String message_content) {
            this.message_content = message_content;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
