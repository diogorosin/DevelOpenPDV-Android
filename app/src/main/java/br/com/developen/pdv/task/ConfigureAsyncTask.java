package br.com.developen.pdv.task;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.developen.pdv.exception.HttpRequestException;
import br.com.developen.pdv.jersey.ConfigurationBean;
import br.com.developen.pdv.jersey.CompanyDeviceDatasetBean;
import br.com.developen.pdv.jersey.ExceptionBean;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.RequestBuilder;

public final class ConfigureAsyncTask<
        A extends Activity & ConfigureAsyncTask.Listener,
        B extends ConfigurationBean,
        C extends Integer,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;

    private SharedPreferences preferences;


    public ConfigureAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

        this.preferences = App.getInstance().
                getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onConfigurePreExecute();

    }


    protected Object doInBackground(ConfigurationBean... parameters) {

        ConfigurationBean configurationBean = parameters[0];

        try {

            Response response = RequestBuilder.
                    build("device", "pos").
                    request(MediaType.APPLICATION_JSON).
                    header(HttpHeaders.AUTHORIZATION,
                            Constants.TOKEN_PREFIX +
                                    preferences.getString(Constants.TOKEN_PROPERTY, "")).
                    post(Entity.entity(configurationBean, MediaType.APPLICATION_JSON));

            switch (response.getStatus()) {

                case HttpURLConnection.HTTP_OK:

                    return response.readEntity(CompanyDeviceDatasetBean.class);

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

                if (callResult instanceof CompanyDeviceDatasetBean){

                    listener.onConfigureSuccess((CompanyDeviceDatasetBean) callResult);

                } else {

                    if (callResult instanceof Messaging){

                        listener.onConfigureFailure((Messaging) callResult);

                    }

                }

            }

        }

    }


    public interface Listener {

        void onConfigurePreExecute();

        void onConfigureSuccess(CompanyDeviceDatasetBean companyDeviceDatasetBean);

        void onConfigureFailure(Messaging messaging);

    }


}
