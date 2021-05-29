package com.example.concurrency_practice.backgroundworkers.onetimebackgroundwork;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.concurrency_practice.Constants;

public class WorkerA extends Worker {

    public static final String TAG = WorkerA.class.getSimpleName();
    private Data outputData;

    public WorkerA(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "worker A doing background work");

        try {
            Thread.sleep(5000);

            String fullNameValue = "Robert Meek Mason";

            outputData = new Data.Builder()
                    .putString(Constants.USER_FULL_NAME_KEY, fullNameValue)
                    .build();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Result.success(outputData);
    }

}
