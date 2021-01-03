package com.startup.shoppyauto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.startup.shoppyauto.Actions.PermissionSupport;
import com.startup.shoppyauto.Model.DataSharePre;

import java.io.OutputStream;

public class MainActivity<deviceId> extends AppCompatActivity {

    Button btnStart;
    TextView deviceName;
    TextView TextVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart=findViewById(R.id.btnStart);

        //DataSharePre.saveDataSharedInt(getApplicationContext(),"deviceId",10);
        getDeviceId();
        setDataSetting ();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String autoStatus = DataSharePre.getDataSharedString(getApplicationContext(),"auto");

                if(autoStatus.equals("no")){
                    Log.d("ToanTQ", "autoStatus: " + autoStatus);
                    DataSharePre.saveDataSharedString(getApplicationContext(),"auto","yes");
                    String deviceStatus = "yes";

                    btnStart.setText("Stop AuTo");
                    callAuto();
                }else{
                    DataSharePre.saveDataSharedString(getApplication(),"auto","no");
                    String deviceStatus = "No";

                    btnStart.setText("Start AuTo");
                    showToast("Tạm dừng Auto");
                }
            }
        });

        String autoStatus = DataSharePre.getDataSharedString(getApplicationContext(),"auto");

        if(autoStatus.equals("no")){
            btnStart.setText("Start auto");
        }else{
            btnStart.setText("Stop auto");
        }

       //
       // btnStop.setText("STOP AUTO");

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
        DataSharePre.saveDataSharedString(getApplication(),"deviceId",deviceId);
    }

    public  void  setDataSetting (){
        String deviceId = DataSharePre.getDataSharedString(getApplicationContext(),"deviceId");
        String deviceStatus = DataSharePre.getDataSharedString(getApplicationContext(),"auto");
       // String versionCode = DataSharePre.getDataSharedString(getApplicationContext(),"version_code");
        String versionName = DataSharePre.getDataSharedString(getApplicationContext(),"version_code");
        deviceName = (TextView) findViewById(R.id.devicename);
        deviceName.setText(deviceId);
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
            Log.d("ToanTQ", "Exception: " + e.getMessage());
            showToast("Có lỗi khi Auto");
        }
    }



    public  void  showToast(String sms){
        Toast.makeText(this,sms,Toast.LENGTH_LONG).show();
    }

}
