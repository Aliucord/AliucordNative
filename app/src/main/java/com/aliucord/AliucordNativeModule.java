/*
 * This file is part of Aliucord, an Android Discord client mod.
 * Copyright (c) 2021 Juby210 & Vendicated
 * Licensed under the Open Software License version 3.0
 */

package com.aliucord;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.ReactApplication;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class AliucordNativeModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private final ReactApplicationContext appContext;

    public AliucordNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        appContext = reactContext;
    }

    @Override
    public String getName() {
        return "AliucordNative";
    }

    @Override
    public Map<String, Object> getConstants() {
        return new HashMap<>() {{
            put("externalStorageDirectory", Environment.getExternalStorageDirectory().getAbsolutePath());
            put("codeCacheDirectory", appContext.getCodeCacheDir().getAbsolutePath());
        }};
    }

    @ReactMethod
    public void listNativeModules(Promise p) {
        var ret = Arguments.createMap();
        for (var module : appContext.getCatalystInstance().getNativeModules()) {
            var methods = Arguments.createArray();

            for (var method : module.getClass().getDeclaredMethods()) {
                if (method.getAnnotation(ReactMethod.class) != null) {
                    methods.pushString(method.toString());
                }
            }
            ret.putArray(module.getName(), methods);
        }
        p.resolve(ret);
    }

    private Promise permissionResult;
    private static final int PERMISSION_REQUEST_CODE = 9090;

    @SuppressLint("NewApi")
    @ReactMethod
    public void requestPermissions(Promise p) {
        if (checkPermissionsInternal()) {
            p.resolve(true);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            permissionResult = p;
            appContext.addActivityEventListener(this);

            var activity = getCurrentActivity();
            try {
                activity.startActivityForResult(
                        new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:" + appContext.getPackageName())),
                        PERMISSION_REQUEST_CODE
                );
            } catch (Exception e) {
                activity.startActivityForResult(
                        new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION),
                        PERMISSION_REQUEST_CODE
                );
            }
        } else {
            permissionResult = p;
            appContext.addActivityEventListener(this);

            getCurrentActivity().requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @ReactMethod
    public void checkPermissions(Promise p) {
        p.resolve(checkPermissionsInternal());
    }

    private boolean checkPermissionsInternal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return appContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    public void onActivityResult(Activity activity, int request, int result, Intent intent) {
        if (request != PERMISSION_REQUEST_CODE || permissionResult == null) return;
        permissionResult.resolve(checkPermissionsInternal());
        permissionResult = null;
        appContext.removeActivityEventListener(this);
    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    @ReactMethod
    public void download(String url, String path, Promise p) {
        try (
                var readableByteChannel = Channels.newChannel(new URL(url).openStream());
                var fileOutputStream = new FileOutputStream(path);
                var fileChannel = fileOutputStream.getChannel()
        ) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);

            p.resolve(null);
        } catch (Throwable e) {
            p.reject(e);
        }
    }

    @ReactMethod
    public void openDevTools() {
        Activity plainActivity = appContext.getCurrentActivity();
        if (plainActivity == null) return;

        ((ReactApplication) plainActivity.getApplication()).getReactNativeHost().getReactInstanceManager().showDevOptionsDialog();
    }
}
