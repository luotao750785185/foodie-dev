package com.luotao.mapper;

import com.luotao.pojo.vo.CategoryVO;
import com.luotao.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {//自定义类，有SQL，不像继承myMapper的无SQL
    public List<CategoryVO> getSubCatList(Integer rootCatId);

    public List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String,Object> map);
}