package com.luotao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author : luo
 * @date : 2020/3/15 20:58
 * 此类作为跨域的设置
 */
@Configuration
public class CorsConfig {
     CorsConfig(){

    }
    @Bean
    public CorsFilter corsFilter(){
         //1.添加cors配置信息
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        //放通部分跨域请求
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.addAllowedOrigin("http://111.231.66.209:8081");
        corsConfiguration.addAllowedOrigin("http://111.231.66.209");

        //是否允许请求添加一些东西，需要cookie，所以true
        corsConfiguration.setAllowCredentials(true);
        //设置允许的方式（GET,POST等）
        corsConfiguration.addAllowedMethod("*");
        //设置允许的header
        corsConfiguration.addAllowedHeader("*");

        //2.为URL添加映射路径
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource=new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);

        //3.返回重新定义好的urlBasedCorsConfigurationSource
        return new CorsFilter(urlBasedCorsConfigurationSource);

    }

}
