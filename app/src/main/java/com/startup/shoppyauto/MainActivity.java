package com.startup.shoppyauto;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.startup.shoppyauto.Actions.PermissionSupport;
import com.startup.shoppyauto.Model.DataSharePre;

import java.io.OutputStream;

public class MainActivity<deviceId> extends AppCompatActivity {

    Button btnStart, btnStop;
    TextView deviceName;
    TextView TextVersionCode;
    TextView fbaccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btnStart);
        btnStop=findViewById(R.id.btnStop);

        //DataSharePre.saveDataSharedInt(getApplicationContext(),"deviceId",10);
        getDeviceId();
        setDataSetting();

        btnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int PID1= android.os.Process.getUidForName("com.auto.autoupdate");
                android.os.Process.killProcess(PID1);

                String autoStatus = DataSharePre.getDataSharedString(getApplicationContext(), "auto");

                Log.d("ToanTQ", "autoStatus: " + autoStatus);
                DataSharePre.saveDataSharedString(getApplicationContext(), "auto", "yes");

                callAuto();
            }
        });

        btnStop.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                DataSharePre.saveDataSharedString(getApplication(),"auto","no");
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                showToast("Tạm dừng Auto");
            }
        });
        checkQuyen();
    }


    public void checkQuyen() {
        PermissionSupport.getInstall(this).requestPermissionStore();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Khi xin quyền xong nó trả về đây

        checkQuyen();
    }

    public void getDeviceId() {
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        DataSharePre.saveDataSharedString(getApplication(), "deviceId", deviceId);
    }

    public void setDataSetting() {
        String deviceId = DataSharePre.getDataSharedString(getApplicationContext(), "deviceId");
        int fbaccountsvalue = DataSharePre.getDataSharedInt(getApplicationContext(), "fbaccounts");
        // String versionCode = DataSharePre.getDataSharedString(getApplicationContext(),"version_code");
        String versionName = DataSharePre.getDataSharedString(getApplicationContext(), "version_code");
        deviceName = (TextView) findViewById(R.id.devicename);
        deviceName.setText(deviceId);
        TextVersionCode = (TextView) findViewById(R.id.versioncodevalue);
        TextVersionCode.setText(versionName);
//        fbaccounts = (TextView) findViewById(R.id.fbaccountsvalue);
//        fbaccounts.setText(fbaccountsvalue);

    }


    public void callAuto() {
        try {
            OutputStream out = new ProcessBuilder(new String[0]).redirectErrorStream(true).command(new String[]{"su"}).start().getOutputStream();
            out.write("am instrument -w -r   -e debug false -e class 'com.startup.shoppyauto.AutoLogin' com.startup.shoppyauto.test/androidx.test.runner.AndroidJUnitRunner\n".getBytes("UTF-8"));
            out.flush();
            out.close();
            showToast("Bắt đầu thực hiện Auto");
        } catch (Exception e) {
            Log.d("ToanTQ", "Exception: " + e.getMessage());
            showToast("Có lỗi khi Auto");
        }
    }


    public void showToast(String sms) {
        Toast.makeText(this, sms, Toast.LENGTH_LONG).show();
    }

}
