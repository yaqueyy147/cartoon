package com.ljy.cartoon.util;

import com.ljy.cartoon.domain.Cartoonuser;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/3/7 0007.
 */
public class Userutils {

    public static Cartoonuser getcookieuser(HttpServletRequest request) throws Exception{
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        Cartoonuser cartoonuser = (Cartoonuser)JSONObject.toBean(jsonUser,Cartoonuser.class);
        return cartoonuser;
    }

    public static String getuserid(HttpServletRequest request) throws Exception{
        return getcookieuser(request).getId();
    }

    public static String getusername(HttpServletRequest request) throws Exception{
        return getcookieuser(request).getUsername();
    }

    public static String getloginname(HttpServletRequest request) throws Exception{
        return getcookieuser(request).getLoginname();
    }
}
