package com.bayue.ciic.http;

/**
 * Created by Administrator on 2017/6/26.
 */

public class API {
    private static boolean isLocal(){
        return 2==2;
    }
    private static final String Base_Inner="http://192.168.1.171/zhongz";
    private static final String Domain_Net="";
    public static final String BaseUrl= isLocal()? Base_Inner:Domain_Net;

    public static class Login{

        public static final String REG="/Home/Login/reg";
        public static final String LOGIN="/Home/Login/login";
        public static final String VALI="/Home/Login/send_message";
        public static final String FORGET="/Home/Login/forgot_password";





    }


}
