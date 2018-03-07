package com.ljy.cartoon.service.impl;

import com.ljy.cartoon.domain.Cartooninfo;
import com.ljy.cartoon.service.CartoonService;
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
		List<Cartooninfo> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<Cartooninfo>(Cartooninfo.class));
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
	public int deletecartoontype(String ids) {
		return 0;
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

}
