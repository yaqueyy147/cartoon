package com.ljy.cartoon.controller.consoles;

import com.ljy.cartoon.dao.CartoontypeDao;
import com.ljy.cartoon.domain.Cartoontype;
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
public class CartoontypeController {

    @Autowired
    private CartoontypeDao cartoontypeDao;

    @Autowired
    private CartoonService cartoonService;

    @RequestMapping(value = "/type")
    public ModelAndView type(Model model){
        return new ModelAndView("/consoles/type");
    }

    @RequestMapping(value = "/typelist")
    @ResponseBody
    public Object typelist(@RequestParam Map<String,Object> params){
        List<Cartoontype> typelist = cartoonService.getCartoontypeList(params);
        return typelist;
    }

    @RequestMapping(value = "savetype")
    @ResponseBody
    public Object savetype(Cartoontype cartoontype, HttpServletRequest request){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if(!CommonUtil.isBlank(cartoontype.getId()) && !"0".equals(cartoontype.getId())){
                Cartoontype old = cartoontypeDao.get(cartoontype.getId());
                cartoontype.setCreateid(old.getCreateid());
                cartoontype.setCreatename(old.getCreatename());
                cartoontype.setCreatedate(old.getCreatedate());
                cartoontype.setDeleteflag(old.getDeleteflag());
                cartoontype.setRemark(old.getRemark());
            }else{
                cartoontype.setId(CommonUtil.uuid());
                cartoontype.setCreateid(Userutils.getuserid(request,Userutils.CONSOLE_COOKIE_NAME));
                cartoontype.setCreatename(Userutils.getusername(request,Userutils.CONSOLE_COOKIE_NAME));
                cartoontype.setCreatedate(new Date());
            }
            cartoontypeDao.save(cartoontype);
            result.put("code",1);
            result.put("msg","保存成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put("code",0);
            result.put("msg","保存失败");
        }
        return result;
    }

    @RequestMapping(value = "deletetype")
    @ResponseBody
    public Object deletetype(HttpServletRequest request,String ids){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            int i = cartoonService.deletesomething(ids,"cartoontype");
            result.put("code",1);
            result.put("msg","删除成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put("code",0);
            result.put("msg","删除失败");
        }
        return result;
    }

}
