package br.com.developen.pdv.task;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.exception.InternalException;
import br.com.developen.pdv.room.SaleReceiptVO;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.Messaging;

public final class DeleteSaleReceiptAsyncTask<L extends DeleteSaleReceiptAsyncTask.Listener> extends AsyncTask<Integer, Void, Object> {


    private WeakReference<L> listener;


    public DeleteSaleReceiptAsyncTask(L listener) {

        this.listener = new WeakReference<>(listener);

    }


    protected Object doInBackground(Integer... parameters) {

        Integer sale = parameters[0];

        Integer receipt = parameters[1];

        DB database = DB.getInstance(App.getInstance());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();

            SaleReceiptVO saleReceipt = new SaleReceiptVO();

            saleReceipt.setSale(sale);

            saleReceipt.setReceipt(receipt);

            database.saleReceiptDAO().delete(saleReceipt);

            database.setTransactionSuccessful();

            return Boolean.TRUE;

        } catch(Exception e) {

            return new InternalException();

        } finally {

            if (database.inTransaction())

                database.endTransaction();

        }

    }


    protected void onPostExecute(Object callResult) {

        L listener = this.listener.get();

        if (listener != null)

            if (callResult instanceof Messaging)

                listener.onDeleteSaleReceiptFailure((Messaging) callResult);

    }


    public interface Listener {

        void onDeleteSaleReceiptFailure(Messaging messaging);

    }


}