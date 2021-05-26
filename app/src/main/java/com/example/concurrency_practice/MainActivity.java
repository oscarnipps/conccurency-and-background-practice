package com.example.concurrency_practice;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import com.example.concurrency_practice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private int notificationId = 1;
    public static final String CHANNEL_ID = "default_channel_id";
    private AlarmManager mAlarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }



    public void showNotification(View view) {
        Bitmap largeIconBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.icon_default));

        createNotificationChannel();

        Intent notificationDetailIntent = new Intent(this,DetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationDetailIntent,0);

        Intent cancelIntent = new Intent(this,CustomBroadcastReceiver.class);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(this,0,cancelIntent,0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_school_icon)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(R.string.default_text))
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_school_icon,"cancel",cancelPendingIntent)
                .setLargeIcon(largeIconBitmap)
                //use category to by-pass don not disturb
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        CharSequence channelName = getString(R.string.default_channel_name);

        String channelDescription = getString(R.string.default_channel_description);

        NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
        );

        notificationChannel.setDescription(channelDescription);

        notificationChannel.setShowBadge(true);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        notificationManager.createNotificationChannel(notificationChannel);
    }

    public void setAlarm(View view) {
        CustomAlarmManager.setAlarm(this);

       /* Intent alarmIntent = new Intent(this , AlarmBroadcastReceiver.class);
        alarmIntent.setAction(ALARM_RECEIVER_ACTION);
        alarmIntent.putExtra("name-key" , "oscar brent");
        alarmIntent.putExtra("age-key" , 25);


        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(
                this,
                100,
                alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        long triggerTimeInMilliSeconds = getTriggerTime();

        Toast.makeText(this, "alarm set", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP ,triggerTimeInMilliSeconds , alarmPendingIntent);
            return;
        }

        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP ,triggerTimeInMilliSeconds , alarmPendingIntent);*/
    }

/*    private long getTriggerTime() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY,9);

        calendar.set(Calendar.MINUTE,5);

        return calendar.getTimeInMillis();
    }*/
}