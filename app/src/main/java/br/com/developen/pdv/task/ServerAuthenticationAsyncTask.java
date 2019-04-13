package br.com.developen.pdv.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.developen.pdv.exception.HttpRequestException;
import br.com.developen.pdv.jersey.CredentialBean;
import br.com.developen.pdv.jersey.ExceptionBean;
import br.com.developen.pdv.jersey.TokenBean;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.RequestBuilder;

public final class ServerAuthenticationAsyncTask<
        A extends Activity & ServerAuthenticationAsyncTask.Listener,
        B extends String,
        C extends Void,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;


    public ServerAuthenticationAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onServerAuthenticationPreExecute();

    }


    protected Object doInBackground(String... parameters) {

        String login = parameters[0];

        String password = parameters[1];

        try {

            CredentialBean credentialBean = new CredentialBean();

            credentialBean.setLogin(login);

            credentialBean.setPassword(password);

            credentialBean.setCompany(null);

            Response response = RequestBuilder.
                    build("security", "authenticate").
                    request(MediaType.APPLICATION_JSON).
                    post(Entity.entity(credentialBean, MediaType.APPLICATION_JSON));

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

                listener.onServerAuthenticationSuccess((TokenBean) callResult);

            } else {

                if (callResult instanceof Messaging){

                    listener.onServerAuthenticationFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onServerAuthenticationPreExecute();

        void onServerAuthenticationSuccess(TokenBean tokenBean);

        void onServerAuthenticationFailure(Messaging messaging);

    }


}