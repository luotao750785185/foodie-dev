package com.luotao.service;

import com.luotao.pojo.Category;
import com.luotao.pojo.vo.CategoryVO;
import com.luotao.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * @author : luo
 * @date : 2020/3/17 20:54
 */
public interface CategoryService {
    /**
     * 查询所有一级分类
     * @return
     */
    public List<Category> queryAllRootLevelCat();
    /**
     * 根据一级分类id查询子分类
     * @return
     */
    public List<CategoryVO>  getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     * @return
     */
    public List<NewItemsVO>  getSixNewItemsLazy(Integer rootCatId);



}
