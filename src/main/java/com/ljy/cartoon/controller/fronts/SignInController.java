package com.ljy.cartoon.controller.fronts;

import com.ljy.cartoon.domain.Cartoonuser;
import com.ljy.cartoon.service.fronts.UserService;
import com.ljy.cartoon.util.CommonUtil;
import com.ljy.cartoon.util.CookieUtil;
import com.ljy.cartoon.util.Userutils;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
@Controller
@RequestMapping(value = "/sign")
public class SignInController {

    private static final Logger LOGGER = Logger.getLogger(SignInController.class);

    @Autowired
    private UserService userService;


    /**
     * 前台登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = {"","/","/login"})
    public ModelAndView frontLogin(Model model, @ModelAttribute("loginCode") String loginCode){

        model.addAttribute("loginCode", loginCode);

        return new ModelAndView("/fronts/login");
    }

    /**
     * 登录
     * @param cartoonuser
     * @param ra
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/signIn")
    public RedirectView signIn(Cartoonuser cartoonuser, RedirectAttributes ra, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException{

        String contextPath = request.getContextPath();
        cartoonuser.setPassword(CommonUtil.string2MD5(cartoonuser.getPassword()));
        //个人用户
        cartoonuser.setIsfront(1);
        List<Cartoonuser> listUser = userService.getUserInfo1(cartoonuser);
        Map<String,Object> mapUserInfo = new HashMap<String,Object>();
        //如果用户存在则为个人用户，则登录，跳转首页
        if(listUser != null && listUser.size() > 0){
            //将用户信息添加到cookie
            CookieUtil.addCookie(Userutils.FRONT_COOKIE_NAME, JSONObject.fromObject(listUser.get(0)).toString(),response);
            return new RedirectView(contextPath + "/fronts/index");
        }
        //否则跳回登录页面
        ra.addFlashAttribute("loginCode",-1);
        return new RedirectView(contextPath + "/sign/login");
    }

    /**
     * 前台注册页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/regedit")
    public ModelAndView regedit(Model model, String regCode){
        model.addAttribute("regCode",regCode);
        return new ModelAndView("/fronts/regedit");
    }

    /**
     * 前台注册页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/regeditPersonal")
    public ModelAndView regeditPersonal(Model model, String regCode){
        model.addAttribute("regCode",regCode);
        return new ModelAndView("/fronts/regedit_personal");
    }

    /**
     * 个人用户注册
     * @param cartoonuser
     * @return
     */
    @RequestMapping(value = "/regesterPersonal")
    public RedirectView regesterPersonal(Cartoonuser cartoonuser, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException{
        String contextPath = request.getContextPath();
        int id = 0;
        JSONObject jsonObject = new JSONObject();
        //个人用户
        //检查用户名是否已经存在了
        List<Cartoonuser> list = userService.getUserInfo1(new Cartoonuser(cartoonuser.getLoginname()));

        if(list != null && list.size() > 0){
            return new RedirectView(contextPath + "/sign/regeditPersonal?regCode=-2");
        }

        cartoonuser.setId(CommonUtil.uuid());
        cartoonuser.setIsfront(1);
        cartoonuser.setState(1);
        cartoonuser.setUserfrom(1);
        cartoonuser.setCreateman(cartoonuser.getLoginname());
        cartoonuser.setCreatedate(CommonUtil.getDateLong());
        userService.createUser(cartoonuser);

        jsonObject = JSONObject.fromObject(cartoonuser);
        jsonObject.put("userType",1);
        //注册成功，自动登录，添加cookie
        CookieUtil.addCookie("userInfo", jsonObject.toString(),response);

        return new RedirectView(contextPath + "/fronts/index");
    }

    /**
     * 退出登录
     * @param model
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public RedirectView logout(Model model, HttpServletResponse response, HttpServletRequest request){
        String contextPath = request.getContextPath();
        //销毁登录用户信息cookie
        CookieUtil.destroyCookies(response,request);
        model.addAttribute("userInfo",null);
        //返回登录页面
        return new RedirectView(contextPath + "/sign/");
    }

    /**
     * 修改用户信息
     * @param cartoonuser
     * @return
     */
    @RequestMapping(value = "/modifyPersonalInfo")
    @ResponseBody
    public Map<String,Object> modifyPersonalInfo(Cartoonuser cartoonuser){
        Map<String,Object> map = new HashMap<String,Object>();

        Cartoonuser tUser11 = userService.getUserInfoFromId(cartoonuser.getId());
        cartoonuser.setIsfront(tUser11.getIsfront());
        cartoonuser.setIsconsole(tUser11.getIsconsole());
        cartoonuser.setUserfrom(tUser11.getUserfrom());
        cartoonuser.setState(tUser11.getState());
        cartoonuser.setCreatedate(tUser11.getCreatedate());

        userService.saveUser(cartoonuser);


        map.put("code",1);
        map.put("msg","修改成功!");
        return map;
    }


    /**
     * 修改密码
     * @param request
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/modifyPassword")
    @ResponseBody
    public Map<String,Object> modifyPassword(HttpServletRequest request,@RequestParam Map<String,Object> params) throws UnsupportedEncodingException{
        Map<String,Object> map = new HashMap<String,Object>();
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");

        if(!CommonUtil.string2MD5(params.get("oldPassword") + "").equals(jsonUser.get("password"))){
            map.put("code",2);
            map.put("msg","原密码输入有误!");
            return map;
        }
        int i = userService.modifyPassword(params);

        map.put("code",i);
        map.put("msg","修改成功!");
        return map;
    }

    /**
     * 修改密码
     * @param request
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/modifyphoto")
    @ResponseBody
    public Map<String,Object> modifyphoto(HttpServletRequest request,@RequestParam Map<String,Object> params) throws UnsupportedEncodingException{
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            Cartoonuser cartoonuser = Userutils.getcookieuser(request,Userutils.FRONT_COOKIE_NAME);

            String photoPath = params.get("photoPath") + "";

            int i = userService.modifyPhoto(cartoonuser.getId(),photoPath);

            map.put("code",i);
            map.put("msg","修改成功!");
        }catch (Exception e){
            map.put("code",0);
            map.put("msg","修改失败!");
        }
        return map;
    }

    /**
     * 登录失效或者用户信息验证失败跳转页面
     * @param model
     * @param type
     * @return
     */
    @RequestMapping(value = "/out")
    public ModelAndView redirectOut(Model model,int type){
//        System.out.println("进来了。。。");
        model.addAttribute("type",type);
        return new ModelAndView("/out");
    }

}
