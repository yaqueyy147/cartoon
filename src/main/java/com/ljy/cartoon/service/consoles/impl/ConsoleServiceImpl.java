package com.ljy.cartoon.service.consoles.impl;

import com.ljy.cartoon.dao.CartoonuserDao;
import com.ljy.cartoon.dao.ConsoleresourceDao;
import com.ljy.cartoon.dao.UserresourceDao;
import com.ljy.cartoon.domain.Cartoonuser;
import com.ljy.cartoon.domain.Consoleresource;
import com.ljy.cartoon.domain.Userresource;
import com.ljy.cartoon.service.consoles.ConsoleService;
import com.ljy.cartoon.util.CommonUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2017/1/11.
 */
@Service("consoleService")
public class ConsoleServiceImpl implements ConsoleService {

    @Resource
    private CartoonuserDao cartoonuserDao;

    public void setCartoonuserDao(CartoonuserDao cartoonuserDao) {
        this.cartoonuserDao = cartoonuserDao;
    }

    @Resource
    private ConsoleresourceDao consoleresourceDao;

    public void setConsoleresourceDao(ConsoleresourceDao consoleresourceDao) {
        this.consoleresourceDao = consoleresourceDao;
    }

    @Resource
    private UserresourceDao userresourceDao;

    public void setUserresourceDao(UserresourceDao userresourceDao) {
        this.userresourceDao = userresourceDao;
    }

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Cartoonuser> getUserList(Map<String, Object> params) {

        String sql = "select * from cartoonuser where state<>9";

        if(!CommonUtil.isBlank(params.get("id"))){
            sql += " and id='" + params.get("id") + "'";
        }
        if(!CommonUtil.isBlank(params.get("userfrom"))){
            sql += " and userfrom='" + params.get("userfrom") + "'";
        }
        if(!CommonUtil.isBlank(params.get("username"))){
            sql += " and username like '%" + params.get("username") + "%'";
        }
        if(!CommonUtil.isBlank(params.get("loginname"))){
            sql += " and loginname like '%" + params.get("loginname") + "%'";
        }
        if(!CommonUtil.isBlank(params.get("province"))){
            sql += " and province='" + params.get("province") + "'";
        }
        if(!CommonUtil.isBlank(params.get("city"))){
            sql += " and city='" + params.get("city") + "'";
        }
        if(!CommonUtil.isBlank(params.get("district"))){
            sql += " and district='" + params.get("district") + "'";
        }
        List<Cartoonuser> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Cartoonuser>(Cartoonuser.class));

        return list;
    }

    @Override
    public void saveUser(Cartoonuser cartoonuser) {
        int i = 0;
        cartoonuserDao.save(cartoonuser);
    }

    @Override
    public int modifyPassword(Map<String, Object> params) {
        String newPassword = CommonUtil.string2MD5(params.get("newPassword") + "");
        String sql = "update cartoonuser set password=? where id=?";

        int i = jdbcTemplate.update(sql,newPassword,params.get("userId"));

        return i;
    }

    @Override
    public int deleteUser(Map<String, Object> params) {

        String ids = params.get("ids") + "";
        String[] id = ids.split(",");

        String sql = "update cartoonuser set state=9 where id=?";
        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,id[i]);
        }

        return ii;
    }

    @Override
    public void saveResource(Consoleresource consoleresource) {
        int i = 0;
        //如果resource的ID大于0，则为修改
        if(CommonUtil.isBlank(consoleresource.getId()) || "0".equals(consoleresource.getId())){
            consoleresource.setId(CommonUtil.uuid());
        }

        consoleresourceDao.save(consoleresource);
    }

    @Override
    public int deleteResource(Map<String, Object> params) {
        String ids = params.get("ids") + "";
        String[] id = ids.split(",");

        String sql = "update consoleresource set state=9 where id=?";
        int ii = 0;
        for(int i=0;i<id.length;i++){
            ii += jdbcTemplate.update(sql,id[i]);
        }
        return ii;
    }

    @Override
    public List<Consoleresource> getResourceList(Map<String, Object> params) {
        String sql = "select * from consoleresource where state<>9";

        if(!CommonUtil.isBlank(params)){
            if(!CommonUtil.isBlank(params.get("id"))){
                sql += " and id='" + params.get("id") + "'";
            }
            if(!CommonUtil.isBlank(params.get("sourcename"))){
                sql += " and sourcename='" + params.get("sourcename") + "'";
            }
        }

        List<Consoleresource> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Consoleresource>(Consoleresource.class));
//        List<TRole> list = tRoleDao.find(params);
        return list;
    }

    @Override
    public List<Userresource> getUserResource(Map<String,Object> params){

        String hql = " from userresource where userid=?";
        List<Userresource> list = userresourceDao.find(hql,params.get("userid"));

        return list;
    }

    @Override
    public int saveAuth(Map<String,Object> params){
        int ii = 0;

        String userId = params.get("userId") + "";
        String sourceIds = params.get("sourceIds") + "";
        String[] sourceIdArr = sourceIds.split(",");

        //先删除当前用户的权限
        String del = "delete from userresource where userid=?";

        jdbcTemplate.update(del,userId);

        for(int i=0;i<sourceIdArr.length;i++){
            Userresource userresource = new Userresource(CommonUtil.uuid());
            userresource.setUserid(userId);
            userresource.setResourceid(sourceIdArr[i]);
            userresourceDao.save(userresource);
        }

        return ii;
    }

    @Override
    public List<Map<String,Object>> getUserMenu(Map<String,Object> params){

        String sql = "select t1.*,t2.userid from consoleresource t1,userresource t2";
        sql += " where t1.id=t2.resourceid and t1.state=1 and t2.userid='" + params.get("userid") + "'";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    @Override
    public List<Map<String, Object>> getUserMenu4admin(Map<String, Object> params) {
        String sql = "select *from consoleresource";
        sql += " where state=1";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

}

