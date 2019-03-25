package br.com.developen.pdv.report.task;

import android.os.AsyncTask;
import android.os.Process;
import android.pt.printer.Printer;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.developen.pdv.report.ReportName;
import br.com.developen.pdv.report.adapter.PrintListener;
import br.com.developen.pdv.report.layout.PT7003HeaderLayout;
import br.com.developen.pdv.report.layout.PT7003SaleablesLayout;
import br.com.developen.pdv.room.SaleableModel;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.DB;

public class PT7003PrintSaleablesAsyncTask<
        A extends PrintListener,
        B extends Map,
        C extends Integer,
        D extends Void> extends AsyncTask<B, C, D>  {


    private WeakReference<A> listener;

    private PT7003HeaderLayout header;

    private PT7003SaleablesLayout body;

    private Printer printer;


    public PT7003PrintSaleablesAsyncTask(A listener,
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

        this.body = new PT7003SaleablesLayout(this.printer);

        this.body.setReportName("RELACAO DE PRODUTOS/SERVICOS");

    }


    protected void onPreExecute() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintPreExecute(ReportName.SALEABLES);

    }


    protected Void doInBackground(Map... params) {

        Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT);

        DB database = DB.getInstance(App.getInstance());

        List<SaleableModel> saleables = database.
                saleableDAO().getSaleables();

        this.body.setRows(saleables);

        if (this.body.getRows() != null && !this.body.getRows().isEmpty()) {

            A l = listener.get();

            if (l != null)

                l.onPrintProgressInitialize(
                        ReportName.SALEABLES,
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

        l.onPrintSuccess(ReportName.SALEABLES);

    }


    protected void onProgressUpdate(Integer... progress) {

        A l = this.listener.get();

        if (l != null)

            l.onPrintProgressUpdate(ReportName.SALEABLES, progress[0]);

    }


    protected void onCancelled() {

        A l = this.listener.get();

        if (l != null)

            l.onPrintCancelled(ReportName.SALEABLES);

    }


}