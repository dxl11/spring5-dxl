package com.dxl.demo.service.impl;

import com.dxl.demo.service.IDemoService;
import com.dxl.mvcframework.annotation.GPService;

/**
 * 核心业务逻辑
 */
@GPService
public class DemoServiceImpl implements IDemoService {

    @Override
    public String get(String name) {
        return "my name is" + name;
    }
}
