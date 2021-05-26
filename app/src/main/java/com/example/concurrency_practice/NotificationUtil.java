package com.example.concurrency_practice;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static androidx.core.content.ContextCompat.getSystemService;

public class NotificationUtil {

    private static int notificationId = 1;

    public static final String CHANNEL_ID = "default_channel_id";

    public static void showNotification(Context context) {
        Bitmap largeIconBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.icon_default));

        createNotificationChannel(context);

        Intent notificationDetailIntent = new Intent(context,DetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,notificationDetailIntent,0);

        Intent cancelIntent = new Intent(context,CustomBroadcastReceiver.class);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(context,0,cancelIntent,0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_school_icon)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText( context.getResources().getString(R.string.default_text))
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_school_icon,"cancel",cancelPendingIntent)
                .setLargeIcon(largeIconBitmap)
                //use category to by-pass do not disturb
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    @TargetApi(Build.VERSION_CODES.O)
    private static void createNotificationChannel(Context context) {
        CharSequence channelName =  context.getString(R.string.default_channel_name);

        String channelDescription = context.getString(R.string.default_channel_description);

        NotificationChannel notificationChannel = new NotificationChannel(
                CHANNEL_ID,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
        );

        notificationChannel.setDescription(channelDescription);

        notificationChannel.setShowBadge(true);

        NotificationManager notificationManager = getSystemService (context,NotificationManager.class);

        notificationManager.createNotificationChannel(notificationChannel);
    }
}
