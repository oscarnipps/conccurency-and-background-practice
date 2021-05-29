package com.example.concurrency_practice.backgroundworkers.onetimebackgroundwork;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.concurrency_practice.Constants;

public class WorkerC extends Worker {

    public static final String TAG = WorkerC.class.getSimpleName();
    private Data outputData;

    public WorkerC(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "worker C doing background work");

        try {
            Thread.sleep(5000);

            Data inputData = getInputData();

            String phoneNumberValue = inputData.getString(Constants.USER_PHONE_NUMBER_KEY);

            String userFullName = inputData.getString(Constants.USER_FULL_NAME_KEY);

            String occupationValue = "Lawyer";

            outputData = new Data.Builder()
                    .putString(Constants.USER_PHONE_NUMBER_KEY, phoneNumberValue)
                    .putString(Constants.USER_FULL_NAME_KEY, userFullName)
                    .putString(Constants.USER_OCCUPATION_KEY, occupationValue)
                    .build();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Result.success(outputData);
    }

}
