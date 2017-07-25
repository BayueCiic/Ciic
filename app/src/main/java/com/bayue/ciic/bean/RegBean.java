package com.bayue.ciic.bean;

/**
 * Created by Administrator on 2017/6/29.
 */

public class RegBean {

    /**
     * data : 注册成功
     * code : 200
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6bnVsbCwicGhvbmUiOiIxNTgyNzIyMTgwOCIsInBpZCI6bnVsbCwidHlwZSI6bnVsbH0.SvtNIMseS5iUuPMeYtK0LDg9dwrJyKDseKYkDifr4Fo
     */
    private String is_admin;
    private String msg;
    private String data;
    private int code;
    private String token;
    private String enterprise_id;

    public String getEnterprise_id() {
        return enterprise_id;
    }

    public void setEnterprise_id(String enterprise_id) {
        this.enterprise_id = enterprise_id;
    }

    public String getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
