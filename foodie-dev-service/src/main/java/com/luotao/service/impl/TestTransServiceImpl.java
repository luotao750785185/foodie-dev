package com.luotao.service.impl;

import com.luotao.service.StuService;
import com.luotao.service.TestTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : luo
 * @date : 2020/3/14 12:52
 */
@Service
public class TestTransServiceImpl implements TestTransService {
    /*  事务的传播
        REQUIRED(0)：
        SUPPORTS(1)
        MANDATORY(2)
        REQUIRES_NEW(3)
        NOT_SUPPORTED(4),
        NEVER(5),
        NESTED(6);
        注意trycatch包裹报错的会使得事务无法回滚，因为事务只能遇见error和runexception时报错，非runException不会，
        trycatch包裹的也可以回滚，只是事务中需要加个回滚，catch块中要抛出异常。
     */
    @Autowired
    private StuService stuService;

    //****************** REQUIRED **********************
    //如果上级有事务，则加入上级的，没有则新建一个事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void REQUIRED() {
        stuService.saveParent();
        stuService.saveChildren_REQUIRED();/*里面加了REQUIRED事务，由于外层REQUIRED方法加了事务，所以会加入到REQUIRED里面，
        里面有一个错误。所有上面的saveParent_REQUIRED方法也会被回滚，导致整个方法无效*/
    }


    //****************** SUPPORTS **********************
    //如果上级有事务，则加入上级的，没有则不用事务
    @Override
    public void SUPPORTS() {
        stuService.saveParent();
        stuService.saveChildren_SUPPORTS();/*里面加了SUPPORTS事务，由于当前方法没事务，所以整个操作都无事务，会存入父亲和孩子1*/
    }


    //****************** MANDATORY **********************
    //如果上级有事务，则加入上级的，没有则直接报错
    @Override
    public void MANDATORY() {
        stuService.saveParent();
        stuService.saveChildren_MANDATORY();/*里面加了MANDATORY事务，由于当前方法没事务，所以加了事务的这个方法
        会直接报错，因而只会保存父亲*/
    }


    //****************** REQUIRES_NEW **********************
    //如果上级有事务，则挂起它，我自己开启一个事务，我执行完后再执行上级事务，上级事务对我无影响。
    // 如果上级无事务，则我自己新建一个事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void REQUIRES_NEW() {
        stuService.saveParent();
        stuService.saveChildren_REQUIRES_NEW();/*此方法加了REQUIRES_NEW事务，由于上级有REQUIRED事务，所以上级的REQUIRED会被挂起，
        下面的报错后会触发REQUIRED事务，导致saveParent（）方法回滚，但是saveChildren方法回不回滚由saveChildren它自己决定*/
        int a = 1 / 0;
    }

    //****************** NOT_SUPPORTED **********************
    //上级有没有事务都不影响我，我就以没有事务的形式进行
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void NOT_SUPPORTED() {
        stuService.saveParent();
        stuService.saveChildren_NOT_SUPPORTED();/*里面加了NOT_SUPPORTED事务，不受外层事务影响（在执行时将外层事务挂起），
                                                    因而会存储两个孩子*/
        int a = 1 / 0;
    }

    //****************** NEVER **********************
    //上级不能有事务，有事务我会报错
    //@Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void NEVER() {
        stuService.saveParent();
        stuService.saveChildren_NEVER();/*里面加了NEVER事务，外层有事务直接报错，不会执行方法。始终以没有事务的形式运行*/
    }

    //****************** NESTED **********************
    //无主事务，子事务自己开启，自己回滚，如果required。
    //有主事务，则子事务嵌套进去，子事务报错回滚，主事务可以回滚也可以不回滚（trycatch包裹报错的子方法和其它有错的）；但是主事务回滚，则子事务
    // 必回滚（不管加不加trycatch）。
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void NESTED() {
        stuService.saveParent();
        try {
            stuService.saveChildren_NESTED();/*里面加了NEVER事务，外层有事务直接报错，不会执行方法。始终以没有事务的形式运行*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //如果在同类中调用这个方法，则此处这个事务无效，因为面向切面动态代理需要对象和对象之间调用才会触发，
    // 才会开启事务。（当然也可以获得本对象再进行调用来触发切面，开启事务）
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    //public void saveChildren_REQUIRES_NEW() {
    //    saveChildren1();
    //    saveChildren2();
    //}
}
