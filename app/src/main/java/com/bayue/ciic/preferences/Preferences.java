package com.bayue.ciic.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.bayue.ciic.utils.Utils;

/**
 * Created by Administrator on 2017/6/5.
 */

public class Preferences {

    public  static final String TOKEN="token";
    public  static final String ADMIN="admin";

    private static final String ID="id";
    private static final String ACCID="accid";

    public  static void saveString (Context context,String key,String value){
        SharedPreferences sp=context.getSharedPreferences("",context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String getString ( Context context,String key){
        SharedPreferences sp=context.getSharedPreferences("",context.MODE_PRIVATE);
        return sp.getString(key,"-1");
    }
    public static void saveUserName(String user){
        SharedPreferences sp= Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("username", user);
        editor.commit();
    }
    public static String getUserName(){
        SharedPreferences sp=Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        return sp.getString("username","");
    }
    public static void savePassword(String password){
        SharedPreferences sp= Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("password", password);
        editor.commit();
    }
    public static String  getPassword(){

        SharedPreferences sp=Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        return sp.getString("password","");
    }

    public static void saveAdmin(String admin){
        SharedPreferences sp= Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString(ADMIN, admin);
        editor.commit();
    }
    public static String  getAdmin(){

        SharedPreferences sp=Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        return sp.getString(ADMIN,"");
    }
    public static void saveEnterprise_id(String id){
        SharedPreferences sp= Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString(ID, id);
        editor.commit();
    }
    public static String  getEnterprise_id(){

        SharedPreferences sp=Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        return sp.getString(ID,"");
    }
    public static void saveIm_token(String id){
        SharedPreferences sp= Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("Im_token", id);
        editor.commit();
    }
    public static String  getIm_token(){

        SharedPreferences sp=Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        return sp.getString("Im_token","");
    }
    public static void saveAccid(String id){
        SharedPreferences sp= Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString(ACCID, id);
        editor.commit();
    }
    public static String  getAccid(){

        SharedPreferences sp=Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        return sp.getString(ACCID,"");
    }
    public static void saveTitle(String id){
        SharedPreferences sp= Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("title", id);
        editor.commit();
    }
    public static String  getTitle(){

        SharedPreferences sp=Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        return sp.getString("title","");
    }
    public static void saveImg(String id){
        SharedPreferences sp= Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("img", id);
        editor.commit();
    }
    public static String  getImg(){

        SharedPreferences sp=Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        return sp.getString("img","");
    }
    public static void saveRoomid(String id){
        SharedPreferences sp= Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("roomid", id);
        editor.commit();
    }
    public static String  getRoomid(){

        SharedPreferences sp=Utils.getContext().getSharedPreferences("",Utils.getContext().MODE_PRIVATE);
        return sp.getString("roomid","");
    }
}
