package br.com.developen.pdv.task;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.exception.InternalException;
import br.com.developen.pdv.room.CashVO;
import br.com.developen.pdv.room.MeasureUnitGroup;
import br.com.developen.pdv.room.ProductModel;
import br.com.developen.pdv.room.ProductProductModel;
import br.com.developen.pdv.room.SaleItemModel;
import br.com.developen.pdv.room.SaleItemTicketVO;
import br.com.developen.pdv.room.SaleModel;
import br.com.developen.pdv.room.SaleReceiptCashVO;
import br.com.developen.pdv.room.SaleReceiptModel;
import br.com.developen.pdv.room.SaleReceiptVO;
import br.com.developen.pdv.room.SaleableType;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.Messaging;

public final class FinalizeSaleAsyncTask<L extends FinalizeSaleAsyncTask.Listener>
        extends AsyncTask<Object, Void, Object> {


    private WeakReference<L> listener;


    public FinalizeSaleAsyncTask(L listener) {

        this.listener = new WeakReference<>(listener);

    }


    protected Object doInBackground(Object... parameters) {

        Integer saleIdentifier = (Integer) parameters[0];

        DB database = null;

        if (listener.get() != null)

            database = DB.getInstance(App.getInstance());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();

            SaleModel saleModel = database.
                    saleDAO().
                    getSaleByIdentifier(saleIdentifier);

            List<SaleItemModel> saleItems = database.saleItemDAO().getItemsAsList(saleModel.getIdentifier());

            if (saleItems!=null && !saleItems.isEmpty()){

                for (SaleItemModel saleItemModel: saleItems) {

                    //VERIFICA SE O ITEM É UNITARIO.
                    boolean isUnit = saleItemModel.getMeasureUnit().getGroup() == MeasureUnitGroup.UNIT.ordinal();

                    //CASO SEJA UNITARIO, IMPRIME UM TICKET POR UNIDADE.
                    int quantity = isUnit ? saleItemModel.getQuantity().intValue() : 1;

                    //VERIFICA SE O ITEM É COMPOSTO.
                    boolean isComposed = saleItemModel.getSaleable().getType() == (SaleableType.MERCHANDISE.ordinal()) &&
                            (database.productDAO().isComposed(saleItemModel.getSaleable().getIdentifier()));

                    //SE FOR UNITARIO E O PRODUTO POSSUI COMPOSICAO
                    if (isUnit && isComposed){

                        //CRIA LISTA DE TICKETS
                        List<SaleItemTicketVO> ticketList = new ArrayList<>();

                        //VARIAVEL QUE CONTROLA O NUMERO
                        //SEQUENCIAL DOS TICKETS DO ITEM VENDIDO
                        int ticket = 1;

                        //BUSCA A COMPOSICAO DO PRODUTO
                        List<ProductProductModel> parts = database.
                                productProductDAO().
                                getCompositionOfProduct(saleItemModel.
                                        getSaleable().
                                        getIdentifier());

                        //QUANTIDADE DO ITEM DA VENDA
                        for (int x = 1; x <= quantity; x++) {

                            //PARTES DO PRODUTO
                            for (ProductProductModel part: parts) {

                                //BUSCA OS DADOS DO COMPONENTE
                                ProductModel childModel = database.productDAO().getProduct(part.getChild().getIdentifier());

                                //VERIFICA SE O COMPONENTE É UNITARIO
                                boolean isPartUnit = childModel.getStockUnit().getGroup() == MeasureUnitGroup.UNIT.ordinal();

                                //CASO SEJA UNITARIO, IMPRIME UM TICKET POR UNIDADE DO COMPONENTE.
                                int partQuantity = isPartUnit ? part.getQuantity().intValue() : 1;

                                //GERA O CUPOM DO COMPONENTE
                                for (int y = 1; y <= partQuantity; y++){

                                    SaleItemTicketVO saleItemTicketVO = new SaleItemTicketVO();

                                    saleItemTicketVO.setSale(saleItemModel.getSale().getIdentifier());

                                    saleItemTicketVO.setItem(saleItemModel.getItem());

                                    saleItemTicketVO.setTicket(ticket);

                                    saleItemTicketVO.setOf(0);

                                    saleItemTicketVO.setLabel(childModel.getDenomination());

                                    saleItemTicketVO.setQuantity(isPartUnit ? 1 : part.getQuantity());

                                    saleItemTicketVO.setMeasureUnit(childModel.getStockUnit().getIdentifier());

                                    saleItemTicketVO.setPrinted(false);

                                    ticketList.add(saleItemTicketVO);

                                    ticket++;

                                }

                            }

                        }

                        for (SaleItemTicketVO saleItemTicketVO: ticketList) {

                            //ATUALIZA O TOTAL DE TICKETS CRIADOS PARA O ITEM
                            saleItemTicketVO.setOf(ticket);

                            //CRIA O ITEM
                            database.saleItemTicketDAO().create(saleItemTicketVO);

                        }

                    } else {

                        for (int n = 1; n <= quantity; n++) {

                            SaleItemTicketVO saleItemTicketVO = new SaleItemTicketVO();

                            saleItemTicketVO.setSale(saleItemModel.getSale().getIdentifier());

                            saleItemTicketVO.setItem(saleItemModel.getItem());

                            saleItemTicketVO.setTicket(n);

                            saleItemTicketVO.setOf(quantity);

                            saleItemTicketVO.setLabel(saleItemModel.getSaleable().getLabel());

                            saleItemTicketVO.setQuantity(isUnit ? 1 : saleItemModel.getQuantity());

                            saleItemTicketVO.setMeasureUnit(saleItemModel.getMeasureUnit().getIdentifier());

                            saleItemTicketVO.setPrinted(false);

                            database.saleItemTicketDAO().create(saleItemTicketVO);

                        }

                    }

                }

            }

            //CRIAR O RECEBIMENTO EM DINHEIRO CASO O USUARIO
            //NAO TENHA INFORMADO NENHUMA FORMA DE RECEBIMENTO
            if (database.saleReceiptDAO().count(saleModel.getIdentifier()) == 0){

                SaleReceiptVO saleReceiptVO = new SaleReceiptVO();

                saleReceiptVO.setSale(saleModel.getIdentifier());

                saleReceiptVO.setReceipt(1);

                saleReceiptVO.setReceiptMethod("DIN");

                saleReceiptVO.setValue(database.
                        saleDAO().
                        getTotalAsDouble(saleModel.getIdentifier()));

                database.saleReceiptDAO().create(saleReceiptVO);

            }

            //LANCA OS RECEBIMENTOS DA VENDA
            for (SaleReceiptModel saleReceiptModel: database.saleReceiptDAO().getReceiptsAsList(saleModel.getIdentifier())) {

                switch (saleReceiptModel.getReceiptMethod().getIdentifier()){

                    case "DIN":

                        CashVO cashVO = new CashVO();

                        cashVO.setDateTime(new Date());

                        cashVO.setOperation("REC");

                        cashVO.setType("E");

                        cashVO.setNote("Venda Nº " + saleModel.getIdentifier());

                        cashVO.setUser(saleModel.getUser().getIdentifier());

                        cashVO.setValue(saleReceiptModel.getValue());

                        cashVO.setIdentifier(database.cashDAO().create(cashVO).intValue());

                        SaleReceiptCashVO saleReceiptCash = new SaleReceiptCashVO();

                        saleReceiptCash.setSale(saleReceiptModel.getSale().getIdentifier());

                        saleReceiptCash.setReceipt(saleReceiptModel.getReceipt());

                        saleReceiptCash.setCash(cashVO.getIdentifier());

                        database.saleReceiptCashDAO().create(saleReceiptCash);

                        break;

                }

            }

            //LANCA O TROCO DA VENDA, CASO EXISTA
            Double change = database.saleDAO().getToReceiveAsDouble(saleModel.getIdentifier());

            if (change < 0) {

                CashVO cashVO = new CashVO();

                cashVO.setDateTime(new Date());

                cashVO.setOperation("TRC");

                cashVO.setType("S");

                cashVO.setNote("Venda Nº " + saleModel.getIdentifier());

                cashVO.setUser(saleModel.getUser().getIdentifier());

                cashVO.setValue(change * -1);

                cashVO.setIdentifier(database.cashDAO().create(cashVO).intValue());

                SaleReceiptCashVO saleReceiptCash = new SaleReceiptCashVO();

                saleReceiptCash.setSale(saleModel.getIdentifier());

                saleReceiptCash.setReceipt(1);

                saleReceiptCash.setCash(cashVO.getIdentifier());

                database.saleReceiptCashDAO().create(saleReceiptCash);

            }

            database.setTransactionSuccessful();

            return saleModel.getIdentifier();

        } catch(Exception e) {

            e.printStackTrace();

            return new InternalException();

        } finally {

            if (database.inTransaction())

                database.endTransaction();

        }

    }


    protected void onPostExecute(Object callResult) {

        L listener = this.listener.get();

        if (listener != null) {

            if (callResult instanceof Integer) {

                listener.onFinalizeSaleSuccess((Integer) callResult);

            } else {

                if (callResult instanceof Messaging) {

                    listener.onFinalizeSaleFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onFinalizeSaleSuccess(Integer sale);

        void onFinalizeSaleFailure(Messaging messaging);

    }


}