package com.example.springboottaskcore.config;

import dev.akkinoc.spring.boot.orika.OrikaMapperFactoryConfigurer;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

/**
 * @author evanyang
 * @description 自定义映射
 * @date 2019/10/10 15:41
 */
@Component
public class MapperFadeConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory orikaMapperFactory) {

    }
}
