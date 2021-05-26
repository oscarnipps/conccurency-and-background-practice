package com.example.concurrency_practice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import java.util.Calendar;

import static androidx.core.content.ContextCompat.getSystemService;

public class CustomAlarmManager {

    public static void setAlarm(Context context) {
        AlarmManager mAlarmManager =  getSystemService(context,AlarmManager.class);

        Intent alarmIntent = new Intent(context , AlarmBroadcastReceiver.class);
        alarmIntent.setAction(Constants.ALARM_RECEIVER_ACTION);
        alarmIntent.putExtra("name-key" , "oscar brent");
        alarmIntent.putExtra("age-key" , 25);


        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(
                context,
                100,
                alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        long triggerTimeInMilliSeconds = getCustomTriggerTime();

        Toast.makeText(context, "alarm set", Toast.LENGTH_SHORT).show();

        enableRebootBroadcastReceiver(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP ,triggerTimeInMilliSeconds , alarmPendingIntent);
            return;
        }

        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP ,triggerTimeInMilliSeconds , alarmPendingIntent);
    }

    //enables the alarm to be reset when the device has being rebooted
    private static void enableRebootBroadcastReceiver(Context context) {
        ComponentName rebootReceiver = new ComponentName(context,RebootBroadcastReceiver.class);

        PackageManager packageManager = context.getPackageManager();

        packageManager.setComponentEnabledSetting(
                rebootReceiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );
    }

    private static long getTriggerTime() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY,21);

        calendar.set(Calendar.MINUTE,3);

        return calendar.getTimeInMillis();
    }

    private static long getCustomTriggerTime() {
        //every 2 minutes or you can specify the time in milliseconds
        return System.currentTimeMillis() + 120000;
    }

}
