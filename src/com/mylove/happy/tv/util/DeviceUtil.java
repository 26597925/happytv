 package com.mylove.happy.tv.util;
 
 import android.content.Context;
 import android.content.pm.ApplicationInfo;
 import android.content.pm.PackageInfo;
 import android.content.pm.PackageManager;
 import android.os.Build;
 import android.os.Bundle;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileReader;
 import java.io.IOException;
 
 public class DeviceUtil
 {
   public static String os;
   public static String kernel;
   public static String mac;
   public static String model;
   public static String rom;
   public static String channel;
 
   public static void getDeviceInfo(Context context)
   {
     try
     {
       BufferedReader reader = new BufferedReader(new FileReader("/proc/version"), 256);
       ApplicationInfo appi = context.getPackageManager()
         .getApplicationInfo(context.getPackageName(), 
         128);
       Bundle bundle = appi.metaData;
       channel = (String)bundle.get("CHANNEL");
       model = Build.MODEL;
       os = Build.VERSION.RELEASE;
       rom = Build.DISPLAY;
       kernel = reader.readLine();
       mac = getDeviceMac("/sys/class/net/eth0/address");
       if ((mac.equals("")) || (mac == null)) {
         mac = getDeviceMac("/proc/version");
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
 
   public static String getDeviceMac(String filePath) {
     File file = new File(filePath);
     if (!file.exists()) {
       return "";
     }
     String mode = null;
     try {
       BufferedReader in = new BufferedReader(new FileReader(filePath), 32);
       try {
         mode = in.readLine();
       } finally {
         in.close();
       }
       return mode; } catch (IOException e) {
     }
     return "";
   }
 
   public static String getVersionName(Context ctx)
   {
     PackageManager packageManager = ctx.getPackageManager();
     try
     {
       PackageInfo packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
       return packInfo.versionName;
     } catch (PackageManager.NameNotFoundException e) {
       e.printStackTrace();
     }return null;
   }
 }