package br.com.developen.pdv.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.Date;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.exception.InternalException;
import br.com.developen.pdv.room.CashModel;
import br.com.developen.pdv.room.CashVO;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.Messaging;

public final class OpenCashAsyncTask<A extends Activity & OpenCashAsyncTask.Listener> extends AsyncTask<Object, Void, Object> {


    private WeakReference<A> activity;


    public OpenCashAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected Object doInBackground(Object... parameters) {

        Double value = (Double) parameters[0];

        Integer user = (Integer) parameters[1];

        DB database = null;

        if (activity.get() != null)

            database = DB.getInstance(activity.get().getBaseContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();


            CashVO cashVO = new CashVO();

            cashVO.setType(Constants.ENTRY_CASH_TYPE);

            cashVO.setOperation(Constants.OPEN_CASH_OPERATION);

            cashVO.setValue(value);

            cashVO.setNote("Abertura do Caixa");

            cashVO.setDateTime(new Date());

            cashVO.setUser(user);

            cashVO.setIdentifier(database.cashDAO().create(cashVO).intValue());


            database.setTransactionSuccessful();


            CashModel cashModel = new CashModel();

            cashModel.setIdentifier(cashVO.getIdentifier());

            cashModel.setType(cashVO.getType());

            cashModel.setOperation(cashVO.getOperation());

            cashModel.setValue(cashVO.getValue());

            cashModel.setNote(cashVO.getNote());

            cashModel.setDateTime(cashVO.getDateTime());

            cashModel.setUser(database.userDAO().getUserByIdentifier(cashVO.getUser()));


            return cashModel;


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

            if (callResult instanceof CashModel) {

                listener.onOpenCashSuccess((CashModel) callResult);

            } else {

                if (callResult instanceof Messaging) {

                    listener.onOpenCashFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onOpenCashSuccess(CashModel cashModel);

        void onOpenCashFailure(Messaging messaging);

    }


}