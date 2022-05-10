package com.example.springboottaskcore.config;

import cn.hutool.core.util.StrUtil;
import com.example.springboottaskcore.constant.CommonConstant;
import com.example.springboottaskcore.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 接口拦截器
 *
 * @author zhuzishuang
 * @date 2022/5/10  17:48
 */
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        // 如果是OPTIONS结束请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.OK.value());
            return true;
        }
        String token = request.getHeader(CommonConstant.AUTH_TOKEN_KEY);
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String jumpurl = requestUri.substring(contextPath.length());
        // 静态文件暂不做拦截
        String[] content = jumpurl.split("\\.");
        String [] fileTypes = {"jpg", "png", "jpeg", "gif", "ico", "txt"};
        if (content.length == 2
                && jumpurl.endsWith(content[1])
                && Arrays.asList(fileTypes).contains(content[1].toLowerCase())) {
            return true;
        }
        if (StrUtil.isNotEmpty(token) && redisUtil.getExpire(token) > 0 &&  redisUtil.hasKey(token)) {
            // token 有效
            redisUtil.expire(token,CommonConstant.REDIS_LOGIN_EXPIRE);
        } else {
            log.info("jumpurl = {}", jumpurl);

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
