package br.com.developen.pdv.task;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.developen.pdv.exception.HttpRequestException;
import br.com.developen.pdv.jersey.CompanyBean;
import br.com.developen.pdv.jersey.ExceptionBean;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.RequestBuilder;

public final class GetCompaniesAsyncTask<
        A extends Activity & GetCompaniesAsyncTask.Listener,
        B extends Void,
        C extends Void,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;

    private SharedPreferences preferences;


    public GetCompaniesAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

        this.preferences = App.getInstance().
                getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onGetCompanyPreExecute();

    }


    protected Object doInBackground(Void... parameters) {

        try {

            Response response = RequestBuilder.
                    build("security", "company").
                    request(MediaType.APPLICATION_JSON).
                    header(HttpHeaders.AUTHORIZATION,
                            Constants.TOKEN_PREFIX +
                                    preferences.getString(Constants.TOKEN_PROPERTY, "")).
                    get();

            switch (response.getStatus()) {

                case HttpURLConnection.HTTP_PARTIAL:
                case HttpURLConnection.HTTP_OK:

                    return response.readEntity(new GenericType<List<CompanyBean>>(){});

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

            if (callResult instanceof List){

                listener.onGetCompanySuccess((List<CompanyBean>) callResult);

            } else {

                if (callResult instanceof Messaging){

                    listener.onGetCompanyFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onGetCompanyPreExecute();

        void onGetCompanySuccess(List<CompanyBean> list);

        void onGetCompanyFailure(Messaging messaging);

    }


}