package com.jash.lesson2;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by jash
 * Date: 15-12-29
 * Time: 下午2:09
 */
public interface QsbkService {
    @GET("article/list/{type}")
    Call<Response> getList(@Path("type") String type, @Query("page") int page);
}
