package io.keiji.ghostbusters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = BootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onBootCompleted");
        }

        context.startService(new Intent(context, WatchDockService.class));
    }

}
