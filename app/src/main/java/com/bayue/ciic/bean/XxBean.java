package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class XxBean {


    /**
     * data : [{"id":"2","user_id":"8","message_content":"恭喜成功获得","create_time":"123123123","type":"1"},{"id":"3","user_id":"8","message_content":"恭喜成功获得","create_time":"123123123","type":"1"},{"id":"1","user_id":"8","message_content":"恭喜成功获得","create_time":"123123123","type":"1"}]
     * code : 200
     */

    private  String msg;
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
         * id : 2
         * user_id : 8
         * message_content : 恭喜成功获得
         * create_time : 123123123
         * type : 1
         */
        private  String title;
        private boolean selected;
        private String id;
        private String user_id;
        private String message_content;
        private String create_time;
        private String type;
        private String status;

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

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

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
    }
}
