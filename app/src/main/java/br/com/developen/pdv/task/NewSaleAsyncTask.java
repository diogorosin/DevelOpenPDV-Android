package br.com.developen.pdv.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Date;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.exception.InternalException;
import br.com.developen.pdv.room.SaleModel;
import br.com.developen.pdv.room.SaleVO;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.Messaging;

public final class NewSaleAsyncTask<A extends Activity & NewSaleAsyncTask.Listener> extends AsyncTask<Object, Void, Object> {


    private WeakReference<A> activity;


    public NewSaleAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected Object doInBackground(Object... parameters) {

        Date dateTime = (Date) parameters[0];

        Integer user = (Integer) parameters[1];

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();

            SaleVO saleVO = new SaleVO();

            saleVO.setDateTime(dateTime);

            saleVO.setUser(user);

            saleVO.setIdentifier(database.saleDAO().create(saleVO).intValue());

            database.setTransactionSuccessful();

            return database.saleDAO().getSaleByIdentifier(saleVO.getIdentifier());

        } catch(Exception e) {

            return new InternalException();

        } finally {

            if (database.inTransaction())

                database.endTransaction();

        }

    }


    protected void onPostExecute(Object callResult) {

        A listener = this.activity.get();

        if (listener != null) {

            if (callResult instanceof SaleModel) {

                listener.onNewSaleCreateSuccess((SaleModel) callResult);

            } else {

                if (callResult instanceof Messaging) {

                    listener.onNewSaleCreateFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onNewSaleCreateSuccess(SaleModel saleModel);

        void onNewSaleCreateFailure(Messaging messaging);

    }


}