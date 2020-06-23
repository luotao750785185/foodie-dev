package com.luotao.controller;

import com.luotao.service.StuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class HelloController {
    @Autowired
    private StuService stuService;
    private final static Logger logger= LoggerFactory.getLogger(HelloController.class);
    @GetMapping("/getStuInfo")//获得用get请求，幂等
    public Object  getStuInfo(int id){
        //return stuService.getStuInfo(id);
        return "";
    }
    @PostMapping("/saveStu")//增删改用post请求,保存非幂等
    public Object  saveStu(){
        //stuService.saveStu();
        return "ok";
    }
    @PostMapping("/updateStu")//幂等
    public Object  updateStu(int id){
        //stuService.updateStu(id);
        return "ok";
    }
    @PostMapping("/deleteStu")//幂等
    public Object  deleteStu(int id){
        //stuService.deleteStu(id);
        return "ok";
    }

    @GetMapping("/getSession")
    public Object setSession(HttpServletRequest request){
        HttpSession session=request.getSession();
        //设置session,与此同时，客服端会自动设置一个键为jsessionid(值为数字字母型的字符串)的cookie
        session.setAttribute("userInfo","new user");
        session.setMaxInactiveInterval(3600);//0为永不过时
        session.getAttribute("userInfo");
        //移除session
        session.removeAttribute("userInfo");
        return "ok";
    }

    @GetMapping("/loging")
    public Object loging(){
        logger.debug("debug:hello");
        logger.info("info:hello");
        logger.warn("warn:hello");
        logger.error("error:hello");
        return "ok";
    }


}
