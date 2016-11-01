package com.smn.update;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

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

/**
 * 更新信息
 */
public class UpdateInfo {
  private String appName;
  private String appDescription;
  private String packageName;
  private Integer versionCode;
  private String versionName;
  private Boolean forceUpdate;
  private Boolean autoUpdate;
  private String apkUrl;
  private Map<String, String> updateTips;

  public String getAppDescription() {
    return appDescription;
  }

  public void setAppDescription(String appDescription) {
    this.appDescription = appDescription;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public Integer getVersionCode() {
    return versionCode;
  }

  public void setVersionCode(Integer versionCode) {
    this.versionCode = versionCode;
  }

  public String getVersionName() {
    return versionName;
  }

  public void setVersionName(String versionName) {
    this.versionName = versionName;
  }

  public Boolean getForceUpdate() {
    return forceUpdate;
  }

  public void setForceUpdate(Boolean forceUpdate) {
    this.forceUpdate = forceUpdate;
  }

  public Boolean getAutoUpdate() {
    return autoUpdate;
  }

  public void setAutoUpdate(Boolean autoUpdate) {
    this.autoUpdate = autoUpdate;
  }

  public String getApkUrl() {
    return apkUrl;
  }

  public void setApkUrl(String apkUrl) {
    this.apkUrl = apkUrl;
  }

  public Map<String, String> getUpdateTips() {
    return updateTips;
  }

  public void setUpdateTips(Map<String, String> updateTips) {
    this.updateTips = updateTips;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getDefaultUpdateTip() {
    if (this.updateTips.containsKey("zh"))
      return this.updateTips.get("zh");
    if (this.updateTips.containsKey("zh_CN"))
      return this.updateTips.get("zh");
    return this.updateTips.get("default");
  }

  public WritableMap toMap() {
    WritableMap map = Arguments.createMap();
    map.putString("appName", getAppName());
    map.putString("appDescription", getAppDescription());
    map.putInt("versionCode", getVersionCode());
    map.putString("versionName", getVersionName());
    map.putString("updateTip", getDefaultUpdateTip());
    map.putString("apkUrl", getApkUrl());
    return map;
  }
}
