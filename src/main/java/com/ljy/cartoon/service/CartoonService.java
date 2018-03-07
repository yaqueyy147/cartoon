package com.ljy.cartoon.service;

import com.ljy.cartoon.domain.Cartooninfo;

import java.util.List;
import java.util.Map;

public interface CartoonService {
	
	public List<Cartooninfo> getCartoonList(Map<String,Object> params);

	public int deleteCartoon(Map<String,Object> params);

	public int deletecartoontype(String ids);

	public int deletesomething(String ids,String tablename);

}
