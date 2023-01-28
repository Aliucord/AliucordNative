package com.discord.sounds;

import com.discord.BuildConfig;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import android.content.Context;
import com.discord.logging.Log;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;

public final class SoundManagerModule extends ReactContextBaseJavaModule {
    private final SoundManager soundManager;

    public SoundManagerModule(ReactApplicationContext reactApplicationContext0) {
        super(reactApplicationContext0);
        if(reactApplicationContext0 == null)
            throw new NullPointerException("reactApplicationContext must not be null");
        this.soundManager = new SoundManager(reactApplicationContext0);
    }

    @Override  // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "DCDSoundManager";
    }

    @ReactMethod
    public final void pause(int v) {
        Log.i$default(Log.INSTANCE, "SoundManager", "Pause " + v, null, 4, null);
        this.soundManager.pause(v);
    }

    @ReactMethod
    public final void play(int v) {
        Log.i$default(Log.INSTANCE, "SoundManager", "Play " + v, null, 4, null);
        this.soundManager.play(v);
    }

    @ReactMethod
    public final void prepare(String s, String s1, int v, Callback callback0) {
        if(s == null)
            throw new NullPointerException("s must not be null");
        if(callback0 == null)
            throw new NullPointerException("callback0 must not be null");

        Log.i$default(Log.INSTANCE, "SoundManager", "Prepare " + s + " with " + v + ".", null, 4, null);
        int v1 = 5;
        if(s1 != null) {
            int hashCode = s1.hashCode();
            if(hashCode != 0x6B2E132) {
                switch(hashCode) {
                    case 0x11F69621: {
                        if(s1.equals("ring_tone")) {
                            v1 = 6;
                        }

                        break;
                    }
                    case 595233003: {
                        s1.equals("notification");
                    }
                }
            }
            else if(s1.equals("voice")) {
                v1 = 2;
            }
        }

        ReactApplicationContext reactApplicationContext0 = this.getReactApplicationContext();
        if(reactApplicationContext0 == null)
            throw new NullPointerException("reactApplicationContext must not be null");

        int v3 = SoundManagerModule.resolveRawResId$default(this, reactApplicationContext0, s, null, 2, null);
        com.discord.sounds.SoundManagerModule$prepare$1 soundManagerModule$prepare$10 = new com.discord.sounds.SoundManagerModule$prepare$1(callback0);
        this.soundManager.prepare(v, v1, v3, soundManagerModule$prepare$10);
    }

    @ReactMethod
    public final void release(int v) {
        Log.i$default(Log.INSTANCE, "SoundManager", "Release " + v, null, 4, null);
        this.soundManager.release(v);
    }

    private final int resolveRawResId(Context context0, String s, String s1) {
        // ALIUCORD CHANGED: dynamic package name lookup -> BuildConfig.APPLICATION_ID
        int v = context0.getResources().getIdentifier(s, s1, BuildConfig.APPLICATION_ID);
        if(v > 0) {
            return v;
        }

        throw new IllegalArgumentException("Trying to resolve unknown sound " + s);
    }

    static int resolveRawResId$default(SoundManagerModule soundManagerModule0, Context context0, String s, String s1, int v, Object object0) {
        if((v & 2) != 0) {
            s1 = "raw";
        }

        return soundManagerModule0.resolveRawResId(context0, s, s1);
    }

    @ReactMethod
    public final void setCurrentTime(int v, int v1) {
        Log.i$default(Log.INSTANCE, "SoundManager", "Set current time for " + v + " with value " + v1, null, 4, null);
        this.soundManager.setCurrentTime(v, v1);
    }

    @ReactMethod
    public final void setNumberOfLoops(int v, int v1) {
        Log.i$default(Log.INSTANCE, "SoundManager", "Set number of loops for " + v + " with value " + v1, null, 4, null);
        this.soundManager.setNumberOfLoops(v, v1);
    }

    @ReactMethod
    public final void setPan(int v, int v1) {
        Log.i$default(Log.INSTANCE, "SoundManager", "Set pan for " + v + " with value " + v1, null, 4, null);
        this.soundManager.setPan(v, ((float)v1));
    }

    @ReactMethod
    public final void setVolume(int v, float f) {
        Log.i$default(Log.INSTANCE, "SoundManager", "Set volume for " + v + " with value " + f, null, 4, null);
        this.soundManager.setVolume(v, f);
    }

    @ReactMethod
    public final void stop(int v) {
        Log.i$default(Log.INSTANCE, "SoundManager", "Stop " + v, null, 4, null);
        this.soundManager.stop(v);
    }
}
