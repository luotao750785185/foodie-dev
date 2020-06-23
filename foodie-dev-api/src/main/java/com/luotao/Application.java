package com.luotao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
//自定义扫描mybatis所在mapper(不是xml文件)的包,@SpringBootApplication只会扫描与application
// 同级别的包（即Java下的包），mapper的在resource文件夹，扫描不到
@MapperScan(basePackages = "com.luotao.mapper")
//扫描所有包和相关组件包
@ComponentScan(basePackages={"com.luotao","org.n3r.idworker"})

public class Application {

    /*1、run方法可以去跑一个springApplication这样的一个实例的（这个实例就是第一个参数），
      2、这个run会返回一个上下文对象（一个正在运行的applicationContext），
      3、@SpringBootApplication注解中有@SpringBootConfiguration、@EnableAutoConfiguration、
         @ComponentScan 三个注解。@SpringBootConfiguration将当前类中有@bean注解的方法实例加载
         到spring容器；@EnableConfiguration加载spring.factories中的配置类；@ComponentScan扫描
         自定义路径下符合扫描规则的类加载到spring容器中。

      4、之后会扫描application类所在包下的文件，加载容器，启动内置Tomcat启动项目*/
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
