/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.startup.shoppyauto;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

import com.startup.shoppyauto.Retrofit2.APIService;
import com.startup.shoppyauto.Retrofit2.Contact;
import com.startup.shoppyauto.Retrofit2.RetrofitClient;
import com.startup.shoppyauto.Retrofit2.Schedules;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;


@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AutoLogin {
    String TAG = "SonLv";
    private UiDevice mDevice;

    List<Contact> dataContact = new ArrayList<>();
    List<Schedules> dataSchedules = new ArrayList<>();
    int step = 0;
    boolean isFinish = false;


    @Before
    public void startMainActivityFromHomeScreen() {
        mDevice = UiDevice.getInstance(getInstrumentation());

       // Get data from server
      //  loadDataApi();

        // Lấy schedule cần tương tác
        getSchedules();

        try {
            mDevice.wakeUp();
            Log.d("SonLv", "wakeUp");

        } catch (Exception e) {
            Log.d("SonLv", "Exception: " + e.getMessage());
        }

        oppenAppActivity();
    }

    @Test
    public void runTesst() {
        autoView();
    }


    public void autoView() {
        Log.d("SonLv", "autoView: " + step);

        Log.d("SonLv", "getCategory: " + dataSchedules.get(0).getCategory());



        if (isFinish) {
            Log.d(TAG, "Kết thúc quá trình auto!");
            return;
        }


    /*    try {
            mDevice.wakeUp(); // sang màn hình, mở khoá màn hình
        } catch (RemoteException e) {
            e.printStackTrace();
        } */


      //  checkView();
        searchProduct();

        sleep(5000);
      //  autoView();
    }

    public  void loadDataApi(){
        APIService apiService= RetrofitClient.getClient().create(APIService.class);
        apiService.getData().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                //Nếu ok thì về dây

                if(response.body().size()>0){
                    for (int i = 0; i < response.body().size(); i++) {
                        dataContact.addAll(response.body());

                        // Đến đây dataContact vẫn có giá trị đúng
                        Log.d("SonLv","name: "+dataContact.get(i).getName());

                    }
                }

            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
    //Nếu có lỗi thì về ây
                Log.d("SonLv","error: "+t.getMessage());
            }
        });
        // Đến đây dataContact là nó không có giá trị gì

    }

    public  void getSchedules(){
        APIService apiService= RetrofitClient.getClient().create(APIService.class);
        apiService.getSchedules(1,"100010760317952").enqueue(new Callback<List<Schedules>>() {
            @Override
            public void onResponse(Call<List<Schedules>> call, Response<List<Schedules>> response) {
                //Nếu ok thì về dây

                if(response.body().size() > 1){
                    for (int i = 0; i < response.body().size(); i++) {
                        dataSchedules.addAll(response.body());

                        // Đến đây dataContact vẫn có giá trị đúng
                        Log.d("SonLv","accountId: "+ dataSchedules.get(i).getAccountId());

                    }
                }else {
                    dataSchedules.addAll(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Schedules>> call, Throwable t) {
            //Nếu có lỗi thì về ây
                Log.d("SonLv","error: "+t.getMessage());
            }
        });

    }


    public void searchProduct() {

        try {
            UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));
            if (appViews1 == null) return;
            appViews1.scrollTextIntoView("Thích");
            Log.d("SonLv", "Scroll filter");
        } catch (UiObjectNotFoundException  e) {
            Log.d("SonLv", "Exception: " + e.getMessage());
        }
        UiObject2  view = mDevice.findObject(By.text("Thích"));
        if (view != null) {
            view.click();
            Log.d("SonLv", "Click Like");
            step = 3;
            isFinish=true;
        }

    }

    public void checkView() {
        Log.d("checkView", "processView: " + step);
        try {
            List<UiObject2> listView = mDevice.findObjects(By.enabled(true));
            Log.d("checkView", "----------------------View-------------------------");
            Log.d("checkView", "list: " + listView.size());
            for (int i = 0; i < listView.size(); i++) {
                UiObject2 view = listView.get(i);
                try{
                    Log.d("checkView", i + " getClassName: " + view.getClassName());
                    Log.d("checkView", i + " getResourceName: " + view.getResourceName());
                    Log.d("checkView", i + " getText: " + view.getText());
                    Log.d("checkView", i + " getContentDescription: " + view.getContentDescription());
                    Log.d("checkView", "-----------------------------------------------\n");
                }catch (Exception e){}
            }

        } catch (Exception e) {
            Log.d("checkView", "Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sleep() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }
    }

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {

        }
    }


    public void oppenAppActivity() {
        mDevice.pressHome();
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage("com.facebook.katana");
        if (intent == null) {
            oppenMainActivity("Không có app facebook");
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }


    public void oppenMainActivity(String sms) {
        Log.d("SonLv", "sms: " + sms);
        isFinish = true;

        mDevice.pressHome();
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BuildConfig.APPLICATION_ID);
        intent.putExtra("sms", sms);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}
