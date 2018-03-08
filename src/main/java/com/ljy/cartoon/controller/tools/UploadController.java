package com.ljy.cartoon.controller.tools;

import com.alibaba.fastjson.JSONObject;
import com.ljy.cartoon.service.CartoonService;
import com.ljy.cartoon.util.CommonUtil;
import cq.hlideal.jetty.main.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suyx on 2017/1/7.
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private CartoonService cartoonService;

    /**
     * 上传图片
     * @param uploadFile
     * @param targetFile
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile uploadFile, String targetFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            String path = request.getSession().getServletContext().getRealPath("");//项目根目录
            String requestUrl = request.getRequestURL().toString();//请求地址
            String projectName = request.getContextPath();//项目名称
            path = path.substring(0,path.indexOf("\\webapp") + 7) + "\\static" + targetFile;//上传图片的地址
            String filePath = CommonUtil.uploadFile(path, uploadFile);//上传图片
            filePath = filePath.replace("\\","/");//将路径中的\替换为/
            filePath = filePath.substring(filePath.indexOf("/static"));


            map.put("filePath",filePath);
            map.put("code",1);
        }catch (Exception e){
            e.printStackTrace();
            map.put("filePath","");
            map.put("code",0);
        }
        String resultStr = JSONObject.toJSONString(map);
        return resultStr;
    }

    /**
     * 删除图片
     * @param request
     * @param response
     */
    @RequestMapping("deleteimg")
    @ResponseBody
    public Object deletecenoimg(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,Object> params){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", 0);
        try{
            String path = request.getSession().getServletContext().getRealPath("");
            String imgpath = params.get("imgpath") + "";
            String tablename = params.get("tablename") + "";
            String columnname = params.get("columnname") + "";
            String id = params.get("id") + "";
            if(!CommonUtil.isBlank(id)){
                int i = cartoonService.deleteimg(tablename,columnname,id);
            }

            boolean bb = FileUtil.delete(path + imgpath);
            if(!bb){
                LOGGER.info("文件(" + imgpath + ")删除失败!");
            }
            map.put("code", 1);
            map.put("message","图片删除成功!");
        }catch (Exception e){
            map.put("message","修改失败，请稍后重试");
        }
        return map;
    }

}
