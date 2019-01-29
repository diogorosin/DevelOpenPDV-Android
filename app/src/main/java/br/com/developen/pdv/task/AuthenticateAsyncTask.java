package br.com.developen.pdv.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.exception.InternalException;
import br.com.developen.pdv.exception.ValidationException;
import br.com.developen.pdv.room.UserModel;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.StringUtils;


public final class AuthenticateAsyncTask<
        A extends Activity & AuthenticateAsyncTask.Listener,
        B extends String,
        C extends Integer,
        D> extends AsyncTask<B, C, D> {


    private final WeakReference<A> activity;


    public AuthenticateAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onAuthenticatePreExecute();

    }


    protected Object doInBackground(String... parameters) {

        String login = parameters[0];

        String password = parameters[1];

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            Thread.sleep(1000);

            UserModel userModel = database.userDAO().getUserByLogin(login);

            if (userModel == null)

                throw new ValidationException("Nenhum usuário vinculado ao login informado.");

            if (!userModel.getActive())

                throw new ValidationException("Usuário não está ativo.");

            String digestedPassword = StringUtils.digestString(password);

            if (!digestedPassword.equals(userModel.getPassword()))

                throw new ValidationException("Senha incorreta.");

            return userModel;

        } catch (InterruptedException e) {

            return new InternalException();

        } catch (ValidationException e) {

            return e;

        }

    }


    protected void onPostExecute(Object callResult) {

        A listener = this.activity.get();

        if (listener != null) {

            if (callResult instanceof UserModel) {

                listener.onAuthenticateSuccess((UserModel) callResult);

            } else {

                if (callResult instanceof Messaging) {

                    listener.onAuthenticateFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onAuthenticatePreExecute();

        void onAuthenticateSuccess(UserModel userModel);

        void onAuthenticateFailure(Messaging messaging);

    }


}