package com.imooc.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private JavaConfigBean javaConfigBean;

    @RequestMapping("/index")
    public String index() {
        System.err.println("timeout: " + javaConfigBean.getTimeout());
        System.err.println("newKey: " + javaConfigBean.getNewKey());
        return "index";
    }

    //@Autowired
    //private FlowService flowService;
    //
    //@RequestMapping("/test")
    //public String test() {
    //    return flowService.test();
    //}


}
