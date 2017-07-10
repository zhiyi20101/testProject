package com.scott.testproject.jobscheduler;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.scott.testproject.R;

import java.util.List;

public class JobSchedulerActivity extends Activity implements View.OnClickListener{
    private int mJobId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);
        findViewById(R.id.start_job).setOnClickListener(this);
        findViewById(R.id.stop_job).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_job:
                startJobService();
                break;
            case R.id.stop_job:
                stopJobService();
                break;
        }
    }

    private void stopJobService() {
        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        List<JobInfo> list = jobScheduler.getAllPendingJobs();
        if (list.size() > 0){
            int jobId = list.get(0).getId();
            jobScheduler.cancel(jobId);
            Toast.makeText(this,"取消了"+jobId,Toast.LENGTH_LONG).show();
        }
    }

    private void startJobService() {
        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(mJobId++,
                new ComponentName(getPackageName(),MyJobSchedulerService.class.getName()));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        //每三秒重复一次
        //builder.setPeriodic(3000);
        int result = jobScheduler.schedule(builder.build());
        Toast.makeText(this,"result:"+result,Toast.LENGTH_LONG).show();
    }
}
