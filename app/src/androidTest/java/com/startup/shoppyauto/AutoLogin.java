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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

import com.startup.shoppyauto.Model.DataSharePre;
import com.startup.shoppyauto.Retrofit2.APIService;
import com.startup.shoppyauto.Retrofit2.Contact;
import com.startup.shoppyauto.Retrofit2.RetrofitClient;
import com.startup.shoppyauto.Retrofit2.Schedules;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.startup.shoppyauto.MainActivity.*;


@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AutoLogin {
    String TAG = "ToanTQ";
    private UiDevice mDevice;

    List<Contact> dataContact = new ArrayList<>();
    List<Schedules> dataSchedules = new ArrayList<>();
    String dataUpdateResultSchedules;
    int step = 0;
    boolean isFinish = false;

    @Before
    public void startMainActivityFromHomeScreen() {
        mDevice = UiDevice.getInstance(getInstrumentation());
        //String deviceId = Settings.Secure.ANDROID_ID;
        try {
            mDevice.wakeUp();
            Log.d("ToanTQ", "wakeUp");

        } catch (Exception e) {
            Log.d("ToanTQ", "Exception: " + e.getMessage());
        }
        oppenAppActivity();
    }

    @Test
    public void runTesst() {
      //  DataSharePre.saveDataSharedString(getApplicationContext(),"deviceId", "2");

        runAllTime();
       // checkView();
    }

    public void runAllTime() {

        String deviceId =  Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        //String deviceId = DataSharePre.getDataSharedString(getApplicationContext(),"deviceId");
        Log.d("Toantq", "deviceID runall: " + deviceId);
        getSchedules(deviceId, "12345" );

        long start = System.currentTimeMillis();
        long end = start + 60000;

        while (dataSchedules == null) {
            if (System.currentTimeMillis() > end) {
                Log.d("ToanTQ", "Không lấy được dữ liệu từ sv: ");
                break;
            }
        }

        if (dataSchedules != null) {

            String checkAuto = DataSharePre.getDataSharedString(getApplicationContext(),"auto");

            if(checkAuto.equals("yes")){
                Log.d("ToanTQ", "auto: " + checkAuto);
                if (dataSchedules.size() == 1) {
                    autoView(dataSchedules.get(0));
                }
                if (dataSchedules.size() > 1) {
                    for (int i = 0; i < dataSchedules.size(); i++) {
                        Log.d("ToanTQ", "bat dau tuong tac: " + i);
                        autoView(dataSchedules.get(i));
                        sleep(ranInt(5000, 10000));
                    }
                }

                dataSchedules.clear();
                sleep(ranInt(10000, 20000));
                Log.d("ToanTQ", "tuong tac lap lai ");
                runAllTime();

            }else {
                Log.d("ToanTQ", "auto: " + checkAuto);

            }
        }
    }

    public void autoView(Schedules schedule) {
        String deviceId =  Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("ToanTQ", "start auto Facebook: ");

        if (isFinish) {
            Log.d(TAG, "Kết thúc quá trình auto!");
            return;
        }

        try {
            mDevice.wakeUp(); // sang màn hình, mở khoá màn hình
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        String linkPost = schedule.getTitle();
        //  String linkPost = "https://www.facebook.com/224894154970611/posts/760376101422411";

        startIntentFace(getApplicationContext(), linkPost);

        if (schedule.getType().equals("bulk_like")) {
            Log.d(TAG, "Bat dau like: ");
            int likeResult = likePost();
            if (likeResult == 1) {
                updateResultSchedules(deviceId, "12345", schedule.getId(), likeResult);
            }
            sleep(ranInt(5000, 10000));

            Log.d(TAG, "Dữ liệu gửi lên sv : " + dataUpdateResultSchedules);
        }

        if (schedule.getType().equals("seeding")) {
            Log.d(TAG, "Bat dau seeding: ");

            int commentResult = 0;
            String[] ArrayMessenger = new String[0];

            // Mảng các comment
            ArrayMessenger = dataSchedules.get(0).getMessage().toString().split("\n");

            // Lấy ngẫu nhiên 1 comment
            Log.d(TAG, "Tin nhan seeding: " + ArrayMessenger[ranInt(0, ArrayMessenger.length - 1)]);
            commentResult = commentPost(ArrayMessenger[ranInt(0, ArrayMessenger.length - 1)]);

            if (commentResult == 0) {
                updateResultSchedules(deviceId, "12345", schedule.getId(), commentResult);
            }
        }

        if (schedule.getType().equals("bulk_share")) {
            int shareResult = 0;
            shareResult = sharePost();
            if (shareResult == 0) ;
            {
                updateResultSchedules(deviceId, "12345", schedule.getId(), shareResult);
            }

        }

        sleep(ranInt(5000, 10000));
        // autoView(schedule);
    }

    public void updateResultSchedules(String device, String fbid, int sid, int result) {
        dataUpdateResultSchedules = "";
        APIService apiService = RetrofitClient.getClient().create(APIService.class);
        apiService.updateResultSchedules(device, fbid, sid, result).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dataUpdateResultSchedules = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void loadDataApi() {
        APIService apiService = RetrofitClient.getClient().create(APIService.class);
        apiService.getData().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                //Nếu ok thì về dây

                if (response.body().size() > 0) {
                    for (int i = 0; i < response.body().size(); i++) {
                        dataContact.addAll(response.body());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                //Nếu có lỗi thì về ây
                Log.d("ToanTQ", "error: " + t.getMessage());
            }
        });
        // Đến đây dataContact là nó không có giá trị gì

    }

    public void getSchedules(String device, String fbid) {
        APIService apiService = RetrofitClient.getClient().create(APIService.class);
        apiService.getSchedules(device, fbid).enqueue(new Callback<List<Schedules>>() {
            @Override
            public void onResponse(Call<List<Schedules>> call, Response<List<Schedules>> response) {
                //Nếu ok thì về dây
                dataSchedules.clear();
                dataSchedules.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Schedules>> call, Throwable t) {
                //Nếu có lỗi thì về ây
                Log.d("ToanTQ", "error: " + t.getMessage());
            }
        });

    }

    public void searchProduct() {

        try {
            UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));
            if (appViews1 == null) return;
            appViews1.scrollTextIntoView("Thích");
            Log.d("ToanTQ", "Scroll filter");
        } catch (UiObjectNotFoundException e) {
            Log.d("ToanTQ", "Exception: " + e.getMessage());
        }
        UiObject2 view = mDevice.findObject(By.text("Thích"));
        if (view != null) {
            view.click();
            Log.d("ToanTQ", "Click Like");

        }

    }

    private static int ranInt(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public int sharePost() {
        UiObject2 view = mDevice.findObject(By.text("Chia sẻ"));
        if (view != null) {
            view.click();
            Log.d("ToanTQ", "Click Chia sẻ");
            sleep(ranInt(1000, 3000));
            UiObject2 shareNow = mDevice.findObject(By.desc("CHIA SẺ NGAY"));
            shareNow.click();
            ;

        } else {
            try {
                UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));
                if (appViews1 == null) return 0;
                appViews1.scrollTextIntoView("Chia sẻ");
                Log.d("ToanTQ", "Scroll tìm nút share");
            } catch (UiObjectNotFoundException e) {
                Log.d("ToanTQ", "Exception: " + e.getMessage());
            }

            view = mDevice.findObject(By.text("Chia sẻ"));
            if (view != null) {
                view.click();
                Log.d("ToanTQ", "Click Chia sẻ");
                sleep(ranInt(1000, 3000));
                UiObject2 shareNow = mDevice.findObject(By.desc("CHIA SẺ NGAY"));
                shareNow.click();
                ;

            } else {
                return 0;
            }
        }

        return 1;
    }

    public int likePost() {

        sleep(ranInt(6000, 9000));
        UiObject2 view = mDevice.findObject(By.text("Thích"));

        Log.d("ToanTQ", "view Like: " + view);
        if (view != null) {
            sleep(ranInt(1000, 3000));
            view.click();
            Log.d("ToanTQ", "Click Like");
            return 1;
        } else {
            try {

                UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));
                if (appViews1 == null) return 0;
                appViews1.scrollTextIntoView("Thích");
                Log.d("ToanTQ", "Scroll tìm nút Like");
                view = mDevice.findObject(By.text("Thích"));

                if (view != null) {
                    view.click();
                    Log.d("ToanTQ", "Click Like 2");
                    return 1;
                }

            } catch (UiObjectNotFoundException e) {
                Log.d("ToanTQ", "Exception: " + e.getMessage());
            }
            Log.d("ToanTQ", "Đéo Like dc");
            return 0;
        }
    }

    public int ReportProfile() {

        sleep(ranInt(6000, 9000));
        UiObject2 view = mDevice.findObject(By.text("Thích"));

        Log.d("ToanTQ", "view Like: " + view);
        if (view != null) {
            sleep(ranInt(1000, 3000));
            view.click();
            Log.d("ToanTQ", "Click Like");
            return 1;
        } else {
            try {

                UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));
                if (appViews1 == null) return 0;
                appViews1.scrollTextIntoView("Thích");
                Log.d("ToanTQ", "Scroll tìm nút Like");
                view = mDevice.findObject(By.text("Thích"));

                if (view != null) {
                    view.click();
                    Log.d("ToanTQ", "Click Like 2");
                    return 1;
                }

            } catch (UiObjectNotFoundException e) {
                Log.d("ToanTQ", "Exception: " + e.getMessage());
            }
            Log.d("ToanTQ", "Đéo Like dc");
            return 0;
        }
    }

    public int commentPost(String messageComment) {

        if (messageComment.equals(null)) return 0;
        sleep(ranInt(1000, 3000));
        UiObject2 view = mDevice.findObject(By.text("Bình luận"));
        if (view != null) {
            view.click();
            Log.d("ToanTQ", "Click Bình luận");
            sleep(ranInt(1000, 3000));

            UiObject2 edtSearch = mDevice.findObject(By.text("Viết bình luận..."));
            if (edtSearch != null) {
                edtSearch.setText(messageComment);
            }
            sleep(ranInt(1000, 3000));

            UiObject2 edtSent = mDevice.findObject(By.desc("Gửi"));

            if (edtSent != null) {
                edtSent.click();
                Log.d("ToanTQ", "Click Gửi");
            }

        } else {
            Log.d("ToanTQ", "Scroll tim nut comment");
            try {
                UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));
                if (appViews1 == null) return 0;
                appViews1.scrollTextIntoView("Bình luận");
                Log.d("ToanTQ", "Scroll tìm nút comment");
            } catch (UiObjectNotFoundException e) {
                Log.d("ToanTQ", "Exception: " + e.getMessage());
            }

            view = mDevice.findObject(By.text("Bình luận"));
            if (view != null) {
                view.click();
                Log.d("ToanTQ", "Click Bình luận");
                sleep(ranInt(1000, 3000));

                UiObject2 edtSearch = mDevice.findObject(By.text("Viết bình luận..."));
                if (edtSearch != null) {
                    edtSearch.setText(messageComment);
                }
                sleep(ranInt(1000, 3000));

                UiObject2 edtSent = mDevice.findObject(By.desc("Gửi"));

                if (edtSent != null) {
                    edtSent.click();
                    Log.d("ToanTQ", "Click Gửi");
                }

                sleep(ranInt(1000, 3000));

            } else {
                return 0;
            }


        }
        return 1;

    }

    public static void startIntentFace(Context context, String uriContent) {
        Uri uri = Uri.parse(uriContent);
        if (uri == null) return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setPackage("com.facebook.katana");
        intent.setData(uri);

        context.startActivity(intent);

    }

    public void checkView() {
        Log.d("checkView", "processView: " + step);
        try {
            List<UiObject2> listView = mDevice.findObjects(By.enabled(true));
            Log.d("checkView", "----------------------View-------------------------");
            Log.d("checkView", "list: " + listView.size());
            for (int i = 0; i < listView.size(); i++) {
                UiObject2 view = listView.get(i);
                try {
                    Log.d("checkView", i + " class i thu: " + i);
                    Log.d("checkView", i + " getClassName: " + view.getClassName());
                    Log.d("checkView", i + " getResourceName: " + view.getResourceName());
                    Log.d("checkView", i + " getText class i thu: " + i + " " + view.getText());
                    Log.d("checkView", i + " getContentDescription: " + view.getContentDescription());
                    Log.d("checkView", "-----------------------------------------------\n");
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            Log.d("checkView", "Exception: " + e.getMessage());
            e.printStackTrace();
        }
        sleep(ranInt(10000, 11000));
        checkView();
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
        Log.d("ToanTQ", "sms: " + sms);
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
