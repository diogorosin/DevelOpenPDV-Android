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
import br.com.developen.pdv.jersey.CompanyBean;
import br.com.developen.pdv.jersey.ExceptionBean;
import br.com.developen.pdv.jersey.TokenBean;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.RequestBuilder;

public final class ServerSwitchCompanyAsyncTask<
        A extends Activity & ServerSwitchCompanyAsyncTask.Listener,
        B extends Integer,
        C extends Void,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;

    private SharedPreferences preferences;


    public ServerSwitchCompanyAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

        this.preferences = App.getInstance().
                getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onServerSwitchCompanyPreExecute();

    }


    protected Object doInBackground(Integer... parameters) {

        Integer identifier = parameters[0];

        try {

            CompanyBean companyBean = new CompanyBean();

            companyBean.setIdentifier(identifier);

            Response response = RequestBuilder.
                    build("security", "company").
                    request(MediaType.APPLICATION_JSON).
                    header(HttpHeaders.AUTHORIZATION,
                            Constants.TOKEN_PREFIX +
                                    preferences.getString(Constants.TOKEN_PROPERTY, "")).
                    put(Entity.entity(companyBean, MediaType.APPLICATION_JSON));

            switch (response.getStatus()) {

                case HttpURLConnection.HTTP_OK:

                    return response.readEntity(TokenBean.class);

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

            if (callResult instanceof TokenBean){

                listener.onServerSwitchCompanySuccess((TokenBean) callResult);

            } else {

                if (callResult instanceof Messaging){

                    listener.onServerSwitchCompanyFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onServerSwitchCompanyPreExecute();

        void onServerSwitchCompanySuccess(TokenBean tokenBean);

        void onServerSwitchCompanyFailure(Messaging messaging);

    }


}