package com.startup.shoppyauto.Retrofit2;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadData {

    public ListContact data ;
    public String jsonStr = null;

    public ListContact loadJSON(){

        OkHttpClient builder = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
       // Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://zinbee.ddns.net:3000/")
                .client(builder)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        RequestRe request = retrofit.create(RequestRe.class);
        Call<ListContact> call =  request.loadJSON();
        call.enqueue(new Callback<ListContact>() {
            @Override
            public void onResponse(Call<ListContact> call, Response<ListContact> response) {
               // ListContact  jsonResponse = response.body();
                data  = response.body();
         // data = new ArrayList<>(Arrays.asList(jsonResponse.getCont()));


            }

            @Override
            public void onFailure(Call<ListContact> call, Throwable t) {

            }
        });

        //RequestRe request = sRetrofit.create(RequestRe.class);
        Log.d("checkkk",
                String.valueOf(data));
        return data;
    }


}
