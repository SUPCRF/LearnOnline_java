package org.supcrf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @title: Swagger2Config
 * @projectName learn-online_java
 * @description: TODO
 * @Author supcrf
 * @Version 1.0
 * @Date 2/13/2021 21:50
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket swagger(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(apiInfo())
                .groupName("learn 1.0")
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("org.supcrf.controller"))
                .build();
    }
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("SUPCRF接口文档")
                .version("v1.0")
                .description("springboot+vue系统")
                .build();
    }
}