package com.startup.shoppyauto.Retrofit2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @GET("/api_mobile/test1")
    Call<List<Contact>> getData();

    @GET("/api_mobile/data")
    Call<List<Schedules>> getSchedules(@Query("device") int device, @Query("fbid") String fbid);

    @GET("/api_mobile/updateresult")
    Call<String> updateResultSchedules(@Query("device") int device, @Query("fbid") String fbid, @Query("sid") int sid, @Query("result") int result);

    @FormUrlEncoded
    @POST("user/edit")
    Call<Schedules> postSchedules(@Field("first_name") String first, @Field("last_name") String last);

}