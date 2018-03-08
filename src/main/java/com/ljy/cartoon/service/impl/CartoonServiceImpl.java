package com.ljy.cartoon.service.impl;

import com.ljy.cartoon.domain.Cartooninfo;
import com.ljy.cartoon.domain.Cartoonseries;
import com.ljy.cartoon.domain.Cartoontype;
import com.ljy.cartoon.service.CartoonService;
import com.ljy.cartoon.util.CommonUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("cartoonService")
public class CartoonServiceImpl implements CartoonService {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Cartooninfo> getCartoonList(Map<String,Object> params) {
		String sql = "select * from cartooninfo where 1=1";
		if(!CommonUtil.isBlank(params)){
			if(!CommonUtil.isBlank(params.get("cartoonname"))){
				sql += " and cartoonname like '%" + params.get("cartoonname") + "%'";
			}
			if(!CommonUtil.isBlank(params.get("cartoontypedesc"))){
				sql += " and cartoontypedesc like '%" + params.get("cartoontypedesc") + "%'";
			}
		}
		List<Cartooninfo> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Cartooninfo>(Cartooninfo.class));
		return list;
	}

	@Override
	public List<Cartoonseries> getCartoonseriesList(Map<String, Object> params) {
		String sql = "select * from cartoonseries where 1=1";
		if(!CommonUtil.isBlank(params)){
			if(!CommonUtil.isBlank(params.get("serisid"))){
				sql += " and id='" + params.get("serisid") + "'";
			}
			if(!CommonUtil.isBlank(params.get("cartoonid"))){
				sql += " and cartoonid='" + params.get("cartoonid") + "'";
			}
			if(!CommonUtil.isBlank(params.get("orderby"))){
				sql += params.get("orderby");
			}
		}
		List<Cartoonseries> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Cartoonseries>(Cartoonseries.class));
		return list;
	}

	@Override
	public List<Cartoontype> getCartoontypeList(Map<String, Object> params) {
		String sql = "select * from cartoontype where 1=1";
		if(!CommonUtil.isBlank(params)){
			if(!CommonUtil.isBlank(params.get("cartoontype"))){
				sql += " and cartoontype like '%" + params.get("cartoontype") + "%'";
			}
		}
		List<Cartoontype> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Cartoontype>(Cartoontype.class));
		return list;
	}

	@Override
	public int deleteCartoon(Map<String, Object> params) {

		String ids = params.get("ids") + "";
		String[] id = ids.split(",");

		String sql = "delete from cartooninfo where id=?";
		int ii = 0;
		for(int i=0;i<id.length;i++){
			ii += jdbcTemplate.update(sql,id[i]);
		}
		return ii;

	}

	@Override
	public int deletecartoontype(String cartoonid) {
		String sql = "delete from cartoontotype where cartoonid=?";
		int ii = jdbcTemplate.update(sql,cartoonid);
		return ii;
	}

	@Override
	public int deletesomething(String ids, String tablename) {
		String[] id = ids.split(",");

		String sql = "delete from " + tablename + " where id=?";
		int ii = 0;
		for(int i=0;i<id.length;i++){
			ii += jdbcTemplate.update(sql,id[i]);
		}
		return ii;

	}

	@Override
	public int deleteimg(String tablename, String columnname, String id) {

		String sql = "update " + tablename + " set columnname='' where id='" + id + "'";
		int i = jdbcTemplate.update(sql);
		return i;
	}

	@Override
	public List<Cartoontype> getCartoontype4cartoon(String cartoonid) {
		String sql = "select t2.* from cartoontotype t1,cartoontype t2";
		sql += " where t2.id=t1.typeid and t1.cartoonid=?";
		List<Cartoontype> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Cartoontype>(Cartoontype.class),cartoonid);

		return list;
	}

}
