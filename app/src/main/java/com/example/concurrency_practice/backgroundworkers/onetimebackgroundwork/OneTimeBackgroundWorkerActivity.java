package com.example.concurrency_practice.backgroundworkers.onetimebackgroundwork;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.example.concurrency_practice.Constants;
import com.example.concurrency_practice.R;
import com.example.concurrency_practice.databinding.ActivityOneTimeBackgroundWorkerBinding;

public class OneTimeBackgroundWorkerActivity extends AppCompatActivity {

    public static final String TAG = OneTimeBackgroundWorkerActivity.class.getSimpleName();
    private ActivityOneTimeBackgroundWorkerBinding binding;
    private WorkManager mWorkManager;
    private static final String ONE_TIME_BACKGROUND_UNIQUE_WORK_NAME= "one_time_work";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_one_time_background_worker);
        mWorkManager = WorkManager.getInstance(this);
    }

    public void startOneTimeWork(View view) {
        binding.button.setVisibility(View.GONE);

        binding.loadingUserProgress.setVisibility(View.VISIBLE);

        Constraints constraints = getConstraints();

        Data inputData = getWorkerInputData();

        OneTimeWorkRequest workerARequest = new OneTimeWorkRequest.Builder(WorkerA.class)
                .setInputData(inputData)
                .addTag(WorkerA.class.getSimpleName())
                .build();

        OneTimeWorkRequest workerBRequest = new OneTimeWorkRequest.Builder(WorkerB.class)
                .setInputData(inputData)
                .addTag(WorkerB.class.getSimpleName())
                .build();

        OneTimeWorkRequest workerCRequest = new OneTimeWorkRequest.Builder(WorkerC.class)
                .setInputData(inputData)
                .addTag(WorkerC.class.getSimpleName())
                .build();

        mWorkManager.beginUniqueWork(ONE_TIME_BACKGROUND_UNIQUE_WORK_NAME, ExistingWorkPolicy.KEEP,workerARequest)
                .then(workerBRequest)
                .then(workerCRequest)
                .enqueue();

        observeWork();
    }

    private void observeWork() {
        mWorkManager.getWorkInfosByTagLiveData(WorkerC.class.getSimpleName()).observe(this,workInfoList -> {

            Log.d(TAG , "work info size : " + workInfoList.size());

            Log.d(TAG , "work info with state : " +  workInfoList.get(0).getTags() );

            if (workInfoList.get(0).getState() == WorkInfo.State.SUCCEEDED) {

                Data resultData = workInfoList.get(0).getOutputData();

                binding.userDetailsGroup.setVisibility(View.VISIBLE);

                binding.button.setVisibility(View.VISIBLE);

                binding.loadingUserProgress.setVisibility(View.GONE);

                binding.userFullName.setText(resultData.getString(Constants.USER_FULL_NAME_KEY));

                binding.userPhoneNumber.setText(resultData.getString(Constants.USER_PHONE_NUMBER_KEY));

                binding.occupation.setText(resultData.getString(Constants.USER_OCCUPATION_KEY));


            }
        });
    }

    private Data getWorkerInputData() {
        /*return new Data.Builder()
                .putString(Constants.USER_FULL_NAME_KEY,"")
                .putString(Constants.USER_PHONE_NUMBER_KEY,"")
                .putString(Constants.USER_OCCUPATION_KEY,"")
                .build();*/

        return Data.EMPTY;
    }

    private Constraints getConstraints() {
        return new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();
    }


}