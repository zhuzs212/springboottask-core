package com.example.springboottaskcore.util;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zhu_zishuang
 * @date 2022/5/10 15:04
 */
@Data
public class Test {
    /**
     *
     */
    @NotBlank(message = "name不能为空！")
    private String name;
}
