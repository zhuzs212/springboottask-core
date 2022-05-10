package com.example.springboottaskcore.config;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;
import java.time.*;
import java.util.Date;

/**
 * LocalDateTime 序列化和反序列化配置
 *
 * @author zhu_zishuang
 * @date 2021-03-12
 */
@Configuration
public class JacksonConfig {

    /**
     * description:适配自定义序列化和反序列化策略
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer());
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());
        };
    }

    /**
     * description:序列化
     * LocalDateTime序列化为毫秒级时间戳
     */
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            if (value != null) {
                long timestamp = LocalDateTimeUtil.toEpochMilli(value);
                gen.writeNumber(timestamp);
            }
        }
    }

    /**
     * description:反序列化
     * 毫秒级时间戳序列化为LocalDateTime
     */
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            long timestamp = p.getValueAsLong();
            if (timestamp > 0) {
                return LocalDateTimeUtil.of(timestamp, ZoneOffset.of("+8"));
            } else {
                return null;
            }
        }
    }

    /**
     * description:LocalDateTime转换器，用于转换RequestParam和PathVariable参数
     * 接收毫秒级时间戳字符串——>LocalDateTime
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                //毫秒级时间戳转LocalDateTime
                return LocalDateTimeUtil.of(Long.parseLong(source), ZoneOffset.of("+8"));
            }
        };
    }

    /**
     * description:java.util.Date转换器
     * 接收毫秒级时间戳字符串——>Date
     */
    @Bean
    public Converter<String, Date> dateConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                long longTimeStamp = new Long(source);
                return new Date(longTimeStamp);
            }
        };
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        //不注释,会导致swagger报错
//        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        //关闭日期序列化为时间戳的功能
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
//        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
        // 美化json
//        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

//        //关闭序列化的时候没有为属性找到getter方法,报错
//        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        //关闭反序列化的时候，没有找到属性的setter报错
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        //序列化的时候序列对象的所有属性
//        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        SimpleModule simpleModule = new SimpleModule();
        //json值序列化
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        //json值反序列化
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    // 序列化 LocalDate
    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                Long timestamp = value.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                gen.writeNumber(timestamp);
            }
        }
    }
    // 反序列化 LocalDate
    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            // 支持时间戳 和 时间字符串
            Long timestamp = parser.getValueAsLong();
            if (timestamp > 0) {
                return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
            } else {
//                String dateTime = parser.getValueAsString();
//                LocalDate localDate = LocalDate.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                if (!ObjectUtils.isEmpty(localDate)) {
//                    return localDate;
//                } else {
//                    return null;
//                }
                return null;
            }
        }
    }
}
