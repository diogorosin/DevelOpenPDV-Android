package br.com.developen.pdv.task;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.developen.pdv.exception.HttpRequestException;
import br.com.developen.pdv.jersey.ExceptionBean;
import br.com.developen.pdv.jersey.StringBean;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.RequestBuilder;

public final class RetrieveDeviceAliasBySerialNumberAsyncTask<
        A extends Activity & RetrieveDeviceAliasBySerialNumberAsyncTask.Listener,
        B extends String,
        C extends Void,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;

    private SharedPreferences preferences;


    public RetrieveDeviceAliasBySerialNumberAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

        this.preferences = App.getInstance().
                getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onRetrieveDeviceAliasBySerialNumberPreExecute();

    }


    protected Object doInBackground(String... parameters) {

        String serialNumber = parameters[0];

        try {

            Response response = RequestBuilder.
                    build("device", "alias", serialNumber).
                    request(MediaType.APPLICATION_JSON).
                    header(HttpHeaders.AUTHORIZATION,
                            Constants.TOKEN_PREFIX +
                                    preferences.getString(Constants.TOKEN_PROPERTY, "")).
                    get();

            switch (response.getStatus()) {

                case HttpURLConnection.HTTP_OK:

                    return response.readEntity(StringBean.class);

                default:

                    return response.readEntity(ExceptionBean.class);

            }

        } catch (Exception e){

            return new HttpRequestException();

        }

    }


    protected void onPostExecute(Object callResult) {

        A listener = this.activity.get();

        if (listener != null) {

            if (callResult != null) {

                if (callResult instanceof StringBean){

                    listener.onRetrieveDeviceAliasBySerialNumberSuccess((StringBean) callResult);

                } else {

                    if (callResult instanceof Messaging){

                        listener.onRetrieveDeviceAliasBySerialNumberFailure((Messaging) callResult);

                    }

                }

            }

        }

    }


    public interface Listener {

        void onRetrieveDeviceAliasBySerialNumberPreExecute();

        void onRetrieveDeviceAliasBySerialNumberSuccess(StringBean stringBean);

        void onRetrieveDeviceAliasBySerialNumberFailure(Messaging messaging);

    }


}