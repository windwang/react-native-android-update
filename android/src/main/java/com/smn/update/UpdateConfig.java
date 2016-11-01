package com.smn.update;

import java.util.Map;

/**
 * Created by wwm on 2016-11-01.
 */

//"appName": "鸿校园",
//  "appDescription": "有鸿智慧校园",
//  "packageName": "com.smn.school",
//  "versionCode": "4",
//  "versionName": "1.0.4",
//  "forceUpdate": false,
//  "autoUpdate": false,
//  "apkUrl": "http://60.216.75.139:6101/hschool/app.apk",
//  "updateTips": {
//  "default": "update tips",
//  "en":  "update tips",
//  "zh":  "修复圈子崩溃问题",
//  "zh_CN": "修复圈子崩溃问题",
//  "zh_TW": "修复圈子崩溃问题",
//  "zh_HK": "修复圈子崩溃问题"
//
//  }
public class UpdateConfig {

  private UpdateInfo updateInfo;


  public UpdateInfo getUpdateInfo() {
    return updateInfo;
  }

  public void setUpdateInfo(UpdateInfo updateInfo) {
    this.updateInfo = updateInfo;
  }
}
