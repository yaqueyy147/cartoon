package com.ljy.cartoon.controller.consoles;

import com.ljy.cartoon.domain.Cartoonuser;
import com.ljy.cartoon.domain.Consoleresource;
import com.ljy.cartoon.domain.Userresource;
import com.ljy.cartoon.service.consoles.ConsoleService;
import com.ljy.cartoon.service.fronts.UserService;
import com.ljy.cartoon.util.CommonUtil;
import com.ljy.cartoon.util.CookieUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/1/11.
 */
@Controller
@RequestMapping(value = "/consoles")
public class ConsoleController {

    private static final Logger LOGGER = Logger.getLogger(ConsoleController.class);

    @Autowired
    private ConsoleService consoleService;

    @Autowired
    private UserService userService;

    /**
     * 用户页面
     * @return
     */
    @RequestMapping(value = "user")
    public ModelAndView user(){
        return new ModelAndView("/consoles/userSetting");
    }

    /**
     * 用户列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "userList")
    @ResponseBody
    public Map<String,Object> getUserList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        List<Cartoonuser> list = consoleService.getUserList(params);
        result.put("dataList",list);
        return result;
    }

    /**
     * 保存用户
     * @param cartoonuser
     * @return
     */
    @RequestMapping(value = "saveUserBase")
    @ResponseBody
    public Map<String,Object> saveUserBase(HttpServletRequest request,HttpServletResponse response, Cartoonuser cartoonuser) throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        int i = 0;

        try {
            JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
            String userName = consolesUser.get("userName") + "";

            Map<String,Object> params = new HashMap<String,Object>();

            if(CommonUtil.isBlank(cartoonuser.getId()) || "0".equals(cartoonuser.getId())){//新建用户，需要设置加密密码
                //检查用户名是否已经存在了
                params.put("loginname",cartoonuser.getLoginname());
                List<Cartoonuser> list = consoleService.getUserList(params);
                if(list != null && list.size() > 0){
                    result.put("msg","该用户已存在!");
                    result.put("cartoonuser",cartoonuser);
                    result.put("code",99);
                    return result;
                }
                cartoonuser.setUserfrom(2);
                cartoonuser.setPassword(CommonUtil.string2MD5(cartoonuser.getPassword()));
                cartoonuser.setCreateman(userName);
                cartoonuser.setCreatedate(CommonUtil.getDateLong());
                cartoonuser.setId(CommonUtil.uuid());

            }else{//修改用户，不修改密码
                params = new HashMap<String,Object>();
                params.put("id",cartoonuser.getId());
                List<Cartoonuser> list = consoleService.getUserList(params);
                cartoonuser.setCreateman(list.get(0).getCreateman());
                cartoonuser.setCreatedate(list.get(0).getCreatedate());
                cartoonuser.setUserfrom(CommonUtil.parseInt(list.get(0).getUserfrom()));
                cartoonuser.setPassword(consolesUser.get("password") + "");
            }
            consoleService.saveUser(cartoonuser);

            result.put("msg","保存成功!");
            result.put("cartoonuser",cartoonuser);
            result.put("code",1);
        }catch (Exception e){
            result.put("msg","系统错误，请联系管理员!");
            result.put("cartoonuser",cartoonuser);
            result.put("code",0);
        }
        return result;
    }

    /**
     * 修改密码
     * @param params
     * @return
     */
    @RequestMapping(value = "modifyPassword")
    @ResponseBody
    public Map<String,Object> modifyPassword(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,Object> condition = new HashMap<String,Object>();

        condition.put("id",params.get("userId"));

        List<Cartoonuser> list = consoleService.getUserList(condition);

        String oldPassword = CommonUtil.string2MD5(params.get("oldPassword") + "");

        //非管理员操作时，要验证原密码
        if(CommonUtil.isBlank(params.get("isAdmin"))){
            if(!CommonUtil.isBlank(oldPassword)){
                if(!oldPassword.equals(list.get(0).getPassword())){
                    result.put("msg","原密码输入有误!");
                    result.put("code",-2);
                    return result;
                }
            }
        }

        int i = consoleService.modifyPassword(params);
        result.put("msg","修改成功!");
        result.put("code",i);
        return result;
    }

    /**
     * 删除用户
     * @param params
     * @return
     */
    @RequestMapping(value = "deleteUser")
    @ResponseBody
    public Map<String,Object> deleteUser(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<String,Object>();
        int i = consoleService.deleteUser(params);
        result.put("code",i);
        result.put("msg","操作成功!");
        return result;
    }
    
    /**
     * 用户功能权限树
     * @param params
     * @return
     */
    @RequestMapping(value = "userResourceList")
    @ResponseBody
    public Map<String,Object> userResourceList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();
        List<Consoleresource> list = consoleService.getResourceList(params);

        Map<String,Object> params1 = new HashMap<>();
        params1.put("userid",params.get("userId"));
        List<Userresource> list1 = consoleService.getUserResource(params);

        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();

        for (Consoleresource tt : list) {
            Map<String,Object> temp = new HashMap<String,Object>();
            temp = CommonUtil.bean2Map(tt);
            temp.put("pId",tt.getParentsourceid());
            temp.put("pid",tt.getParentsourceid());
            temp.put("name",tt.getSourcename());
            temp.put("text",tt.getSourcename());
            temp.put("_parentId",tt.getParentsourceid());
            temp.put("resourceState",tt.getState());
            temp.put("state","open");
            temp.put("open",true);
            for(Userresource tt1 : list1){
                if(tt1.getResourceid() == tt.getId()){
                    temp.put("checked",true);
                    break;
                }
            }
            resultList.add(temp);
        }

        result.put("resourceList",resultList);
        return result;
    }

    /**
     * 用户功能权限设置
     * @param params
     * @return
     */
    @RequestMapping(value = "saveAuth")
    @ResponseBody
    public Map<String,Object> saveAuth(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();

        int i = consoleService.saveAuth(params);

        result.put("code",i);
        result.put("msg","授权完成");
        return result;
    }


    @RequestMapping(value = "resource")
    public ModelAndView resource(Model model){
        return new ModelAndView("/consoles/resource");
    }

    /**
     * 功能列表
     * @param params
     * @return
     */
    @RequestMapping(value = "resourceList")
    @ResponseBody
    public Map<String,Object> resourceList(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();
        List<Consoleresource> list = consoleService.getResourceList(params);

        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();

        for (Consoleresource tt : list) {
            Map<String,Object> temp = new HashMap<String,Object>();
            temp = CommonUtil.bean2Map(tt);
            temp.put("pId",tt.getParentsourceid());
            temp.put("pid",tt.getParentsourceid());
            temp.put("name",tt.getSourcename());
            temp.put("text",tt.getSourcename());
            temp.put("_parentId",tt.getParentsourceid());
            temp.put("resourceState",tt.getState());
            temp.put("state","open");
            temp.put("open",true);
            resultList.add(temp);
        }

        result.put("resourceList",resultList);
        return result;
    }

    /**
     * 保存功能
     * @param consoleresource
     * @return
     */
    @RequestMapping(value = "saveResource")
    @ResponseBody
    public Map<String,Object> saveResource(Consoleresource consoleresource){
        Map<String,Object> result = new HashMap<>();

        try {
            consoleService.saveResource(consoleresource);

            result.put("code",1);
            result.put("msg","操作成功");
        }catch (Exception e){
            result.put("code",0);
            result.put("msg","系统错误，请联系管理员!");
        }
        return result;
    }

    /**
     * 删除功能
     * @param params
     * @return
     */
    @RequestMapping(value = "deleteResource")
    @ResponseBody
    public Map<String,Object> deleteResource(@RequestParam Map<String,Object> params){
        Map<String,Object> result = new HashMap<>();

        int i = consoleService.deleteResource(params);

        result.put("code",i);
        result.put("msg","删除完成");
        return result;
    }

}
