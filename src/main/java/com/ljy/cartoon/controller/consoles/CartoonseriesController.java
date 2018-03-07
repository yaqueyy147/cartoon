package com.ljy.cartoon.controller.consoles;

import com.ljy.cartoon.dao.CartoonseriesDao;
import com.ljy.cartoon.domain.Cartoonseries;
import com.ljy.cartoon.service.CartoonService;
import com.ljy.cartoon.util.CommonUtil;
import com.ljy.cartoon.util.Userutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by *** on 2018/3/7 0007.
 */
@Controller
@RequestMapping(value = "/consoles")
public class CartoonseriesController {

    @Autowired
    private CartoonseriesDao cartoonseriesDao;

    @Autowired
    private CartoonService cartoonService;

    @RequestMapping(value = "/series")
    public ModelAndView type(Model model){
        return new ModelAndView("/consoles/series");
    }

    @RequestMapping(value = "/serieslist")
    @ResponseBody
    public Object serieslist(@RequestParam Map<String,Object> params){
        List<Cartoonseries> serieslist = cartoonseriesDao.find(params);
        return serieslist;
    }

    @RequestMapping(value = "saveseries")
    @ResponseBody
    public Object savetype(Cartoonseries cartoonseries, HttpServletRequest request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if(!CommonUtil.isBlank(cartoonseries.getId()) && !"0".equals(cartoonseries.getId())){
                Cartoonseries old = cartoonseriesDao.get(cartoonseries.getId());
                cartoonseries.setCreateid(old.getCreateid());
                cartoonseries.setCreatename(old.getCreatename());
                cartoonseries.setCreatedate(old.getCreatedate());
                cartoonseries.setDeleteflag(old.getDeleteflag());
                cartoonseries.setRemark(old.getRemark());
            }else{
                cartoonseries.setId(CommonUtil.uuid());
                cartoonseries.setCreateid(Userutils.getuserid(request,Userutils.FRONG_COOKIE_NAME));
                cartoonseries.setCreatename(Userutils.getusername(request,Userutils.FRONG_COOKIE_NAME));
                cartoonseries.setCreatedate(new Date());
            }
            cartoonseriesDao.save(cartoonseries);
            result.put("code",1);
        }catch (Exception e){
            e.printStackTrace();
            result.put("code",0);
        }
        return result;
    }

    @RequestMapping(value = "deleteseries")
    @ResponseBody
    public Object deletetype(HttpServletRequest request,String ids){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            int i = cartoonService.deletesomething(ids,"cartoonseries");
            result.put("code",1);
        }catch (Exception e){
            e.printStackTrace();
            result.put("code",0);
        }
        return result;
    }

}
