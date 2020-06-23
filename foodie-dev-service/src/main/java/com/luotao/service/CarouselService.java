package com.luotao.service;

import com.luotao.pojo.Carousel;

import java.util.List;

/**
 * @author : luo
 * @date : 2020/3/17 20:54
 */
public interface CarouselService {
    /**
     * 查询所以轮廓图
     * @param isShow
     */
    public List<Carousel> queryAll(Integer isShow);
}
