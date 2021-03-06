package com.ljy.cartoon.service.fronts;

import com.ljy.cartoon.domain.Cartoonuser;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
public interface UserService {

    //创建一个用户
    public void createUser(Cartoonuser cartoonuser);
    //修改用户信息
    public void saveUser(Cartoonuser cartoonuser);

    //根据传入的用户信息查询用户主要用户名和密码，返回list<map>
    public List<Map<String,Object>> signIn(Cartoonuser cartoonuser);

    //根据传入的用户信息查询用户主要用户名和密码，返回list<Cartoonuser>
    public List<Cartoonuser> getUserInfo1(Cartoonuser Cartoonuser);

    //修改密码
    public int modifyPassword(Map<String, Object> params);

    //修改头像
    public int modifyPhoto(String userId, String photoPath);

    //根据用户ID查询用户
    public Cartoonuser getUserInfoFromId(String userId);

    //设置用户是否可登陆后台
    public int setUserConsole(int userId,int state);

    //设置用户是否可登陆前台
    public int setUserFront(int userId,int state);

}
