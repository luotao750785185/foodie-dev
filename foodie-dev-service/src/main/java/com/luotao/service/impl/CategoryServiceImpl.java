package com.luotao.service.impl;

import com.luotao.mapper.CategoryMapper;
import com.luotao.mapper.CategoryMapperCustom;
import com.luotao.pojo.Carousel;
import com.luotao.pojo.Category;
import com.luotao.pojo.vo.CategoryVO;
import com.luotao.pojo.vo.NewItemsVO;
import com.luotao.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : luo
 * @date : 2020/3/17 20:57
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryMapperCustom categoryMapperCustom;



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        //用mapper接口继承myMapper来的方法来实现查询
        Example example=new Example(Carousel.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("type",1);//此处不用枚举了，这种一般写死了
        List<Category> result=categoryMapper.selectByExample(example);
        return result;
    }
    @Transactional(propagation = Propagation.SUPPORTS)//查询
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        List<CategoryVO> list=categoryMapperCustom.getSubCatList(rootCatId);
        return list;
    }
    @Transactional(propagation = Propagation.SUPPORTS)//查询
    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        Map<String,Object> map=new HashMap();
        map.put("rootCatId",rootCatId);
        return categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
