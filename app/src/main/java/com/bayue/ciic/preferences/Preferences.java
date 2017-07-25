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
}
