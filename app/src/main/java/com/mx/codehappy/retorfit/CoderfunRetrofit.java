package com.mx.codehappy.retorfit;

import com.mx.codehappy.constant.CoderfunKey;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by boobooL on 2016/5/3 0003
 * Created 邮箱 ：boobooMX@163.com
 */
public class CoderfunRetrofit {
    public static final OkHttpClient httpClient=new OkHttpClient();
    public static Retrofit.Builder sBuilder=new Retrofit.Builder()
            .baseUrl(CoderfunKey.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create());


    public <S> S createService(Class<S> serviceClass){
        Retrofit retrofit=sBuilder.client(httpClient).build();
        return  retrofit.create(serviceClass);
    }

}
