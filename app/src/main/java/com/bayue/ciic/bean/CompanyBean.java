package com.bayue.ciic.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class CompanyBean {
    /**
     * data : [{"id":"1","name":"这个个企业1","short_name":"企业1"},{"id":"3","name":"这个个企业3","short_name":"企业3"}]
     * code : 200
     */

    private String  msg;
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
         * id : 1
         * name : 这个个企业1
         * short_name : 企业1
         */

        private String id;
        private String name;
        private String short_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }
    }
}
