package com.startup.shoppyauto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart=findViewById(R.id.btnStart);
       // dataSharePre ();
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("url", "teamvietdev.com");
        editor.putString("name", "Team Việt Dev");
        editor.putBoolean("active", true);
        editor.commit();


        dataSharePre ();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAuto();
            }
        });




        btnStart.setText(getString(R.string.description));
    }

    public void dataSharePre (){
        Log.d("Toantq", "Start data share pre ");

        String url = sharedPreferences.getString("url", "");
        String name = sharedPreferences.getString("name", "");
        boolean active = sharedPreferences.getBoolean("active", false);

        Log.d("Toantq", "url " + url);
        Log.d("Toantq", "name " + name);
        Log.d("Toantq", "active " + active);
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
