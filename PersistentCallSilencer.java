package com.quit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class PersistentCallSilencer extends BroadcastReceiver {
    private static final long MINUTE_IN_MILLIS = 60000;
    private HashMap<String, ArrayList<Long>> numberToTimestamps = new HashMap<>();
    private HashMap<String, ArrayList<Long>> addressToTimestamps = new HashMap<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            if (pdus != null) {
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                String incomingAddress = messages[0].getOriginatingAddress();

                if (incomingAddress != null) {
                    ArrayList<Long> timestamps = addressToTimestamps.get(incomingAddress);
                    long currentTimestamp = System.currentTimeMillis();

                    if (timestamps != null && timestamps.size() > 0 && (currentTimestamp - timestamps.get(timestamps.size() - 1)) <= (5 * MINUTE_IN_MILLIS)) {
                        // This is a persistent texter, silence the ringtone
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        Toast.makeText(context, "Persistent texter detected, silencing ringtone", Toast.LENGTH_SHORT).show();
                    } else {
                        // This is not a persistent texter, add it to the list of texters
                        if (timestamps == null) {
                            timestamps = new ArrayList<>();
                            addressToTimestamps.put(incomingAddress, timestamps);
                        }
                        timestamps.add(currentTimestamp);
                    }
                }
            }
        } else if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            String state = intent.getStringExtra("state");
            if (state != null && state.equals("RINGING")) {
                String incomingNumber = intent.getStringExtra("incoming_number");

                if (incomingNumber != null) {
                    ArrayList<Long> timestamps = numberToTimestamps.get(incomingNumber);
                    long currentTimestamp = System.currentTimeMillis();

                    if (timestamps != null && timestamps.size() > 0 && (currentTimestamp - timestamps.get(timestamps.size() - 1)) <= (5 * MINUTE_IN_MILLIS)) {
                        // This is a persistent caller, silence the ringtone
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        Toast.makeText(context, "Persistent caller detected, silencing ringtone", Toast.LENGTH_SHORT).show();
                    } else {
                        // This is not a persistent caller, add it to the list of callers
                        if (timestamps == null) {
                            timestamps = new ArrayList<>();
                            numberToTimestamps.put(incomingNumber, timestamps);
                        }
                        timestamps.add(currentTimestamp);
                    }
                }
            } else if (state != null && state.equals("IDLE")) {
                // Reset the list of timestamps when the call ends
                numberToTimestamps.clear();
            }
        }
    }
}
