package com.luotao.controller;

import com.luotao.service.StuService;
import com.luotao.utils.RedisOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author : luo
 * @date : 2020/3/13 17:26
 */
@ApiIgnore //不显示在swagger文档
@RestController//等于@Controller注解加返回json对象
@RequestMapping("redis")
public class RedisController {
    @Autowired
    private RedisOperator redisOperator;
    private final static Logger logger= LoggerFactory.getLogger(RedisController.class);
    @GetMapping("/set")//获得用get请求，幂等
    public Object  set(String key,String value){
        redisOperator.set(key,value);
        return "ok";
    }
    @PostMapping("/get")//增删改用post请求,保存非幂等
    public Object  saveStu(String key){
        return (String)redisOperator.get(key);
    }
    @PostMapping("/delete")//幂等
    public Object  delete(String key){
        redisOperator.del(key);
        return "ok";
    }
}
