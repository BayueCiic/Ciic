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
    public static class user{
        public static final String AMEND="/Home/User/ChangePassword";
        public static final String CANYU="/Home/User/JoinActivityList";
        public static final String DEL="/Home/User/DelJoinActivity";
        public static final String VIDEO="/Home/User/CollectionVideoList";
        public static final String DEL_VIDEO="/Home/User/UncollectionVideo";
        public static final String XX="/Home/User/Message";
        public static final String DEL_XX="/Home/User/DelMessage";
        public static final String TAG_XX="/Home/User/ChangeStatus";
        public static final String COMPANY="/Home/User/SelectEnterprise";
        public static final String COMPLAINT="/Home/User/Complain";
        public static final String ALTER="/Home/User/ChangeUserinfo";
        public static final String DETAILS="/Home/User/MessageInfo";
        public static final String GEREN="/Home/User/Userinfo";
    }

}
