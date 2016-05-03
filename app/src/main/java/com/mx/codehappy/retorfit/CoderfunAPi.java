package com.mx.codehappy.retorfit;

import com.mx.codehappy.model.DataResults;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by boobooL on 2016/5/3 0003
 * Created 邮箱 ：boobooMX@163.com
 */
public interface CoderfunAPi {
    @GET("data/{type}/{number}/{page}")
    Observable<DataResults>getDataResults(
            @Path("type")String type,
            @Path("number")int number,
            @Path("page")int page);

}
