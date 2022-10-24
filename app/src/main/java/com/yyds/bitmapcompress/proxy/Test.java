package com.yyds.bitmapcompress.proxy;

/**
 * Created by 阿飞の小蝴蝶 on 2022/10/21
 * Describe:
 */
public class Test {
    public static void main(String[] args) {
        //testNoProxy();
        testProxy();
    }

    private static void testProxy() {
//        TargetProxy targetProxy = new TargetProxy(new Target());
//        targetProxy.show();
        ProxyBuilder proxyBuilder = new ProxyBuilder(new Target());
        Target target = (Target) proxyBuilder.createProxy();
        target.show();
    }

    private static void testNoProxy() {
        Target target = new Target();
        target.show();
    }
}
