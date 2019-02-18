package br.com.developen.pdv.report.task;

import android.os.AsyncTask;
import android.os.Process;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.developen.pdv.report.ReportName;
import br.com.developen.pdv.report.adapter.PrintListener;
import br.com.developen.pdv.report.exception.FailPrinterException;
import br.com.developen.pdv.report.exception.InParametersErrorPrinterException;
import br.com.developen.pdv.report.exception.NoPaperPrinterException;
import br.com.developen.pdv.report.exception.TimeoutPrinterException;
import br.com.developen.pdv.report.layout.PT7003SaleItemTicketFooterLayout;
import br.com.developen.pdv.report.layout.PT7003SaleItemTicketHeaderLayout;
import br.com.developen.pdv.report.layout.PT7003SaleItemTicketLayout;
import br.com.developen.pdv.report.util.PT7003Printer;
import br.com.developen.pdv.room.SaleItemTicketModel;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.Messaging;

public class PT7003PrintTicketsOfSaleAsyncTask<
        A extends PrintListener,
        B extends Integer,
        C extends Integer,
        D extends Map> extends AsyncTask<B, C, D>  {


    private static final int ERROR_PROPERTY = 0;

    private static final int SALE_PROPERTY = 1;


    private PT7003SaleItemTicketHeaderLayout header;

    private PT7003SaleItemTicketFooterLayout footer;

    private PT7003Printer printer;

    private WeakReference<A> listener;

    private String userName;

    private String deviceAlias;


    public PT7003PrintTicketsOfSaleAsyncTask(A listener,
                                             String title,
                                             String subtitle,
                                             String deviceAlias,
                                             String userName,
                                             String note,
                                             String footer){

        this.listener = new WeakReference<>(listener);

        this.deviceAlias = deviceAlias;

        this.userName = userName;

        this.printer = new PT7003Printer();

        this.header = new PT7003SaleItemTicketHeaderLayout(printer);

        this.header.setTitle(title);

        this.header.setSubtitle(subtitle);

        this.footer = new PT7003SaleItemTicketFooterLayout(printer);

        this.footer.setNote(note);

        this.footer.setFooter(footer);

    }


    protected void onPreExecute() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintPreExecute(ReportName.SALE_ITEM_COUPON);

    }


    protected Map doInBackground(Integer... params) {

        Integer sale = params[0];

        Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT);

        DB database = DB.getInstance(App.getInstance());

        int printedTicketsCountOfSale = database.saleItemTicketDAO().getPrintedTicketsCountOfSale(sale);

        List<SaleItemTicketModel> saleItemTicketModels = database.
                saleItemTicketDAO().
                getTicketsOfSale(sale);

        A l = listener.get();

        if (l != null)

            l.onPrintProgressInitialize(
                    ReportName.SALE_ITEM_COUPON,
                    printedTicketsCountOfSale,
                    saleItemTicketModels.size());

        try {

            printer.open();

            printer.init();

            for (SaleItemTicketModel saleItemTicketModel : saleItemTicketModels) {

                if (!saleItemTicketModel.getPrinted()) {

                    //IMPRIME O CABECALHO DO TICKET
                    header.print();

                    //IMPRIME O CORPO DO TICKET
                    PT7003SaleItemTicketLayout body = new PT7003SaleItemTicketLayout(printer);

                    body.setDeviceAlias(deviceAlias);

                    body.setSale(saleItemTicketModel.getSaleItem().getSale().getNumber());

                    body.setItem(saleItemTicketModel.getSaleItem().getItem());

                    body.setTicket(saleItemTicketModel.getTicket());

                    body.setOf(saleItemTicketModel.getOf());

                    body.setUserName(userName);

                    body.setDateTime(new Date());

                    body.setLabel(saleItemTicketModel.getLabel());

                    body.setQuantity(saleItemTicketModel.getQuantity());

                    body.setMeasureUnit(saleItemTicketModel.getMeasureUnit().getAcronym());

                    body.print();

                    //IMPRIME O RODAPE DO TICKET
                    footer.print();

                    //DEFINE O TICKET COMO IMPRESSO.
                    database.saleItemTicketDAO().setTicketAsPrinted(
                            saleItemTicketModel.getSaleItem().getSale().getIdentifier(),
                            saleItemTicketModel.getSaleItem().getItem(),
                            saleItemTicketModel.getTicket());

                    //ATUALIZA O STATUS
                    if (l != null)

                        l.onPrintProgressUpdate(ReportName.SALE_ITEM_COUPON, 1);

                }

            }

            printer.printString(" ");

        } catch (NoPaperPrinterException |
                FailPrinterException |
                InParametersErrorPrinterException |
                TimeoutPrinterException e) {

            Map<Integer, Object> result = new HashMap<>();

            result.put(ERROR_PROPERTY, e);

            result.put(SALE_PROPERTY, sale);

            return result;

        } finally {

            printer.close();

        }

        return new HashMap<>();

    }


    protected void onPostExecute(Map callResult) {

        A listener = this.listener.get();

        if (listener != null) {

            if (callResult instanceof Map) {

                Map<Integer, Object> map = (Map<Integer, Object>) callResult;

                if (map.containsKey(ERROR_PROPERTY)){

                    listener.onPrintFailure(
                            ReportName.SALE_ITEM_COUPON,
                            (Messaging) map.get(ERROR_PROPERTY));

                } else {

                    listener.onPrintSuccess(
                            ReportName.SALE_ITEM_COUPON);

                }

            }

        }

    }


    protected void onProgressUpdate(Integer... progress) {

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressUpdate(ReportName.SALE_ITEM_COUPON, progress[0]);

    }


    protected void onCancelled() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintCancelled(ReportName.SALE_ITEM_COUPON);

    }


}