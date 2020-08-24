package com.startup.shoppyauto.Retrofit2;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
 
public class RetrofitClient {
 
    private static Retrofit retrofit = null;
 
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://zinbee.ddns.net:3000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ArrayList<Contact> getDataUrl(){

         ArrayList<Contact> dataContact = new ArrayList<>();

        APIService apiService= RetrofitClient.getClient().create(APIService.class);
        apiService.getData().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                //Nếu ok thì về dây

                Log.d("SonLv","data22: ");

                Log.d("SonLv","res: "+ response.body().size());
                for (int i = 0; i < response.body().size(); i++) {
                    dataContact.add(response.body().get(i));

                    // Đến đây dataContact vẫn có giá trị đúng
                    Log.d("SonLv","name: "+dataContact.get(i).getName());

                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
//Nếu có lỗi thì về ây
                Log.d("SonLv","error: "+t.getMessage());
            }
        });
        // Đến đây dataContact là nó không có giá trị gì
        Log.d("SonLv","dataContact: "+ dataContact.size());
        return dataContact;
    }

}
