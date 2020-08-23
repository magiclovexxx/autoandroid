package com.startup.shoppyauto.Retrofit2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @GET("/getdata")
    Call<List<Contact>> getData();

    @GET("/getdata2")
    Call<List<Contact>> getData2(@Query("id") String id, @Query("name") String name);
}