package com.example.springboottaskcore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 全局跨域控制
 *
 * @author zhuzishuang
 * @date 2022/5/10  17:34
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.path}")
    private String path;

    /**
     * 资源配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
        // 映射本地服务器文件
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("file:" + path);
    }

    /**
     * 拦截器配置
     * @return
     */
    @Bean
    public SecurityInterceptor securityInterceptor(){
        return new SecurityInterceptor();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 参数转换 用户LocalDateTIme 和 时间戳之间的转换
        JacksonConfig jacksonConfig = new JacksonConfig();
        converters.add(new MappingJackson2HttpMessageConverter(jacksonConfig.objectMapper()));
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.forEach(converter -> {
            if (converter instanceof StringHttpMessageConverter){
                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("UTF-8"));
            }
        });
    }

    /**
     * 拦截器作用范围
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                // 添加拦截规则
                .addInterceptor(securityInterceptor())
                // 拦截目录
                .addPathPatterns("/**")
                // 白名单目录
                .excludePathPatterns(
                        "/attach/**",
                        "/static/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/**",
                        "/error",
                        "/webjars/**",
                        "/notify/**"
                );
    }
}
