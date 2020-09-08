/*package com.startup.shoppyauto.Actions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.startup.shoppyauto.BuildConfig;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import static androidx.core.content.ContextCompat.startActivity;

private class DownloadFileAsyn extends AsyncTask<Void, String, Void> {
    private ProgressDialog mProgressDialog;

    @Override
    protected Void doInBackground(Void... params) {
        int count;
        try {
            String urlUpdateApp = "https://tangtuongtac.net/apk/app-debug-androidTest.apk";
            URL url = new URL(urlUpdateApp);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            int lenghtOfFile = conexion.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(getFileName());
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        openFile(getFileName());
        mProgressDialog.dismiss();
        Log.d("SonLv", "DownloadDone ");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog();
        mProgressDialog.setMessage("Đang tải xuống...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        mProgressDialog.setProgress(Integer.parseInt(values[0]));
    }
}

    public String getFileName() {
        return Environment.getExternalStorageDirectory() + "/" + BuildConfig.APPLICATION_ID + ".tmp";
    }

    protected void openFile(String fileName) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            install.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
            startActivity(install);
        } catch (Exception e) {
            install.setDataAndType(FileProvider.getUriForFile(this, getPackageName(), new File(fileName)), "application/vnd.android.package-archive");
            startActivity(install);
        }

        Log.d("SonLv", fileName);
    }

 */