package br.com.developen.pdv.task;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.room.CashVO;
import br.com.developen.pdv.room.SaleModel;
import br.com.developen.pdv.room.SaleReceiptCashModel;
import br.com.developen.pdv.room.SaleReceiptCashVO;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.Messaging;

public final class CancelSaleAsyncTask<L extends CancelSaleAsyncTask.Listener>
        extends AsyncTask<SaleModel, Void, Object> {


    private WeakReference<L> listener;

    private SharedPreferences preferences;


    public CancelSaleAsyncTask(L listener) {

        this.listener = new WeakReference<>(listener);

        this.preferences = App.getInstance().
                getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);

    }


    protected Object doInBackground(SaleModel... parameters) {

        DB database = DB.getInstance(App.getInstance());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();

            for (SaleModel saleModel: parameters) {

                //VERIFICA SE A VENDA ESTA FINALIZADA
                switch (saleModel.getStatus()){

                    case Constants.FINISHED_SALE_STATUS:

                        //ESTORNA OS LANCAMENTOS NO CAIXA
                        List<SaleReceiptCashModel> saleReceiptCashList = database.
                                saleReceiptCashDAO().
                                getListBySale(saleModel.getIdentifier());

                        for (SaleReceiptCashModel saleReceiptCashModel: saleReceiptCashList) {


                            CashVO cashVO = new CashVO();

                            cashVO.setDateTime(new Date());

                            cashVO.setOperation(Constants.REVERSAL_CASH_OPERATION);

                            cashVO.setType(Constants.OUT_CASH_TYPE);

                            cashVO.setNote("Venda Nº " + saleModel.getIdentifier());

                            cashVO.setUser(preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

                            cashVO.setValue(saleReceiptCashModel.getCash().getValue());

                            cashVO.setIdentifier(database.cashDAO().create(cashVO).intValue());


                            SaleReceiptCashVO saleReceiptCash = database.saleReceiptCashDAO().retrieve(

                                    saleReceiptCashModel.getSaleReceipt().getSale().getIdentifier(),
                                    saleReceiptCashModel.getSaleReceipt().getReceipt(),
                                    saleReceiptCashModel.getCash().getIdentifier()

                            );

                            saleReceiptCash.setReversal(cashVO.getIdentifier());

                            database.saleReceiptCashDAO().update(saleReceiptCash);

                        }








                        break;

                }

            }

            database.setTransactionSuccessful();

            return null;

        } catch(Exception exception) {

            exception.printStackTrace();

            return exception;

        } finally {

            if (database.isOpen() && database.inTransaction())

                database.endTransaction();

        }

    }


    protected void onPostExecute(Object callResult) {

        L listener = this.listener.get();

        if (listener != null) {

            if (callResult instanceof Integer) {

                listener.onCancelSaleSuccess();

            } else {

                if (callResult instanceof Messaging) {

                    listener.onCancelSaleFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onCancelSaleSuccess();

        void onCancelSaleFailure(Messaging messaging);

    }


}