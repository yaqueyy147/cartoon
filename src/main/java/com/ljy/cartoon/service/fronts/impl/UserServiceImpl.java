package com.ljy.cartoon.service.fronts.impl;

import com.ljy.cartoon.dao.CartoonuserDao;
import com.ljy.cartoon.domain.Cartoonuser;
import com.ljy.cartoon.service.fronts.UserService;
import com.ljy.cartoon.util.CommonUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/12/18.
 */
@Service("userService")
public class UserServiceImpl implements UserService {


    @Resource
    private CartoonuserDao cartoonuserDao;

    public void setCartoonuserDao(CartoonuserDao cartoonuserDao) {
        this.cartoonuserDao = cartoonuserDao;
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 用户注册
     * @param cartoonuser
     * @return
     */
    @Override
    public void createUser(Cartoonuser cartoonuser) {
        cartoonuser.setPassword(CommonUtil.string2MD5(cartoonuser.getPassword()));
        int id = 0;
        try {
            cartoonuserDao.save(cartoonuser);
        }catch (Exception da){
            da.printStackTrace();
        }
    }

    @Override
    public void saveUser(Cartoonuser cartoonuser) {
        int i = 0;
        try {
            cartoonuserDao.save(cartoonuser);
            i ++;
        }catch (Exception e){

        }
    }

    /**
     * 用户登录
     * @param cartoonuser
     * @return
     */
    @Override
    public List<Map<String, Object>> signIn(Cartoonuser cartoonuser) {
        String sql = "select * from cartoonuser where loginname=? and password=?";
        //将密码加密
        String password = CommonUtil.string2MD5(cartoonuser.getPassword());
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,cartoonuser.getUsername(),password);
        return list;
    }

    /**
     * 根据传入的用户信息查询用户
     * @param cartoonuser
     * @return
     */
    @Override
    public List<Cartoonuser> getUserInfo1(Cartoonuser cartoonuser) {
        String sql = "select * from cartoonuser where state=1";// and password=?

        if(!CommonUtil.isBlank(cartoonuser.getIsfront()) && cartoonuser.getIsfront() == 1){
            sql += " and isfront='1'" ;
        }
        if(!CommonUtil.isBlank(cartoonuser.getIsconsole()) && cartoonuser.getIsconsole() == 1){
            sql += " and isconsole='1'" ;
        }
        if(!CommonUtil.isBlank(cartoonuser.getLoginname())){
            sql += " and loginname='"+ cartoonuser.getLoginname() + "'" ;
        }
        if(!CommonUtil.isBlank(cartoonuser.getUsername())){
            sql += " and username='"+ cartoonuser.getPassword() + "'" ;
        }
        if(!CommonUtil.isBlank(cartoonuser.getPassword())){
            sql += " and password='"+ cartoonuser.getPassword() + "'" ;
        }

        //查询
        List<Cartoonuser> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Cartoonuser>(Cartoonuser.class));

        return list;
    }

    @Override
    public int modifyPassword(Map<String,Object> params) {
        String userId = params.get("userId") + "";
        String newPassword = CommonUtil.string2MD5(params.get("newPassword") + "");
        String sql = "update cartoonuser set password=? where id=?";

        int i = jdbcTemplate.update(sql,newPassword,userId);

        return i;
    }

    @Override
    public int modifyPhoto(String userId, String photoPath, String userType) {

        String sql = "update cartoonuser set userphoto=? where id=?";

        int i = jdbcTemplate.update(sql,photoPath,userId);
        return i;
    }

    @Override
    public Cartoonuser getUserInfoFromId(String userId) {
        Cartoonuser tUser1 = cartoonuserDao.get(userId);
        return tUser1;
    }

    @Override
    public int setUserConsole(int userId, int state) {

        String sql = "update cartoonuser set isconsole=? where id=?";

        int i = jdbcTemplate.update(sql,state,userId);

        return i;
    }

    @Override
    public int setUserFront(int userId, int state) {
        String sql = "update cartoonuser set isfront=? where id=?";

        int i = jdbcTemplate.update(sql,state,userId);

        return i;
    }

}
