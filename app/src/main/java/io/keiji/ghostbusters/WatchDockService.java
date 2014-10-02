package io.keiji.ghostbusters;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class WatchDockService extends Service {
    public static final String TAG = WatchDockService.class.getSimpleName();

    public static final int ZERO = 0;

    private static final WindowManager.LayoutParams SYSTEM_ALERT_PARAMS
            = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

    private static final IntentFilter INTENT_FILTER
            = new IntentFilter(Intent.ACTION_DOCK_EVENT);

    private View mView;
    private WindowManager mWindowManager;

    private int mBrightness = -1;

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int dockState = intent.getIntExtra(Intent.EXTRA_DOCK_STATE, -1);
            boolean isDocked = dockState != Intent.EXTRA_DOCK_STATE_UNDOCKED;

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "dockState = " + dockState);
            }

            if (isDocked) {
                try {
                    mBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "now brightness = " + mBrightness);
                    }
                } catch (Settings.SettingNotFoundException e) {
                    Log.e(TAG, "SettingNotFoundException", e);
                }

                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, ZERO);

                mWindowManager.addView(mView, SYSTEM_ALERT_PARAMS);
            } else if (mView.isShown()) {
                mWindowManager.removeViewImmediate(mView);

                if (mBrightness != -1) {
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, mBrightness);
                }
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

            mView = new View(this);
        }

        registerReceiver(mBroadcastReceiver, INTENT_FILTER);

        return Service.START_STICKY_COMPATIBILITY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
