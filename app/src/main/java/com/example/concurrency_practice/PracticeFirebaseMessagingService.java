package com.example.concurrency_practice;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PracticeFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = PracticeFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "token received : " + token);
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "message received : ");

        remoteMessage.getData().forEach((key, value) -> {
            Log.d(TAG, key +  " : " +  value);
        });
    }
}
