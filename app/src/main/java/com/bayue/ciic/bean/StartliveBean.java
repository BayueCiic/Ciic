package com.bayue.ciic.bean;

/**
 * Created by Administrator on 2017/8/9.
 */

public class StartliveBean {


    /**
     * data : 设置成功！
     * code : 200
     * count : 0
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjE0IiwidXNlcm5hbWUiOiJcdTRlOWFcdTZkMzJcdTlmOTkiLCJlbWFpbCI6Im1pbjk4Nzg4QDE2My5jb20iLCJlbnRlcnByaXNlX2lkIjoiMTQiLCJpc19hZG1pbiI6IjIiLCJ0b2tlbl90eXBlIjoiMiJ9.GM6FPdk9dAJVaRUYwksK93MnHnHY2-56M7g6ZjXRupk
     */
    private  String  msg;
    private String data;
    private int code;
    private String count;
    private String token;

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
