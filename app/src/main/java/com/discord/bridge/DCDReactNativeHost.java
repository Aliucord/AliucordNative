package com.discord.bridge;

import android.app.Application;

import com.aliucord.AliucordNativePackage;
import com.discord.BuildConfig;
import com.discord.bundle_updater.BundleUpdater;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.swmansion.reanimated.ReanimatedJSIModulePackage;
import java.io.File;
import java.util.ArrayList;

// Copy pasted from jadx decompile
public final class DCDReactNativeHost extends ReactNativeHost {
    public DCDReactNativeHost(Application application) {
        super(application);
    }

    @Override // com.facebook.react.ReactNativeHost
    protected String getJSBundleFile() {
        File bundleLocation = BundleUpdater.Companion.instance().getBundleLocation();
        if (bundleLocation == null) {
            return null;
        }
        return bundleLocation.getAbsolutePath();
    }

    @Override // com.facebook.react.ReactNativeHost
    protected String getJSMainModuleName() {
        return BuildConfig.MAIN_MODULE;
    }

    @Override // com.facebook.react.ReactNativeHost
    public boolean getUseDeveloperSupport() {
        return true;
    }

    @Override // com.facebook.react.ReactNativeHost
    protected ReanimatedJSIModulePackage getJSIModulePackage() {
        return new ReanimatedJSIModulePackage();
    }

    @Override // com.facebook.react.ReactNativeHost
    protected ArrayList<ReactPackage> getPackages() {
        var packages = new DCDPackageList(this).getPackages();
        packages.add(new AliucordNativePackage());
        return packages;
    }
}