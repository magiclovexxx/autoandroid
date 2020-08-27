package com.startup.shoppyauto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aquery.AQuery;
import com.aquery.listener.QueryNetworkListener;
import com.startup.shoppyauto.Retrofit2.APIService;
import com.startup.shoppyauto.Retrofit2.Contact;
import com.startup.shoppyauto.Retrofit2.RetrofitClient;

import java.io.OutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart=findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAuto();
            }
        });

        btnStart.setText(getString(R.string.description));

       // getData();
    }

    public  void  loadData(){
        AQuery aQuery=new AQuery(this);
        aQuery.ajax("http://zinbee.ddns.net:3000/getdata").get().response(new QueryNetworkListener() {
            @Override
            public void response(String s, Throwable throwable) {
                Log.d("SonLv","res"+s);
            }
        });
    }

    public void  getData(){
        Log.d("SonLv","getdata");
        APIService apiService= RetrofitClient.getClient().create(APIService.class);
        apiService.getData().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                //Nếu ok thì về dây
               Log.d("SonLv","res: "+response.body().size());

                for (int i = 0; i < response.body().size(); i++) {
                    Log.d("SonLv","name: "+response.body().get(i).getName());
                }

            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
//Nếu có lỗi thì về ây
                Log.d("SonLv","error: "+t.getMessage());
            }
        });

    }


    public void callAuto(){
        try {
            OutputStream out = new ProcessBuilder(new String[0]).redirectErrorStream(true).command(new String[]{"su"}).start().getOutputStream();
            out.write("am instrument -w -r   -e debug false -e class 'com.startup.shoppyauto.AutoLogin' com.startup.shoppyauto.test/androidx.test.runner.AndroidJUnitRunner\n".getBytes("UTF-8"));
            out.flush();
            out.close();
            showToast("Bắt đầu thực hiện Auto");
        } catch (Exception e) {
            Log.d("SonLv", "Exception: " + e.getMessage());
            showToast("Có lỗi khi Auto");
        }
    }
    public  void  showToast(String sms){
        Toast.makeText(this,sms,Toast.LENGTH_LONG).show();
    }

}
