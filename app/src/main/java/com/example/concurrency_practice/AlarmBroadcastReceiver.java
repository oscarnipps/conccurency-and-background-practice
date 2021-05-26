package com.example.concurrency_practice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = AlarmBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "alarm received with action : " + intent.getAction());

        NotificationUtil.showNotification(context);

        String name = intent.getStringExtra("name-key");

        int age = intent.getIntExtra("age-key", 0);

        Log.d(TAG, "name from set alarm : " + name);

        Log.d(TAG, "age from set alarm : " + age);

        CustomAlarmManager.setAlarm(context);
    }

}
