package com.example.springboottaskcore.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger-ui 配置
 *
 * @author zhuzishuang
 * @date 2022/5/10  17:34
 */
@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        //返回文档摘要信息
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 生成接口信息，包括标题、联系人等
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BSS接口文档")
                .description("有疑问,请联系对应的服务端。")
                .contact(new Contact("杨光", "", "evanyangg@163.com"))
                .version("1.0")
                .build();
    }
}
