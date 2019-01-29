package br.com.developen.pdv.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.developen.pdv.exception.HttpRequestException;
import br.com.developen.pdv.jersey.ExceptionBean;
import br.com.developen.pdv.jersey.UserBean;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.RequestBuilder;

public final class RetrieveUserByLoginAsyncTask<
        A extends Activity & RetrieveUserByLoginAsyncTask.Listener,
        B extends String,
        C extends Void,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;


    public RetrieveUserByLoginAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onRetrieveUserByLoginPreExecute();

    }


    protected Object doInBackground(String... parameters) {

        String login = parameters[0];

        try {

            Response response = RequestBuilder.
                    build("user", "login", login).
                    request(MediaType.APPLICATION_JSON).
                    get();

            switch (response.getStatus()) {

                case HttpURLConnection.HTTP_OK:

                    return response.readEntity(UserBean.class);

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

            if (callResult instanceof UserBean){

                listener.onRetrieveUserByLoginSuccess((UserBean) callResult);

            } else {

                if (callResult instanceof Messaging){

                    listener.onRetrieveUserByLoginFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onRetrieveUserByLoginPreExecute();

        void onRetrieveUserByLoginSuccess(UserBean userBean);

        void onRetrieveUserByLoginFailure(Messaging messaging);

    }


}