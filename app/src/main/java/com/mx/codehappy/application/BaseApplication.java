package com.mx.codehappy.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by boobooL on 2016/5/3 0003
 * Created 邮箱 ：boobooMX@163.com
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        //FlowManager.init(this);

    }
}
