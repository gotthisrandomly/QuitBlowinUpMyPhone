package com.quit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class SilenceService extends BroadcastReceiver {
    private static final int MAX_SILENCE_TIME = 5 * 60 * 1000; // 5 minutes
    private static final int MAX_CALLS = 2;

    private static int callCount = 0;
    private static long lastCallTime = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            tm.listen(new PhoneStateListener() {
					@Override
					public void onCallStateChanged(int state, String incomingNumber) {
						super.onCallStateChanged(state, incomingNumber);
						if (state == TelephonyManager.CALL_STATE_RINGING) {
							long currentTime = System.currentTimeMillis();
							if (callCount < MAX_CALLS && (currentTime - lastCallTime) > MAX_SILENCE_TIME) {
								// silence the ringer
								// ...
								callCount++;
								lastCallTime = currentTime;
							}
						}
					}
				}, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
}

