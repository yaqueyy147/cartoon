package com.ljy.cartoon.service.consoles;

import com.ljy.cartoon.domain.Cartoonuser;
import com.ljy.cartoon.domain.Consoleresource;
import com.ljy.cartoon.domain.Userresource;

import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/1/11.
 */
public interface ConsoleService {


    public List<Cartoonuser> getUserList(Map<String,Object> params);

    public void saveUser(Cartoonuser cartoonuser);

    public int modifyPassword(Map<String,Object> params);

    public int deleteUser(Map<String,Object> params);

    public void saveResource(Consoleresource consoleresourceesource);

    public int deleteResource(Map<String,Object> params);

    public List<Consoleresource> getResourceList(Map<String,Object> params);

    public List<Userresource> getUserResource(Map<String,Object> params);

    public int saveAuth(Map<String,Object> params);

    public List<Map<String,Object>> getUserMenu(Map<String,Object> params);

    public List<Map<String,Object>> getUserMenu4admin(Map<String,Object> params);
}
