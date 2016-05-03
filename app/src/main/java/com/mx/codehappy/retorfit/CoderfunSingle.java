package com.mx.codehappy.retorfit;

/**
 * Created by boobooL on 2016/5/3 0003
 * Created 邮箱 ：boobooMX@163.com
 */
public class CoderfunSingle {
    public volatile static  CoderfunAPi CoderfunAPISingleton=null;

    public static  CoderfunAPi getInstance(){
        if(CoderfunAPISingleton==null){
            synchronized (CoderfunSingle.class){
                if(CoderfunAPISingleton==null){
                    CoderfunAPISingleton=new CoderfunRetrofit().createService(CoderfunAPi.class);
                }
            }
        }
        return CoderfunAPISingleton;
    }
}
