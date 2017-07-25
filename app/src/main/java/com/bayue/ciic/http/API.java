package com.bayue.ciic.http;

/**
 * Created by Administrator on 2017/6/26.
 */

public class API {
    private static boolean isLocal(){
        return 1==2;
    }
    private static final String Base_Inner="http://192.168.1.159/zhongz";
    private static final String Domain_Net="http://zz.bayuenet.com";
    public static final String BaseUrl= isLocal()? Base_Inner:Domain_Net;

    public static class Login{
        public static final String REG="/Home/Login/reg";
        public static final String LOGIN="/Home/Login/login";
        public static final String VALI="/Home/Login/send_message";
        public static final String FORGET="/Home/Login/forgot_password";
        public static final String MAIL="/Home/Login/send_mail";
        public static final String EMAIL_REG="/Home/Login/EnterpriseReg";
        public static final String EMAIL_lOGIN= "/Home/Login/EnterpriseLogin";

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

        public static final String WONDERFUL="/Home/Photo/ManageAlbumList";
        public static final String NEWS="/Home/Photo/ManageAlbumList";

        public static final String SHIPINP="/Home/Video/ManageVideoList";
        public static final String SHIPINC="/Home/Video/ManageVideoAudit";

        public static final String ZHIBO="/Home/Direct/ManageDirectList";
    }

    public  static class patfrom{
        public static final String SHOUYE="";
        public static final String SHOUYE_ZHIBO= "/Home/Index/IndexDirect";
        public static final String SHOUYE_SHIPING= "/Home/Index/IndexVideo";
        public static final String SHOUYE_PHOTO= "/Home/Index/IndexPhoto";
        public static final String SHOUYE_HUODONG= "/Home/Index/IndexActivity";
        public static final String SHOUYE_XINWEN= "/Home/Index/IndexNews";



        public static final String ZHIBO="/Home/Direct/ListDirect";
        public static final String SHIPIN="/Home/Video/ListVideo";
        public static final String HUODOANG="/Home/Activity/ListActivity";
        public static final String XINWEN="/Home/News/ListNews";
        public static final String JINGCAI="/Home/Photo/ListAlbum";
    }

    //类型
    public static final String TAG="/Home/Index/LabelList";





}
