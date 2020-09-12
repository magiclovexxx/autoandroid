package com.startup.shoppyauto;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.startup.shoppyauto.Actions.PermissionSupport;
import com.startup.shoppyauto.Model.DataSharePre;

import java.io.File;
import java.io.OutputStream;

import static androidx.core.content.ContextCompat.startActivity;

public class MainActivity<deviceId> extends AppCompatActivity {

    Button btnStart, btnStop;
    EditText edittextdevice;
    TextView TextDevice, TextAutoStatus, TextVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart=findViewById(R.id.btnStart);
        btnStop=findViewById(R.id.btnStop);

        //DataSharePre.saveDataSharedInt(getApplicationContext(),"deviceId",10);

        setDataSetting ();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSharePre.saveDataSharedString(getApplicationContext(),"auto","yes");
                callAuto();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                DataSharePre.saveDataSharedString(getApplication(),"auto","no");
                showToast("Tạm dừng Auto");
            }
        });
        btnStart.setText(getString(R.string.description));
        btnStop.setText("STOP AUTO");

        checkQuyen();
    }

    public void  checkQuyen(){
        PermissionSupport.getInstall(this).requestPermissionStore();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Khi xin quyền xong nó trả về đây

        checkQuyen();
    }

    public void getDeviceId(){
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        DataSharePre.saveDataSharedString(getApplication(),"device",deviceId);
    }

    public  void  setDataSetting (){
        String deviceId = DataSharePre.getDataSharedString(getApplicationContext(),"deviceId");
        String deviceStatus = DataSharePre.getDataSharedString(getApplicationContext(),"auto");
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        edittextdevice = (EditText) findViewById(R.id.edittextdevice);
        edittextdevice.setText(deviceId);
        TextDevice = (TextView) findViewById(R.id.deviceid);
        TextDevice.setText(deviceId);
        TextAutoStatus = (TextView) findViewById(R.id.devicestatus);
        TextAutoStatus.setText(deviceStatus);
        TextVersionCode = (TextView) findViewById(R.id.versioncodevalue);
        TextVersionCode.setText(versionName);

    }


    public void callAuto(){
        try {
            OutputStream out = new ProcessBuilder(new String[0]).redirectErrorStream(true).command(new String[]{"su"}).start().getOutputStream();
            out.write("am instrument -w -r   -e debug false -e class 'com.startup.shoppyauto.AutoLogin' com.startup.shoppyauto.test/androidx.test.runner.AndroidJUnitRunner\n".getBytes("UTF-8"));
            out.flush();
            out.close();
            showToast("Bắt đầu thực hiện Auto");
        } catch (Exception e) {
            Log.d("Toantq", "Exception: " + e.getMessage());
            showToast("Có lỗi khi Auto");
        }
    }



    public  void  showToast(String sms){
        Toast.makeText(this,sms,Toast.LENGTH_LONG).show();
    }

}
