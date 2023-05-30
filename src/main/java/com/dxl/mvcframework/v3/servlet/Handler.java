package com.dxl.mvcframework.v3.servlet;

import com.dxl.mvcframework.annotation.GPRequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Handler {

    //保存方法对应的实例
    protected Object controller;
    //保存映射的方法
    protected Method method;
    protected Pattern pattern;
    //参数顺序
    protected Map<String, Integer> paramIndexMapping;

    /**
     * 构造一个Hanlder的基本参数
     *
     * @param pattern
     * @param controller
     * @param method
     */
    protected Handler(Pattern pattern, Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
        paramIndexMapping = new HashMap<String, Integer>();
        putParamIndexMapping(method);
    }

    private void putParamIndexMapping(Method method) {
        //提取方法中加了注解的参数
        Annotation[][] pa = method.getParameterAnnotations();
        for (int i = 0; i < pa.length; i++) {
            for (Annotation a : pa[i]) {
                if (a instanceof GPRequestParam) {
                    String paramName = ((GPRequestParam) a).value();
                    if (!"".equals(paramName.trim())) {
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }
        //提取方法中的request和response参数
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i];
            if (type == HttpServletRequest.class) {
                paramIndexMapping.put(type.getName(), i);
            }

        }
    }
}
