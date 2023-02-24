package com.discord.sounds;

import android.content.Context;
import android.webkit.URLUtil;
import com.discord.BuildConfig;
import com.discord.logging.Log;
import com.discord.sounds.utils.SoundExtensionsKt;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.io.File;
import kotlin.Unit;
import kotlin.jvm.internal.q;

public final class SoundManagerModule extends ReactContextBaseJavaModule {
    private final SoundManager soundManager;

    public SoundManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        q.g(reactContext, "reactContext");
        this.soundManager = new SoundManager(reactContext);
    }

    private final int resolveRawResId(Context context, String str, String str2) {
	// ALIUCORD CHANGED: dynamic package name lookup -> BuildConfig.APPLICATION_ID
        int identifier = context.getResources().getIdentifier(str, str2, BuildConfig.APPLICATION_ID);
        if (identifier > 0) {
            return identifier;
        }
        throw new IllegalArgumentException("Trying to resolve unknown sound " + str);
    }

    static /* synthetic */ int resolveRawResId$default(SoundManagerModule soundManagerModule, Context context, String str, String str2, int i10, Object obj) {
        if ((i10 & 2) != 0) {
            str2 = "raw";
        }
        return soundManagerModule.resolveRawResId(context, str, str2);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "DCDSoundManager";
    }

    @ReactMethod
    public final void pause(int i10) {
        Log log = Log.INSTANCE;
        String logTag = SoundManager.Companion.getLogTag();
        Log.i$default(log, logTag, "Pause " + i10, null, 4, null);
        this.soundManager.pause(i10);
    }

    @ReactMethod
    public final void play(int i10) {
        Log log = Log.INSTANCE;
        String logTag = SoundManager.Companion.getLogTag();
        Log.i$default(log, logTag, "Play " + i10, null, 4, null);
        this.soundManager.play(i10);
    }

    @ReactMethod
    public final void prepare(String fileName, String str, int i10, Callback callback) {
        Integer valueOf;
        String str2;
        q.g(fileName, "fileName");
        q.g(callback, "callback");
        Log.i$default(Log.INSTANCE, SoundManager.Companion.getLogTag(), "Prepare " + fileName + " with " + i10 + ".", null, 4, null);
        int i11 = 5;
        if (str != null) {
            int hashCode = str.hashCode();
            if (hashCode != 112386354) {
                if (hashCode != 301372961) {
                    if (hashCode == 595233003) {
                        str.equals("notification");
                    }
                } else if (str.equals("ring_tone")) {
                    i11 = 6;
                }
            } else if (str.equals("voice")) {
                i11 = 2;
            }
        }
        int i12 = i11;
        if (URLUtil.isValidUrl(fileName)) {
            String remoteSoundFilename = SoundExtensionsKt.getRemoteSoundFilename(fileName);
            ReactApplicationContext reactApplicationContext = getReactApplicationContext();
            q.f(reactApplicationContext, "reactApplicationContext");
            File file = new File(SoundExtensionsKt.getSoundsCacheDirectory(reactApplicationContext), remoteSoundFilename);
            if (!file.exists()) {
                ReactApplicationContext reactApplicationContext2 = getReactApplicationContext();
                q.f(reactApplicationContext2, "reactApplicationContext");
                SoundExtensionsKt.fetchSound(reactApplicationContext2, fileName);
            } else {
                file.setLastModified(System.currentTimeMillis());
            }
            str2 = file.getAbsolutePath();
            valueOf = null;
        } else {
            ReactApplicationContext reactApplicationContext3 = getReactApplicationContext();
            q.f(reactApplicationContext3, "reactApplicationContext");
            valueOf = Integer.valueOf(resolveRawResId$default(this, reactApplicationContext3, fileName, null, 2, null));
            str2 = null;
        }
        this.soundManager.prepare(i10, i12, valueOf, str2, new SoundManagerModule$prepare$1(callback));
    }

    @ReactMethod
    public final void release(int i10) {
        Log log = Log.INSTANCE;
        String logTag = SoundManager.Companion.getLogTag();
        Log.i$default(log, logTag, "Release " + i10, null, 4, null);
        this.soundManager.release(i10);
    }

    @ReactMethod
    public final void setCurrentTime(int i10, int i11) {
        Log log = Log.INSTANCE;
        String logTag = SoundManager.Companion.getLogTag();
        Log.i$default(log, logTag, "Set current time for " + i10 + " with value " + i11, null, 4, null);
        this.soundManager.setCurrentTime(i10, i11);
    }

    @ReactMethod
    public final void setNumberOfLoops(int i10, int i11) {
        Log log = Log.INSTANCE;
        String logTag = SoundManager.Companion.getLogTag();
        Log.i$default(log, logTag, "Set number of loops for " + i10 + " with value " + i11, null, 4, null);
        this.soundManager.setNumberOfLoops(i10, i11);
    }

    @ReactMethod
    public final void setPan(int i10, int i11) {
        Log log = Log.INSTANCE;
        String logTag = SoundManager.Companion.getLogTag();
        Log.i$default(log, logTag, "Set pan for " + i10 + " with value " + i11, null, 4, null);
        this.soundManager.setPan(i10, (float) i11);
    }

    @ReactMethod
    public final void setVolume(int i10, float f10) {
        Log log = Log.INSTANCE;
        String logTag = SoundManager.Companion.getLogTag();
        Log.i$default(log, logTag, "Set volume for " + i10 + " with value " + f10, null, 4, null);
        this.soundManager.setVolume(i10, f10);
    }

    @ReactMethod
    public final void stop(int i10) {
        Log log = Log.INSTANCE;
        String logTag = SoundManager.Companion.getLogTag();
        Log.i$default(log, logTag, "Stop " + i10, null, 4, null);
        this.soundManager.stop(i10);
    }
}