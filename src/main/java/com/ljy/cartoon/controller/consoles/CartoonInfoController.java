package com.ljy.cartoon.controller.consoles;

import com.ljy.cartoon.dao.CartooninfoDao;
import com.ljy.cartoon.domain.Cartooninfo;
import com.ljy.cartoon.service.CartoonService;
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
import java.util.*;

/**
 * Created by suyx on 2017/1/11.
 */
@Controller
@RequestMapping(value = "/consoles")
public class CartoonInfoController {

    private static final Logger LOGGER = Logger.getLogger(CartoonInfoController.class);

    @Autowired
    private CartoonService cartoonService;

    @Autowired
    private CartooninfoDao cartooninfoDao;

    /**
     * 动漫列表页面
     * @return
     */
    @RequestMapping(value = "cartoon")
    public ModelAndView family(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        model.addAttribute("consolesUser",consolesUser);
        return new ModelAndView("/consoles/cartoonList");
    }

    /**
     * 动漫列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "cartoonList")
    @ResponseBody
    public Map<String,Object> getFamilyList(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> params) throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        //获取登录用户信息
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";

        List<Cartooninfo> list = list = cartoonService.getCartoonList(params);
        
        List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
//        //遍历族谱，设置族谱人数
//        for(Cartooninfo cartooninfo : list){
//            int peopleCount = 0;
//            Map<String,Object> map = new HashMap<String,Object>();
//            Map<String,Object> paramss = new HashMap<>();
//            paramss.put("familyId",tFamily.getId());
//            paramss.put("peopleType",1);
//            List<TPeople> peopleList = familyService.getPeopleList(paramss);
//            if(peopleList != null && peopleList.size() > 0)
//            {
//                peopleCount = peopleList.size();
//            }
//            map = CommonUtil.bean2Map(cartooninfo);
//            map.put("peopleCount",peopleCount);
//            list1.add(map);
//        }
        result.put("dataList",list1);
        return result;
    }

    /**
     * 保存/修改族谱
     * @param request
     * @param response
     * @param cartooninfo  传入的族谱数据
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveCartoon")
    @ResponseBody
    public Map<String,Object> saveFamily(HttpServletRequest request,HttpServletResponse response, Cartooninfo cartooninfo) throws Exception{

        //获取登录用户信息
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("loginName") + "";
        Map<String,Object> map = new HashMap<String,Object>();
        int ii = 0;
        String msg = "创建成功";
        try {
            //如果传入的族谱有id，则为修改族谱
            if(!CommonUtil.isBlank(cartooninfo.getId()) && !"0".equals(cartooninfo.getId())){
                //根据族谱id获取族谱原信息
                Cartooninfo cartooninfoOld = cartooninfoDao.get(cartooninfo.getId());

                LOGGER.info("修改动漫-->" + cartooninfoOld);
                //将原族谱不能修改的信息设置到新族谱中
                cartooninfo.setCreatedate(cartooninfoOld.getCreatedate());
                cartooninfo.setCreateid(cartooninfoOld.getCreateid());
                cartooninfo.setCreatename(cartooninfoOld.getCreatename());
                //修改族谱

                msg = "修改成功";

            }else{//如果传入的族谱id为空或者为0，则为新增族谱
                //设置创建人和创建时间
                cartooninfo.setId(CommonUtil.uuid());
                cartooninfo.setCreateid(consolesUser.get("id") + "");
                cartooninfo.setCreatedate(new Date());
                cartooninfo.setCreatename(userName);
                LOGGER.info("创建动漫-->" + cartooninfo);

            }
            cartooninfoDao.save(cartooninfo);
            map.put("code",1);
        } catch (Exception e){
            LOGGER.error("操作动漫出错-->",e);
            map.put("code",-1);
            map.put("msg","操作动漫出错！-->" + e.getMessage());
            return map;
        }
        map.put("cartooninfo",cartooninfo);

        map.put("msg",msg);
        return map;
    }

    /**
     * 删除族谱
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteCartoon")
    @ResponseBody
    public Map<String,Object> deleteFamily(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception{
        //获取登录用户信息
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("userName") + "";
        Map<String,Object> result = new HashMap<String,Object>();

        LOGGER.info("删除族谱-->" + params);
        int i = cartoonService.deleteCartoon(params);

        result.put("code",i);
        result.put("msg","操作成功!");

        return result;
    }



}
