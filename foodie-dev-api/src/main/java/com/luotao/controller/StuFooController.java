package com.luotao.controller;

import com.luotao.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

//@Controller
@ApiIgnore
@RestController//作为测试所用的
public class StuFooController {

    @Autowired
    private StuService stuService;

    //@GetMapping("/getStu")
    //public Object getStu(int id) {
    //    return stuService.getStuInfo(id);
    //}
    //
    //@PostMapping("/saveStu")
    //public Object saveStu() {
    //    stuService.saveStu();
    //    return "OK";
    //}
    //
    //@PostMapping("/updateStu")
    //public Object updateStu(int id) {
    //    stuService.updateStu(id);
    //    return "OK";
    //}
    //
    //@PostMapping("/deleteStu")
    //public Object deleteStu(int id) {
    //    stuService.deleteStu(id);
    //    return "OK";
    //}


}
