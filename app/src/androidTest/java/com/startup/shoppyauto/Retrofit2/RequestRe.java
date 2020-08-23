package com.startup.shoppyauto.Retrofit2;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestRe {
    @GET("getdata")
    Call<ListContact> loadJSON();
}

