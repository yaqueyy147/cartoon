package com.ljy.cartoon.service;

import com.ljy.cartoon.domain.Cartooninfo;
import com.ljy.cartoon.domain.Cartoonseries;
import com.ljy.cartoon.domain.Cartoontype;

import java.util.List;
import java.util.Map;

public interface CartoonService {
	
	public List<Cartooninfo> getCartoonList(Map<String,Object> params);

	public List<Cartoonseries> getCartoonseriesList(Map<String,Object> params);
	public List<Cartoontype> getCartoontypeList(Map<String,Object> params);

	public int deleteCartoon(Map<String,Object> params);

	//根据cartoonid删除type
	public int deletecartoontype(String cartoonid);

	public int deletesomething(String ids,String tablename);

	public int deleteimg(String tablename, String columnname, String id);

	public List<Cartoontype> getCartoontype4cartoon(String cartoonid);

}
