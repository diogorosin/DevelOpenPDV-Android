package br.com.developen.pdv.task;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.exception.InternalException;
import br.com.developen.pdv.room.SaleReceiptModel;
import br.com.developen.pdv.room.SaleReceiptVO;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.Messaging;

public final class UpdateMoneyReceiptAsyncTask<L extends UpdateMoneyReceiptAsyncTask.Listener> extends AsyncTask<Object, Void, Object> {


    private WeakReference<L> listener;


    public UpdateMoneyReceiptAsyncTask(L listener) {

        this.listener = new WeakReference<>(listener);

    }


    protected Object doInBackground(Object... parameters) {

        Integer sale = (Integer) parameters[0];

        Double newValue = (Double) parameters[1];

        DB database = DB.getInstance(App.getInstance());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();

            List<SaleReceiptModel> moneyReceipts = database.
                    saleReceiptDAO().getReceiptsByMethod(sale, "DIN");

            if (moneyReceipts != null && !moneyReceipts.isEmpty()){

                if (newValue > 0) {

                    SaleReceiptVO moneyReceipt = new SaleReceiptVO();

                    moneyReceipt.setSale(moneyReceipts.get(0).getSale().getIdentifier());

                    moneyReceipt.setReceipt(moneyReceipts.get(0).getReceipt());

                    moneyReceipt.setReceiptMethod(moneyReceipts.get(0).getReceiptMethod().getIdentifier());

                    moneyReceipt.setValue(newValue);

                    database.saleReceiptDAO().update(moneyReceipt);

                } else {

                    SaleReceiptVO moneyReceipt = new SaleReceiptVO();

                    moneyReceipt.setSale(moneyReceipts.get(0).getSale().getIdentifier());

                    moneyReceipt.setReceipt(moneyReceipts.get(0).getReceipt());

                    database.saleReceiptDAO().delete(moneyReceipt);

                }

            } else {

                if (newValue > 0) {

                    SaleReceiptVO moneyReceipt = new SaleReceiptVO();

                    moneyReceipt.setSale(sale);

                    moneyReceipt.setReceipt(database.saleReceiptDAO().lastGeneradedReceipt(sale) + 1);

                    moneyReceipt.setReceiptMethod("DIN");

                    moneyReceipt.setValue(newValue);

                    database.saleReceiptDAO().create(moneyReceipt);

                }

            }

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

                listener.onUpdateMoneyReceiptItemFailure((Messaging) callResult);

    }


    public interface Listener {

        void onUpdateMoneyReceiptItemFailure(Messaging messaging);

    }


}