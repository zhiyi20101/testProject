package com.scott.testproject.jobscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

/**
 * Created by zouzhiyi on 20/06/17.
 */

public class MyJobSchedulerService extends JobService {
    private static final String TAG = MyJobSchedulerService.class.getSimpleName();
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG,"onStartJob");
        jobFinished(params,false);
        Log.d(TAG,"onStartJob:"+params.getExtras()
            +"describeContents:"+params.describeContents());

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG,"onStopJob");
        return false;
    }
}
