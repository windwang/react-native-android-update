package com.smn.update;

import com.facebook.react.bridge.*;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by wwm on 2016-10-28.
 */

public class UpdateModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

  String url = null;
  UpdateConfig updateConfig;
  long downloadId;

  DownloadManager downloadManager;
  DownloadCompleteReceiver completeReceiver;

  public UpdateModule(ReactApplicationContext reactContext) {
    super(reactContext);

    reactContext.addLifecycleEventListener(this);
  }

  @Override
  public String getName() {
    return "RNUpdate";
  }

  /**
   * 初始化
   *
   * @param url
   * @param promise
   */
  @ReactMethod
  public void init(String url, final Promise promise) {
    if (this.url != null && !this.url.equalsIgnoreCase(url)) {
      this.updateConfig = null;
      if (this.downloadId > 0)
        this.downloadManager.remove(this.downloadId);
    }
    this.url = url;
    promise.resolve(url);
  }

  /**
   * 获取新版本的信息
   *
   * @param promise
   */
  @ReactMethod
  public void getNewVersion(final Promise promise) {
    boolean result = hasNewVersion();
    if (result) {
      promise.resolve(getUpdateInfo().toMap());
    } else {
      promise.resolve(null);
    }

  }

  @ReactMethod
  public void update() {
    if (hasNewVersion()) {
      doUpdate();
    }
  }

  boolean hasNewVersion() {
    UpdateInfo updateInfo = getUpdateInfo();
    PackageInfo pinfo = null;
    try {
      pinfo = getReactApplicationContext().getPackageManager().getPackageInfo(getReactApplicationContext().getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      Log.e("Update", "getPackageInfo failure", e);
      return false;
    }
    return updateInfo.getVersionCode() > pinfo.versionCode;
  }


  private UpdateInfo getUpdateInfo() {
    if (this.updateConfig != null) return updateConfig.getUpdateInfo();
    try {
      HttpRequest request = HttpRequest.get(url);
      request.acceptJson();
      String body = request.body();
      if (request.ok()) {
        Gson gson = new Gson();
        this.updateConfig = gson.fromJson(body, UpdateConfig.class);
        return this.updateConfig.getUpdateInfo();
      }
    } catch (Exception ex) {
      Log.e("Update", "getUpdateInfo failure", ex);
    }


    return null;
  }

  private void doUpdate() {
    Log.d("Update", "check update：" + url);
    try {

      UpdateInfo updateInfo = getUpdateInfo();
      if (completeReceiver == null) completeReceiver = new DownloadCompleteReceiver();
      getReactApplicationContext().registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
      downloadManager = (DownloadManager) getReactApplicationContext().getSystemService(DOWNLOAD_SERVICE);
      Log.d("Update", "downloadurl:" + updateInfo.getApkUrl());
      DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateInfo.getApkUrl()));
      //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
      request.setDestinationInExternalPublicDir("Download", updateInfo.getPackageName() + ".apk");
      request.setMimeType("application/vnd.android.package-archive");
      request.setTitle(updateInfo.getAppName());
      downloadId = downloadManager.enqueue(request);
    } catch (Exception ex) {
      Log.e("Update", "check update failure", ex);
      Log.e("Update", ex.getMessage());
      Log.e("Update", ex.toString());
    }
  }


  @Override
  public void onHostResume() {

  }

  @Override
  public void onHostPause() {

  }

  @Override
  public void onHostDestroy() {
    unRegisterReceiver();


  }

  private void unRegisterReceiver() {
    try{
      if(completeReceiver!=null){
        getReactApplicationContext().unregisterReceiver(completeReceiver);
        completeReceiver=null;

      }
    }catch (Exception ex){
      Log.e("Update","unregisterReceiver failure",ex);
    }
  }

  private void promptInstall(Uri data) {

    Intent promptInstall = new Intent(Intent.ACTION_VIEW)
      .setDataAndType(data, "application/vnd.android.package-archive");
    // FLAG_ACTIVITY_NEW_TASK 可以保证安装成功时可以正常打开 app
    promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    getReactApplicationContext().startActivity(promptInstall);
  }

  class DownloadCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      long downloadCompletedId = intent.getLongExtra(
        DownloadManager.EXTRA_DOWNLOAD_ID, 0);
      // 检查是否是自己的下载队列 id, 有可能是其他应用的
      if (downloadId != downloadCompletedId) {
        return;
      }
      DownloadManager.Query query = new DownloadManager.Query();
      query.setFilterById(downloadId);
      Cursor c = downloadManager.query(query);
      if (c.moveToFirst()) {
        int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
        // 下载失败也会返回这个广播，所以要判断下是否真的下载成功
        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
          // 获取下载好的 apk 路径
          String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
          unRegisterReceiver();
          // 提示用户安装
          promptInstall(Uri.parse("file://" + uriString));
        }
      }
    }
  }
}
