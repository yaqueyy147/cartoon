package com.ljy.cartoon.controller.consoles;

import com.ljy.cartoon.dao.CartooninfoDao;
import com.ljy.cartoon.dao.CartoontotypeDao;
import com.ljy.cartoon.domain.Cartooninfo;
import com.ljy.cartoon.domain.Cartoontotype;
import com.ljy.cartoon.domain.Cartoontype;
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

    @Autowired
    private CartoontotypeDao cartoontotypeDao;

    /**
     * 动漫列表页面
     * @return
     */
    @RequestMapping(value = "cartoon")
    public ModelAndView family(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        model.addAttribute("consolesUser",consolesUser);

        List<Cartoontype> typelist = cartoonService.getCartoontypeList(null);

        model.addAttribute("typelist",typelist);
        return new ModelAndView("/consoles/cartoonList");
    }

    /**
     * 动漫列表数据
     * @param params
     * @return
     */
    @RequestMapping(value = "cartoonList")
    @ResponseBody
    public Map<String,Object> getcartoonList(HttpServletRequest request,HttpServletResponse response, @RequestParam Map<String, Object> params) throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();
        //获取登录用户信息
        List<Cartooninfo> list = list = cartoonService.getCartoonList(params);

        result.put("dataList",list);
        return result;
    }

    /**
     * 保存/修改动漫
     * @param request
     * @param response
     * @param cartooninfo  传入的动漫数据
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveCartoon")
    @ResponseBody
    public Map<String,Object> saveFamily(HttpServletRequest request,HttpServletResponse response, Cartooninfo cartooninfo, @RequestParam Map<String,Object> params) throws Exception{

        //获取登录用户信息
        JSONObject consolesUser = CookieUtil.cookieValueToJsonObject(request,"consoleUserInfo");
        String userName = consolesUser.get("loginName") + "";
        Map<String,Object> map = new HashMap<String,Object>();
        int ii = 0;
        String msg = "创建成功";
        String typeidstr = params.get("typeidstr") + "";
        String typedescstr = params.get("typedescstr") + "";
        String[] typeidarr = typeidstr.split(",");
        try {
            //如果传入有id，则为修改
            if(!CommonUtil.isBlank(cartooninfo.getId()) && !"0".equals(cartooninfo.getId())){
                //根据id获取原信息
                Cartooninfo cartooninfoOld = cartooninfoDao.get(cartooninfo.getId());

                LOGGER.info("修改动漫-->" + cartooninfoOld);
                //将原族谱不能修改的信息设置到新族谱中
                cartooninfo.setCreatedate(cartooninfoOld.getCreatedate());
                cartooninfo.setCreateid(cartooninfoOld.getCreateid());
                cartooninfo.setCreatename(cartooninfoOld.getCreatename());
                cartooninfo.setCartoontypedesc(typedescstr);
                cartooninfo.setCartoonseriesnum(cartooninfoOld.getCartoonseriesnum());
                //修改族谱
                msg = "修改成功";

            }else{//如果传入id为空或者为0，则为新增
                //设置创建人和创建时间
                cartooninfo.setId(CommonUtil.uuid());
                cartooninfo.setCreateid(consolesUser.get("id") + "");
                cartooninfo.setCreatedate(new Date());
                cartooninfo.setCreatename(userName);
                cartooninfo.setCartoontypedesc(typedescstr);
                LOGGER.info("创建动漫-->" + cartooninfo);

            }
            cartooninfoDao.save(cartooninfo);

            //设置type，先删除原有type
            cartoonService.deletecartoontype(cartooninfo.getId());
            //循环添加type
            for(int i=0;i<typeidarr.length;i++){
                Cartoontotype cartoontotype = new Cartoontotype(CommonUtil.uuid(),typeidarr[i],cartooninfo.getId());
                cartoontotypeDao.save(cartoontotype);
            }

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
     * 删除动漫
     * @param params
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteCartoon")
    @ResponseBody
    public Map<String,Object> deleteFamily(@RequestParam Map<String,Object> params, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String,Object> result = new HashMap<String,Object>();

        int i = cartoonService.deleteCartoon(params);

        result.put("code",i);
        result.put("msg","操作成功!");

        return result;
    }



}
