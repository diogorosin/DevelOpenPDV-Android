package br.com.developen.pdv.report.task;

import android.os.AsyncTask;
import android.os.Process;
import android.pt.printer.Printer;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.developen.pdv.report.Report;
import br.com.developen.pdv.report.ReportName;
import br.com.developen.pdv.report.adapter.PrintListener;
import br.com.developen.pdv.report.layout.PT7003HeaderLayout;
import br.com.developen.pdv.report.layout.PT7003SalesByCatalogLayout;
import br.com.developen.pdv.room.SaleDAO;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.DB;

public class PT7003PrintSalesByCatalogAsyncTask<
        A extends PrintListener,
        B extends Map,
        C extends Integer,
        D extends Void> extends AsyncTask<B, C, D>  {


    private WeakReference<A> listener;

    private PT7003HeaderLayout header;

    private PT7003SalesByCatalogLayout body;

    private Printer printer;


    public PT7003PrintSalesByCatalogAsyncTask(A listener,
                                              String title,
                                              String subtitle,
                                              Date dateTime,
                                              String deviceAlias){

        this.listener = new WeakReference<>(listener);

        this.printer = new Printer();

        this.header = new PT7003HeaderLayout(this.printer);

        this.header.setTitle(title);

        this.header.setSubtitle(subtitle);

        this.header.setAlias(deviceAlias);

        this.header.setDateTime(dateTime);

        this.body = new PT7003SalesByCatalogLayout(this.printer);

        this.body.setReportName("VENDAS POR CATEGORIA");

    }


    protected void onPreExecute() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintPreExecute(ReportName.SALES_BY_CATALOG);

    }


    protected Void doInBackground(Map... params) {

        Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT);

        Map parameters = params[0];

        Date periodStart = (Date) parameters.get(Report.PERIOD_START_PARAM);

        Date periodEnd = (Date) parameters.get(Report.PERIOD_END_PARAM);

        this.body.setPeriodStart(periodStart);

        this.body.setPeriodEnd(periodEnd);

        DB database = DB.getInstance(App.getInstance());

        List<SaleDAO.SalesByCatalogBean> salesByCatalog;

        if (periodStart.equals(periodEnd))

            salesByCatalog = database.
                    saleDAO().getSalesByCatalogOfDateAsList(periodStart);

        else

            salesByCatalog = database.
                    saleDAO().getSalesByCatalogOfPeriodAsList(periodStart, periodEnd);

        this.body.setRows(salesByCatalog);

        if (this.body.getRows() != null && !this.body.getRows().isEmpty()) {

            A l = listener.get();

            if (l != null)

                l.onPrintProgressInitialize(
                        ReportName.SALES_BY_CATALOG,
                        0, 1);

            try {

                this.printer.open();

                this.printer.init();

                this.header.print();

                this.body.print();

            } finally {

                printer.close();

            }

        }

        return null;

    }


    protected void onPostExecute(Void voids) {

        A l = this.listener.get();

        if (l == null)

            return;

        l.onPrintSuccess(ReportName.SALES_BY_CATALOG);

    }


    protected void onProgressUpdate(Integer... progress) {

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressUpdate(ReportName.SALES_BY_CATALOG, progress[0]);

    }


    protected void onCancelled() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintCancelled(ReportName.SALES_BY_CATALOG);

    }


}