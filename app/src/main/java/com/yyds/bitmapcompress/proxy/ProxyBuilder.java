package com.yyds.bitmapcompress.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 阿飞の小蝴蝶 on 2022/10/21
 * Describe:
 */
public class ProxyBuilder {

    private Object mObj;

    public ProxyBuilder(Object obj){
        mObj = obj;
    }

    public Object createProxy() {
        ClassLoader classLoader = mObj.getClass().getClassLoader(); //获取当前对象的类加载器对象
        Class<?>[] interfaces = mObj.getClass().getInterfaces(); //该类所实现的所有接口的对象
        return Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler(){
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return method.invoke(mObj, objects);
            }
        });
    }
}
