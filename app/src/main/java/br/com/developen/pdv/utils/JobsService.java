package br.com.developen.pdv.utils;

import android.app.job.JobParameters;
import android.app.job.JobService;

import java.util.List;

import br.com.developen.pdv.task.UploadSaleAsyncTask;

public class JobsService extends JobService implements UploadSaleAsyncTask.Listener {

    public boolean onStartJob(JobParameters params) {

        DB database = DB.getInstance(App.getInstance());

        List<Integer> salesToUpload = database.saleDAO().getSalesToUpload();

        if (salesToUpload != null && !salesToUpload.isEmpty()){

            new UploadSaleAsyncTask(this).execute(salesToUpload.toArray(new Integer[salesToUpload.size()]));

        } else {

            Jobs.scheduleJob(getApplicationContext());

        }

        return true;

    }

    public boolean onStopJob(JobParameters params) {

        return true;

    }

    public void onUploadSalesPreExecute() {

    }

    public void onUploadSalesSuccess() {

        Jobs.scheduleJob(getApplicationContext());

    }

    public void onUploadSalesFailure(Messaging messaging) {

        Jobs.scheduleJob(getApplicationContext());

    }

}