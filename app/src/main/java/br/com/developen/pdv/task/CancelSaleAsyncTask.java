package br.com.developen.pdv.task;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.exception.TaskException;
import br.com.developen.pdv.room.CashVO;
import br.com.developen.pdv.room.SaleModel;
import br.com.developen.pdv.room.SaleReceiptCashModel;
import br.com.developen.pdv.room.SaleReceiptCashVO;
import br.com.developen.pdv.room.SaleVO;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.StringUtils;

public final class CancelSaleAsyncTask<L extends CancelSaleAsyncTask.Listener>
        extends AsyncTask<SaleModel, Void, Object> {


    private WeakReference<L> listener;

    private SharedPreferences preferences;


    public CancelSaleAsyncTask(L listener) {

        this.listener = new WeakReference<>(listener);

        this.preferences = App.getInstance().
                getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);

    }


    protected void onPreExecute() {

        L l = this.listener.get();

        if (l != null)

            l.onCancelSalePreExecute();

    }


    @SuppressLint("DefaultLocale")
    protected Object doInBackground(SaleModel... parameters) {

        List<String> exceptions = new ArrayList<>();

        DB database = DB.getInstance(App.getInstance());

        if (database==null)

            return new CannotInitializeDatabaseException();

        L l = listener.get();

        if (l != null)

            l.onCancelSaleProgressInitialize(
                    0,
                    parameters.length);

        try {

            //INICIA A TRANSACAO
            database.beginTransaction();

            for (SaleModel saleModel: parameters) {

                switch (saleModel.getStatus()) {

                    //VERIFICA SE A VENDA ESTA FINALIZADA
                    case Constants.FINISHED_SALE_STATUS: {

                        //VERIFICA SE O CAIXA ESTA ABERTO
                        if (!database.cashDAO().isOpenAsBoolean()) {

                            exceptions.add(String.format("Não foi possível cancelar a venda nº %d: Caixa encontra-se fechado.", saleModel.getNumber()));

                        } else {

                            //OBTEM A SOMA DOS RECEBIMENTOS
                            //EM DINHEIRO DA VENDA
                            Double cashSumOfSale = database.
                                    saleReceiptCashDAO().
                                    getTotalBySale(saleModel.getIdentifier());

                            if (cashSumOfSale > 0) {

                                //OBTEM O SALDO DO CAIXA
                                Double cashValue = database.cashDAO().valueAsDouble();

                                //VERIFICA SE EXISTE SALDO SUFICIENTE NO CAIXA
                                //PARA REALIZAR A DEVOLUCAO DO DINHEIRO AO CLIENTE
                                if (cashSumOfSale > cashValue) {

                                    exceptions.add(String.format("Não foi possível cancelar a venda nº %d: Saldo do caixa é insuficiente.", saleModel.getNumber()));

                                } else {

                                    //OBTEM OS PAGAMENTOS EM DINHEIRO DA VENDA
                                    List<SaleReceiptCashModel> saleReceiptCashList = database.
                                            saleReceiptCashDAO().
                                            getListBySale(saleModel.getIdentifier());

                                    for (SaleReceiptCashModel saleReceiptCashModel : saleReceiptCashList) {

                                        //VERIFICA SE O RECEBIMENTO
                                        //JA FOI PREVIAMENTE ESTORNADO
                                        if (saleReceiptCashModel.getReversal() == null) {

                                            //LANCA O MOVIMENTO DE ESTORNO NO CAIXA
                                            CashVO cashVO = new CashVO();

                                            cashVO.setDateTime(new Date());

                                            cashVO.setOperation(Constants.REVERSAL_CASH_OPERATION);

                                            cashVO.setType(Constants.OUT_CASH_TYPE);

                                            cashVO.setNote("Venda Nº " + saleModel.getIdentifier());

                                            cashVO.setUser(preferences.getInt(Constants.USER_IDENTIFIER_PROPERTY, 0));

                                            cashVO.setValue(saleReceiptCashModel.getCash().getValue());

                                            cashVO.setIdentifier(database.cashDAO().create(cashVO).intValue());

                                            //VINCULA O NOVO LANCAMENTO AO RECEBIMENTO ESTORNADO
                                            SaleReceiptCashVO saleReceiptCash = database.saleReceiptCashDAO().retrieve(

                                                    saleReceiptCashModel.getSaleReceipt().getSale().getIdentifier(),
                                                    saleReceiptCashModel.getSaleReceipt().getReceipt(),
                                                    saleReceiptCashModel.getCash().getIdentifier()

                                            );

                                            saleReceiptCash.setReversal(cashVO.getIdentifier());

                                            database.saleReceiptCashDAO().update(saleReceiptCash);

                                        }

                                    }

                                }

                            }

                            //DEFINE A VENDA COMO CANCELADA
                            SaleVO saleVO = database.
                                    saleDAO().
                                    retrieve(saleModel.getIdentifier());

                            saleVO.setStatus(Constants.CANCELED_SALE_STATUS);

                            saleVO.setNote(String.format("Venda cancelada por %s em %s.",
                                    preferences.getString(Constants.USER_NAME_PROPERTY,"Desconhecido"),
                                    StringUtils.formatDateTime(new Date())));

                            database.saleDAO().update(saleVO);

                        }

                        break;

                    }

                }

                //ATUALIZA O STATUS
                if (l != null)

                    l.onCancelSaleProgressUpdate(1);

                Thread.sleep(500);

            }

            database.setTransactionSuccessful();

            if (exceptions.isEmpty())

                return 0;

            else

                return new TaskException(exceptions.toArray(new String[0]));

        } catch(Exception exception) {

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


    protected void onProgressUpdate(Integer... progress) {

        L l = this.listener.get();

        if (l != null)

            l.onCancelSaleProgressUpdate(progress[0]);

    }


    protected void onCancelled() {

        L l = this.listener.get();

        if (l != null)

            l.onCancelSaleCancelled();

    }


    public interface Listener {

        void onCancelSalePreExecute();

        void onCancelSaleProgressInitialize(int progress, int max);

        void onCancelSaleProgressUpdate(int status);

        void onCancelSaleSuccess();

        void onCancelSaleFailure(Messaging message);

        void onCancelSaleCancelled();

    }


}