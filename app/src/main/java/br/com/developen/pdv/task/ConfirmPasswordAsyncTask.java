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


public final class ConfirmPasswordAsyncTask<
        A extends Activity & ConfirmPasswordAsyncTask.Listener,
        B extends Object,
        C extends Integer,
        D> extends AsyncTask<B, C, D> {


    private final WeakReference<A> activity;


    public ConfirmPasswordAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onConfirmPasswordPreExecute();

    }


    protected Object doInBackground(Object... parameters) {

        Integer user = (Integer) parameters[0];

        String numericPassword = (String) parameters[1];

        Integer minimumLevel = (Integer) parameters[2];

        Integer sleep = (Integer) parameters[3];

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            if (sleep > 0)

                Thread.sleep(sleep);

            UserModel userModel = database.userDAO().getUserByIdentifier(user);

            if (userModel == null)

                throw new ValidationException("Nenhum usuário vinculado ao código informado.");

            if (userModel.getLevel() < minimumLevel)

                throw new ValidationException("Essa função requer previlégios de " + StringUtils.getDenominationOfLevel(minimumLevel) + ".");

            if (!userModel.getActive())

                throw new ValidationException("Usuário não está ativo.");

            if (!numericPassword.equals(userModel.getNumericPassword()))

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

                listener.onConfirmPasswordSuccess((UserModel) callResult);

            } else {

                if (callResult instanceof Messaging) {

                    listener.onConfirmPasswordFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onConfirmPasswordPreExecute();

        void onConfirmPasswordSuccess(UserModel userModel);

        void onConfirmPasswordFailure(Messaging messaging);

    }


}