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
import androidx.test.uiautomator.v18.BuildConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.startup.shoppyauto.Model.DataSharePre;
import com.startup.shoppyauto.Retrofit2.APIService;
import com.startup.shoppyauto.Retrofit2.Accounts;
import com.startup.shoppyauto.Retrofit2.Contact;
import com.startup.shoppyauto.Retrofit2.RetrofitClient;
import com.startup.shoppyauto.Retrofit2.Schedules;
import com.startup.shoppyauto.System.InstallSupport;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;


@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AutoLogin {
    String TAG = "ToanTQ";
    public static UiDevice mDevice;

    List<Contact> dataContact = new ArrayList<>();
    List<Schedules> dataSchedules = new ArrayList<>();
    List<Accounts> dataAccounts = new ArrayList<>();
    String faCode;
    String dataUpdateResultSchedules;
    static int step = 0;
    String apiUrl = "http://tangtuongtac.net";
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
        //  Log.d("ToanTQ", "Đây là apk test");
        runAllTime();
        // checkView();
    }

    private static Retrofit retrofit = null;

    public void runAllTime() {

        // String deviceId =  Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceId = DataSharePre.getDataSharedString(getApplicationContext(), "deviceId");
        String fbid = DataSharePre.getDataSharedString(getApplicationContext(), "fbid");

        ArrayList<String> accountsInDevice = new ArrayList<String>();

        getSchedules(deviceId, fbid);
        sleep(2000);

        if (dataSchedules.size() != 0) {
            String checkAuto = DataSharePre.getDataSharedString(getApplicationContext(), "auto");
            if (checkAuto.equals("yes")) {
                Log.d("ToanTQ", "auto: " + checkAuto);
                Log.d("ToanTQ", "Dữ liệu schedules: " + dataSchedules.size());
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
                sleep(ranInt(5000, 10000));
                Log.d("ToanTQ", "tuong tac lap lai ");
                runAllTime();
            } else {
                Log.d("ToanTQ", "auto: " + checkAuto);

            }
        } else {
            int accountIndex = DataSharePre.getDataSharedInt(getApplicationContext(), "accountIndex");

            dataAccounts = getFbAccounts(deviceId);
            if (dataAccounts.size() > 0) {
                Log.d("ToanTQ", "Dữ liệu account từ sv: " + dataAccounts.size());
                sleep(ranInt(3000, 5000));
                loginFb(dataAccounts.get(accountIndex));
                sleep(ranInt(3000, 5000));
                //    checkView();
            }
        }
        runAllTime();
    }

    public void loginFb(Accounts account) {
        // Nếu chưa login tk, hoặc đang login thì logout
        Log.d("ToanTQ", "Login facebook ");
        // Logout
        Log.d("ToanTQ", "Account facebook: " + account.getUsername() + "|" + account.getPassword() + "|" + account.getTwofa());
        startIntentFace(getApplicationContext(), "fb://root");
        sleep(ranInt(4000, 5000));
        UiObject2 menu = mDevice.findObject(By.desc("Menu, Tab 6/6"));
        if (menu == null) {
            menu = mDevice.findObject(By.desc("Menu, Tab 5/5"));
        }
        if (menu == null) {
            menu = mDevice.findObject(By.desc("Menu, Tab 4/4"));
        }
      //  checkView();
        if (menu != null) {
            Log.d("Toan", "Click menu ");

            menu.click();
        } else {
            Log.d("Toan", "Không tìm thấy menu ");
        }

        sleep(ranInt(2000, 3000));
        try {
            UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));
            if (appViews1 == null) return;
            appViews1.scrollDescriptionIntoView("Đăng xuất, Nút 1/1");
            Log.d("ToanTQ", "Scroll tìm nút đăng xuất");
        } catch (UiObjectNotFoundException e) {
            Log.d("ToanTQ", "Exception: " + e.getMessage());
        }
        sleep(ranInt(2000, 3000));
        UiObject2 logout = mDevice.findObject(By.desc("Đăng xuất, Nút 1/1"));
        if (logout != null) {
            logout.click();
            Log.d("ToanTQ", "Click Đăng xuất");
        }

        sleep(ranInt(7000, 10000));
        // Check logout
        UiObject2 checkLogout = mDevice.findObject(By.text("Đăng nhập bằng tài khoản khác"));
        if (checkLogout != null) {
            Log.d("ToanTQ", "Click nút đăng nhập = tài khoản khác ");
            checkLogout.click();
        } else {
            Log.d("Toan", "Không tìm thấy Đăng nhập bằng tài khoản khác ");
        }
        sleep(ranInt(3000, 5000));
        // Điền username
        UiObject2 username = mDevice.findObject(By.text("Số điện thoại hoặc email"));
        if (username != null) {
            Log.d("ToanTQ", "Điền username");
            username.setText(account.getUsername());
        }
        sleep(ranInt(1000, 2000));
        // Điền pass
        UiObject2 password = mDevice.findObject(By.desc("Mật khẩu"));
        if (password != null) {
            Log.d("ToanTQ", "Điền pass");
            password.setText(account.getPassword());
        }
        sleep(ranInt(1000, 2000));
        // Click dang nhap
        checkClick();
        sleep(ranInt(7000, 10000));
        // Click OK
        checkClick();
        sleep(ranInt(3000, 5000));
        // Check login 2fa
        UiObject2 check2Fa = mDevice.findObject(By.text("Mã đăng nhập"));
        if (check2Fa != null) {
            faCode = getCode(account.getTwofa());
            Log.d("ToanTQ", "mã 2fa: " + faCode);

            if (check2Fa != null) {
                check2Fa.setText(faCode);
                UiObject2 checkContinue = mDevice.findObject(By.desc("Tiếp tục"));
                if (checkContinue != null) {
                    Log.d("ToanTQ", "Click tiếp tục");
                    sleep(ranInt(2000, 3000));
                    checkContinue.click();
                }
            }
        }
        sleep(ranInt(5000, 7000));
        checkClick();
        //
        // check login thành công

        int accountIndex = DataSharePre.getDataSharedInt(getApplicationContext(), "accountIndex");
        accountIndex +=1;
        if (accountIndex >= dataAccounts.size()) {
            accountIndex = 0;
        }
        DataSharePre.saveDataSharedInt(getApplicationContext(), "accountIndex", accountIndex);
        DataSharePre.saveDataSharedString(getApplicationContext(), "fbid", account.getFbid());
    }

    public void checkClick() {
        // Click login
        UiObject2 clickLogin = mDevice.findObject(By.desc("Đăng nhập"));
        if (clickLogin != null) {
            clickLogin.click();
            Log.d("ToanTQ", "Click nút Đăng nhập");
            return;
        }

        UiObject2 view = mDevice.findObject(By.text("LÚC KHÁC"));
        if (view != null) {
            Log.d("Toan", "Click [LÚC KHÁC]");
            view.click();
            return;
        }

        view = mDevice.findObject(By.text("Next"));
        if (view != null) {
            Log.d("Toan", "Click [Next]");
            view.click();
            return;
        }

        view = mDevice.findObject(By.desc("close button"));
        if (view != null) {
            Log.d("Toan", "Click [880|480]");
            mDevice.click(880, 480);
            sleep(2000);
            Log.d("Toan", "Click [close button]");
            view.click();
            return;
        }

        view = mDevice.findObject(By.textStartsWith("Is your email"));
        if (view != null) {
            //   emailConfirm = view.getText().replace("Is your email", "").replace("?", "").trim();
        }


        view = mDevice.findObject(By.textContains("THIS IS MY EMAIL"));
        if (view != null) {
            view.click();
            Log.d("Toan", "Click [THIS IS MY EMAIL]");
            return;
        }

        view = mDevice.findObject(By.desc("Login click"));
        if (view == null) view = mDevice.findObject(By.desc("Login"));

        if (view != null) {
            view.click();
            Log.d("Toan", "Click [Login]");
            return;
        }

        view = mDevice.findObject(By.text("Bỏ qua"));
        if (view == null) view = mDevice.findObject(By.text("BỎ QUA"));
        if (view != null) {
            view.click();
            Log.d("Toan", "Click [Bỏ qua]");
            return;
        }

        view = mDevice.findObject(By.text("None of the above"));
        if (view != null) {
            view.click();
            Log.d("Toan", "Click [None of the above]");
            return;
        }

        view = mDevice.findObject(By.text("Không bao giờ"));
        if (view != null) {
            view.click();
            Log.d("Toan", "Click [Never]");
            return;
        }

        view = mDevice.findObject(By.text("OK"));
        if (view != null) {
            view.click();
            Log.d("Toan", "Click [OK]");
            return;
        }

        view = mDevice.findObject(By.desc("I ACCEPT"));
        if (view != null) {
            view.click();
            Log.d("Toan", "Click [I ACCEPT]");
            return;
        }

        view = mDevice.findObject(By.desc("RETURN TO NEWS FEED"));
        if (view != null) {
            view.click();
            Log.d("Toan", "Click [RETURN TO NEWS FEED]");
            return;
        }
        view = mDevice.findObject(By.desc("Tiếp tục"));
        if (view == null) view = mDevice.findObject(By.desc("TIẾP TỤC"));
        if (view != null) {
            view.click();
            Log.d("Toan", "Click [Continue]");
            return;
        }
    }

    public void autoView(Schedules schedule) {
        String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("ToanTQ", "start auto Facebook: ");

        Log.d("ToanTQ", "Check version app: " + schedule.getVersionName());

        // update apk

        String version_Name = com.startup.shoppyauto.BuildConfig.VERSION_NAME;
        Log.d("ToanTQ", "Check version name: " + version_Name);

        // update nếu version name app !== version name server

        if (!schedule.getVersionName().equals(version_Name)) {
            Log.d("ToanTQ", "start auto UPDATE TEST APK: ");
            new InstallSupport(getApplicationContext()).startUpdateTestApk();
            sleep(ranInt(10000, 15000));

            UiObject2 clickInstall = mDevice.findObject(By.text("Cài đặt"));

            if (clickInstall != null) {
                clickInstall.click();
                Log.d("ToanTQ", "Click cài đặt");
                sleep(ranInt(15000, 20000));
                UiObject2 clickXong = mDevice.findObject(By.text("Xong"));
                clickXong.click();
                Log.d("ToanTQ", "Click xong");
                sleep(ranInt(5000, 7000));
                oppenMyApp();
                Log.d("ToanTQ", "open app");
                sleep(ranInt(5000, 7000));
                UiObject2 clickStart = mDevice.findObject(By.text("START AUTO"));
                clickStart.click();

            }

        /*    Log.d("ToanTQ", "start auto UPDATE APP APK: ");
            new InstallApp(getApplicationContext()).startUpdateAppApk();
            sleep(ranInt(10000, 15000));

            clickInstall = mDevice.findObject(By.text("Cài đặt"));

            if (clickInstall != null) {
                clickInstall.click();
                Log.d("ToanTQ", "Click cài đặt");
                sleep(ranInt(15000, 20000));
                UiObject2 clickXong = mDevice.findObject(By.text("Xong"));
                clickXong.click();
                Log.d("ToanTQ", "Click xong");
                sleep(ranInt(5000, 7000));
                oppenMyApp();
                Log.d("ToanTQ", "open app");
                sleep(ranInt(5000, 7000));
                UiObject2 clickStart = mDevice.findObject(By.text("START AUTO"));
                clickStart.click();

            } */

        }

        int result = 0;
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
        //  linkPost = "fb://profile/hoa.bingan.31337194";
        //  linkPost = "https://fb.com/profile";
        Log.d("ToanTQ", "Link post: " + linkPost);

        startIntentFace(getApplicationContext(), linkPost);

        if (schedule.getType().equals("report_profile")) {
            Log.d(TAG, "Bat dau report profile: ");
            result =  likePost();
            if (result == 1) {
                // updateResultSchedules(deviceId, deviceId, schedule.getId(), result);
            }
            sleep(ranInt(5000, 10000));

            Log.d(TAG, "Dữ liệu gửi lên sv : " + dataUpdateResultSchedules);
        }

        if (schedule.getType().equals("bulk_like")) {
            Log.d(TAG, "Bat dau like: ");
            result =  likePost();
            if (result == 1) {
                updateResultSchedules(deviceId, "12345", schedule.getId(), result);
            }
            sleep(ranInt(5000, 10000));

            Log.d(TAG, "Dữ liệu gửi lên sv : " + dataUpdateResultSchedules);
        }

        if (schedule.getType().equals("seeding")) {
            Log.d(TAG, "Bat dau seeding: ");

            result = 0;
            String[] ArrayMessenger = new String[0];

            // Mảng các comment
            if (dataSchedules.get(0).getMessage().toString().split("\n").length > 1) {
                ArrayMessenger = dataSchedules.get(0).getMessage().toString().split("\n");
                // Lấy ngẫu nhiên 1 comment
                Log.d(TAG, "Tin nhan seeding: " + ArrayMessenger[ranInt(0, ArrayMessenger.length - 1)]);
                result =  commentPost(ArrayMessenger[ranInt(0, ArrayMessenger.length - 1)]);
            } else {

                //   Log.d(TAG, "Tin nhan seeding: " + ArrayMessenger[ranInt(0, ArrayMessenger.length - 1)]);
                result =  commentPost(dataSchedules.get(0).getMessage().toString());
            }

            if (result == 0) {
                updateResultSchedules(deviceId, "12345", schedule.getId(), result);
            }
        }

        if (schedule.getType().equals("bulk_share")) {
            int shareResult = 0;
            shareResult =  sharePost();
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
        APIService apiService = RetrofitClient.getClient(apiUrl).create(APIService.class);
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
        APIService apiService = RetrofitClient.getClient(apiUrl).create(APIService.class);
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
        String url = "http://tangtuongtac.net/";
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService apiService = retrofit.create(APIService.class);
        Response response = null;
        try {
            response = apiService.getSchedules(device, fbid).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            dataSchedules.clear();
            dataSchedules.addAll((Collection<? extends Schedules>) response.body());
        }
    }
    
    public List<Accounts> getFbAccounts(String device) {
        String url = "http://tangtuongtac.net/";
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService apiService = retrofit.create(APIService.class);
        Response response = null;
        try {
            Log.d("ToanTQ", "Get Account");
            response = apiService.getAccounts(device).execute();

        } catch (IOException e) {
            Log.d("ToanTQ", "Error Get Account: " + e.toString());
            e.printStackTrace();
        }
        Log.d("ToanTQ", "Get Account" + response.toString());
        List<Accounts> Accounts = (List<Accounts>) response.body();

        return Accounts;
    }

    public String getCode(String key) {
        //    faCode = "";

        Log.d("ToanTQ", "Start get code 2fa");
        String facodeUrl = "http://facode.tranquoctoan.com:8000/";
        Log.d("ToanTQ", "Secret key: " + key);
        String facode = null;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(facodeUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // return retrofit;
        APIService apiService = retrofit.create(APIService.class);

        Response response = null;
        try {
            response = apiService.get2FaCode(key).execute();
            facode = (String) response.body();
            //   Log.d("ToanTQ", "2fa key: " + facode);
        } catch (IOException e) {
            Log.d("ToanTQ", "exception get facode: " + e.toString());
            e.printStackTrace();
        }

        return facode;
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

    public static void checkView() {
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

    public int shareLive() {
        UiObject2 view = mDevice.findObject(By.text("Chia sẻ"));
        if (view != null) {
            view.click();
            Log.d("ToanTQ", "Click Chia sẻ");
            sleep(ranInt(1000, 3000));
            UiObject2 shareNow = mDevice.findObject(By.desc("CHIA SẺ NGAY"));
            shareNow.click();
        } else {
            try {
                UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));
                if (appViews1 == null) return 0;
                appViews1.scrollTextIntoView("Chia sẻ");
                sleep(ranInt(2000, 3000));
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
                Log.d("ToanTQ", "Không tìm thấy nút Chia sẻ");
                return 0;
            }
        }
        return 1;
    }

    public int likeLive() {

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
                sleep(ranInt(2000, 3000));
                UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));

                if (appViews1 == null) return 0;

                appViews1.scrollTextIntoView("Thích");
                sleep(ranInt(2000, 3000));
                Log.d("ToanTQ", "Scroll tìm nút Like");
                view = mDevice.findObject(By.text("Thích"));

                if (view != null) {
                    view.click();
                    Log.d("ToanTQ", "Click Like 2");
                    return 1;
                } else {
                    Log.d("ToanTQ", "Không tìm thấy nút like");
                }

            } catch (UiObjectNotFoundException e) {
                Log.d("ToanTQ", "Exception: " + e.getMessage());
            }
            Log.d("ToanTQ", "Đéo Like dc");
            return 0;
        }
    }

    public int commentLive(String messageComment) {

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
                sleep(ranInt(2000, 3000));
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
                Log.d("ToanTQ", "Không tìm thấy nút comment");
                return 0;
            }
        }
        return 1;

    }

    public static int sharePost() {
        UiObject2 view = mDevice.findObject(By.text("Chia sẻ"));
        if (view != null) {
            view.click();
            Log.d("ToanTQ", "Click Chia sẻ");
            sleep(ranInt(1000, 3000));
            UiObject2 shareNow = mDevice.findObject(By.desc("CHIA SẺ NGAY"));
            shareNow.click();
        } else {
            try {
                UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));
                if (appViews1 == null) return 0;
                appViews1.scrollTextIntoView("Chia sẻ");
                sleep(ranInt(2000, 3000));
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
                Log.d("ToanTQ", "Không tìm thấy nút Chia sẻ");
                return 0;
            }
        }
        return 1;
    }

    public static int likePost() {

        sleep(ranInt(4000, 7000));
        //  AutoLogin.checkView();
        UiObject2 view = mDevice.findObject(By.text("Thích"));

        Log.d("ToanTQ", "view Like: " + view);
        if (view != null) {
            sleep(ranInt(1000, 3000));
            view.click();
            Log.d("ToanTQ", "Click Like");
            return 1;
        } else {
            try {
                sleep(ranInt(2000, 3000));
                UiScrollable appViews1 = new UiScrollable(new UiSelector().scrollable(true));

                if (appViews1 == null) return 0;

                appViews1.scrollTextIntoView("Thích");
                sleep(ranInt(2000, 3000));
                Log.d("ToanTQ", "Scroll tìm nút Like");
                view = mDevice.findObject(By.text("Thích"));

                if (view != null) {
                    view.click();
                    Log.d("ToanTQ", "Click Like 2");
                    return 1;
                } else {
                    Log.d("ToanTQ", "Không tìm thấy nút like");
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
        UiObject2 view = mDevice.findObject(By.desc("Khác"));

        Log.d("ToanTQ", "Report profile: " + view);
        if (view != null) {
            sleep(ranInt(1000, 3000));
            view.click();

            Log.d("ToanTQ", "Report");

            UiObject2 view2 = mDevice.findObject(By.desc("Khác"));
            // checkView();
            return 1;

        } else {
            return 0;
        }
    }

    public static int commentPost(String messageComment) {

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
                sleep(ranInt(2000, 3000));
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
                Log.d("ToanTQ", "Không tìm thấy nút comment");
                return 0;
            }
        }
        return 1;

    }


    public void sleep() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }
    }
    public static void sleep(long time) {
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
        //    .getLaunchIntentForPackage("com.android.chrome");
        if (intent == null) {
            oppenMainActivity("Không có app facebook");
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }

    public static int ranInt(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    public void oppenMyApp() {
        mDevice.pressHome();
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage("com.startup.shoppyauto");
        //    .getLaunchIntentForPackage("com.android.chrome");
        if (intent == null) {
            oppenMainActivity("Không có my app");
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
