package br.com.developen.pdv.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.developen.pdv.exception.HttpRequestException;
import br.com.developen.pdv.jersey.AccountBean;
import br.com.developen.pdv.jersey.DatasetBean;
import br.com.developen.pdv.jersey.ExceptionBean;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.RequestBuilder;

public final class CreateAccountAsyncTask<
        A extends Activity & CreateAccountAsyncTask.Listener,
        B extends AccountBean,
        C extends Integer,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;


    public CreateAccountAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onCreateAccountPreExecute();

    }


    protected Object doInBackground(AccountBean... parameters) {

        AccountBean accountBean = parameters[0];

        try {

            Response response = RequestBuilder.
                    build("account").
                    request(MediaType.APPLICATION_JSON).
                    post(Entity.entity(accountBean, MediaType.APPLICATION_JSON));

            switch (response.getStatus()) {

                case HttpURLConnection.HTTP_OK:

                    return response.readEntity(DatasetBean.class);

                default:

                    return response.readEntity(ExceptionBean.class);

            }

        } catch (Exception e){

            e.printStackTrace();

            return new HttpRequestException();

        }

    }


    protected void onPostExecute(Object callResult) {

        A listener = this.activity.get();

        if (listener != null) {

            if (callResult != null) {

                if (callResult instanceof DatasetBean){

                    listener.onCreateAccountSuccess((DatasetBean) callResult);

                } else {

                    if (callResult instanceof Messaging){

                        listener.onCreateAccountFailure((Messaging) callResult);

                    }

                }

            }

        }

    }


    public interface Listener {

        void onCreateAccountPreExecute();

        void onCreateAccountSuccess(DatasetBean datasetBean);

        void onCreateAccountFailure(Messaging messaging);

    }


}