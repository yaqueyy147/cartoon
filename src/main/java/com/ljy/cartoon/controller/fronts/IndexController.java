package com.ljy.cartoon.controller.fronts;

import com.ljy.cartoon.dao.CartooninfoDao;
import com.ljy.cartoon.dao.CartoonseriesDao;
import com.ljy.cartoon.dao.CartoonuserDao;
import com.ljy.cartoon.domain.Cartooninfo;
import com.ljy.cartoon.domain.Cartoonseries;
import com.ljy.cartoon.domain.Cartoontype;
import com.ljy.cartoon.domain.Cartoonuser;
import com.ljy.cartoon.service.CartoonService;
import com.ljy.cartoon.util.CommonUtil;
import com.ljy.cartoon.util.CookieUtil;
import com.ljy.cartoon.util.Userutils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
@Controller
@RequestMapping(value = "/fronts")
public class IndexController {
    private static final int PAGE_SIZE = 20;//初始每页条数
    private static final int PAGE_NUM = 6;//初始显示页数

    @Autowired
    private CartoonService cartoonService;

    @Autowired
    private CartooninfoDao cartooninfoDao;

    @Autowired
    private CartoonseriesDao cartoonseriesDao;

    @Autowired
    private CartoonuserDao cartoonuserDao;

    /**
     * 前台首页
     * @param model
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = {"","/","/index"})
    public ModelAndView index(Model model, HttpServletRequest request) throws UnsupportedEncodingException{

        //从cookie获取用户信息
        JSONObject jsonUser = CookieUtil.cookieValueToJsonObject(request,"userInfo");
        model.addAttribute("userInfo",jsonUser);
//        if(CommonUtil.isBlank(jsonUser)){
//            return new ModelAndView("/fronts/login");
//        }

        List<Cartooninfo> list = cartoonService.getCartoonList(null);
        model.addAttribute("cartoonlist",list);
        return new ModelAndView("/fronts/index");
    }

    /**
     * 首页查询族谱
     * @param params
     * @return
     */
    @RequestMapping(value = "/querycartoon")
    @ResponseBody
    public Map<String,Object> queryFamily(@RequestParam Map<String,Object> params){
        String searchname = params.get("searchname") + "";

        Map<String,Object> result = new HashMap<String,Object>();
        List<Cartooninfo> list = cartoonService.getCartoonList(params);

        result.put("cartoonlist",list);

        return result;
    }

    /**
     * 动漫详情页面
     * @param model
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/cartoondetail")
    public ModelAndView cartoondetail(Model model, HttpServletRequest request, String cartoonid) throws Exception{

        Cartoonuser cartoonuser = Userutils.getcookieuser(request, Userutils.FRONT_COOKIE_NAME);

        if(!CommonUtil.isBlank(cartoonuser)){
            model.addAttribute(Userutils.FRONT_COOKIE_NAME,cartoonuser);
        }

        Cartooninfo cartooninfo = cartooninfoDao.get(cartoonid);
        model.addAttribute("cartooninfo",cartooninfo);

        List<Cartoontype> typelist = cartoonService.getCartoontype4cartoon(cartoonid);
        model.addAttribute("typelist",typelist);

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("cartoonid",cartoonid);
        params.put("orderby","order by seriesnum desc");
        List<Cartoonseries> serieslist = cartoonService.getCartoonseriesList(params);
        model.addAttribute("serieslist",serieslist);
        return new ModelAndView("/fronts/cartoondetail");
    }

    /**
     * 动漫播放页面
     * @param model
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/cartoonplay")
    public ModelAndView cartoonplay(Model model, HttpServletRequest request, @RequestParam Map<String,Object> params) throws UnsupportedEncodingException{

        String cartoonid = params.get("cartoonid") + "";
        String serisid = params.get("serisid") + "";
        Cartoonseries cartoonseries = new Cartoonseries();
        if(CommonUtil.isBlank(serisid) && !CommonUtil.isBlank(cartoonid)){
            params.put("orderby","order by seriesnum asc");
            List<Cartoonseries> serieslist = cartoonService.getCartoonseriesList(params);
            if(serieslist != null && serieslist.size() > 0){
                cartoonseries = serieslist.get(0);
            }else{
                Cartooninfo cartooninfo = cartooninfoDao.get(cartoonid);
                model.addAttribute("cartooninfo",cartooninfo);
            }

        }else if(!CommonUtil.isBlank(serisid)){
            cartoonseries = cartoonseriesDao.get(serisid);
        }
        model.addAttribute("cartoonseries",cartoonseries);

        return new ModelAndView("/fronts/cartoonplay");
    }

    @RequestMapping(value = "personalInfo")
    public ModelAndView personalInfo(Model model, HttpServletRequest request){

        try {
            Cartoonuser cartoonuser = Userutils.getcookieuser(request,Userutils.FRONT_COOKIE_NAME);
            if(CommonUtil.isBlank(cartoonuser)){
                model.addAttribute("loginCode",-2);
                return new ModelAndView("/fronts/login");
            }
            cartoonuser = cartoonuserDao.get(cartoonuser.getId());
            model.addAttribute("cartoonuser",cartoonuser);
            model.addAttribute(Userutils.FRONT_COOKIE_NAME,cartoonuser);
        }catch (Exception e){

        }

        return new ModelAndView("/fronts/personalInfo");
    }
}
