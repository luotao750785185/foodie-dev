package com.luotao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author : luo
 * @date : 2020/3/15 16:21
 */
@Configuration//让容器扫描，不知这个类属于controller还是service就用这个注解
@EnableSwagger2
public class Swagger2 {
    //http://localhost:8088/swagger-ui.html    //原路径
    //http://localhost:8088/doc.html           //另一种文档
    //配置Swagger2 核心配置 docket
    @Bean
    public Docket createRestApi(){
return  new Docket(DocumentationType.SWAGGER_2)//指定API类型为SWAGGER_2的
        .apiInfo(apiInfo())                    //定义api文档汇总信息
        .select().apis(RequestHandlerSelectors.basePackage("com.luotao.controller")) //指定controller包
        .paths(PathSelectors.any())            //包下所有文件
        .build();
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("电商平台接口API")  //文档页标题
                .contact(new Contact("罗滔",    //作者
                        "http://111.231.66.209:8081/foodie-shop/",
                        "Lt0000001@outlook.com"))    //联系人信息
                .description("专为前端提供的api文档")   //详细详细
                .version("0.0.1")                     //版本信息
                .termsOfServiceUrl("http://111.231.66.209:8081/foodie-shop/index.html")   //网站地址
                .build();

    }
}
