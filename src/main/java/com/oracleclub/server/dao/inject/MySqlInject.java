package com.oracleclub.server.dao.inject;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :RETURN
 * @date :2021/9/22 0:05
 */
public class MySqlInject extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        // 获取父类中的集合
        List<AbstractMethod> methodList = new ArrayList<>(super.getMethodList(mapperClass));

        // 再扩充自定义的方法
        methodList.add(new SelectAllExist());
        return methodList;
    }
}
