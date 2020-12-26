package com.startup.shoppyauto.Actions;

import android.util.Log;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

import java.util.List;
import java.util.Random;

public class ActionFb {
    private static UiDevice mDevice;

  //  UiDevice mDevice = AutoLogin.mDevice;
    static int step = 0;

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
    public static int ranInt(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
