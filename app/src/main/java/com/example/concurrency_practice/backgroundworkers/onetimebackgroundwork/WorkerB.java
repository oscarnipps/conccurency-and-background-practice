package com.example.concurrency_practice.backgroundworkers.onetimebackgroundwork;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.concurrency_practice.Constants;

public class WorkerB extends Worker {

    public static final String TAG = WorkerB.class.getSimpleName();
    private Data outputData;

    public WorkerB(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "worker B doing background work");

        try {
            Thread.sleep(5000);

            Data inputData = getInputData();

            String userFullName = inputData.getString(Constants.USER_FULL_NAME_KEY);

            String phoneNumberValue = "08033389897";

            outputData = new Data.Builder()
                    .putString(Constants.USER_PHONE_NUMBER_KEY, phoneNumberValue)
                    .putString(Constants.USER_FULL_NAME_KEY, userFullName)
                    .build();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Result.success(outputData);
    }

}