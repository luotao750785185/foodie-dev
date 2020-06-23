package com.luotao.service.impl;

import com.luotao.mapper.CarouselMapper;
import com.luotao.pojo.Carousel;
import com.luotao.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author : luo
 * @date : 2020/3/17 20:57
 */
@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Carousel> queryAll(Integer isShow) {
        //用mapper接口继承myMapper来的方法来实现查询
        Example example=new Example(Carousel.class);
        example.orderBy("sort").desc();//排序（默认升序）
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isShow",isShow);
        List<Carousel> result=carouselMapper.selectByExample(example);
        return result;
    }
}
