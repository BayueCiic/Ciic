package com.bayue.ciic.bean;

/**
 * Created by Administrator on 2017/7/5.
 */

public class GerenBean {


    /**
     * data : {"id":"8","username":"1","useravatar":"Uploads/user/2017-07-04/595b96680fc43.jpg","enterprise_id":"1","EnterpriseName":"这个个企业1","EnterpriseShortName":"企业1"}
     * code : 200
     */
    private String msg;
    private DataBean data;
    private int code;

    public String getMsg() {
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
         * id : 8
         * username : 1
         * useravatar : Uploads/user/2017-07-04/595b96680fc43.jpg
         * enterprise_id : 1
         * EnterpriseName : 这个个企业1
         * EnterpriseShortName : 企业1
         */

        private String id;
        private String username;
        private String useravatar;
        private String enterprise_id;
        private String EnterpriseName;
        private String EnterpriseShortName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUseravatar() {
            return useravatar;
        }

        public void setUseravatar(String useravatar) {
            this.useravatar = useravatar;
        }

        public String getEnterprise_id() {
            return enterprise_id;
        }

        public void setEnterprise_id(String enterprise_id) {
            this.enterprise_id = enterprise_id;
        }

        public String getEnterpriseName() {
            return EnterpriseName;
        }

        public void setEnterpriseName(String EnterpriseName) {
            this.EnterpriseName = EnterpriseName;
        }

        public String getEnterpriseShortName() {
            return EnterpriseShortName;
        }

        public void setEnterpriseShortName(String EnterpriseShortName) {
            this.EnterpriseShortName = EnterpriseShortName;
        }
    }
}
