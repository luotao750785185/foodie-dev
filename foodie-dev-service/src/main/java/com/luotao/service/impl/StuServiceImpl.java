package com.luotao.service.impl;

import com.luotao.mapper.StuMapper;
import com.luotao.pojo.Stu;
import com.luotao.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author : luo
 * @date : 2020/3/14 0:14
 */
@Service//业务层注解
public class StuServiceImpl implements StuService {
    @Autowired
    private StuMapper stuMapper;

    public void saveParent(){
        Stu stu = new Stu();
        stu.setName("parent");
        stu.setAge(19);
        stuMapper.insert(stu);
    }
    public void saveChildren1() {
        Stu stu = new Stu();
        stu.setName("children1");
        stu.setAge(25);
        stuMapper.insert(stu);
    }
    public void saveChildren2() {
        Stu stu = new Stu();
        stu.setName("children2");
        stu.setAge(25);
        stuMapper.insert(stu);
    }

    //****************** REQUIRED **********************
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveChildren_REQUIRED() {
        saveChildren1();
        int a = 1 / 0;
        saveChildren2();
    }
    //**********

    //****************** SUPPORTS **********************
    public void saveChildren_SUPPORTS() {
        saveChildren1();
        int a = 1 / 0;
        saveChildren2();
    }
    //**********

    //****************** MANDATORY **********************
    @Transactional(propagation = Propagation.MANDATORY)
    public void saveChildren_MANDATORY() {
        saveChildren1();
        saveChildren2();
    }
    //**********

    //****************** REQUIRES_NEW **********************
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveChildren_REQUIRES_NEW() {
        saveChildren1();
        saveChildren2();
    }
    //**********

    //****************** NOT_SUPPORTED **********************
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveChildren_NOT_SUPPORTED() {
        saveChildren1();
        saveChildren2();
    }
    //**********

    //****************** NEVER **********************
    @Transactional(propagation = Propagation.NEVER)
    public void saveChildren_NEVER() {
        saveChildren1();
        int a = 1 / 0;
        saveChildren2();
    }
    //**********

    //****************** NESTED **********************
    @Transactional(propagation = Propagation.NESTED)
    public void saveChildren_NESTED() {
        saveChildren1();
        saveChildren2();
    }
    //**********
}
