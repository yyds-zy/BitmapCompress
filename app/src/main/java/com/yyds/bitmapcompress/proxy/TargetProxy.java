package com.yyds.bitmapcompress.proxy;

/**
 * Created by 阿飞の小蝴蝶 on 2022/10/21
 * Describe:
 */
public class TargetProxy {

    private Target mTarget;

    public TargetProxy(Target target){
        mTarget = target;
    }

    public void show(){
        mTarget.show();
    }

}
