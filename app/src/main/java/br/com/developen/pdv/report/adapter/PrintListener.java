package br.com.developen.pdv.report.adapter;

import br.com.developen.pdv.report.ReportName;
import br.com.developen.pdv.utils.Messaging;

public interface PrintListener {

    void onPrintPreExecute(ReportName report);

    void onPrintProgressInitialize(ReportName report, int progress, int max);

    void onPrintProgressUpdate(ReportName report, int status);

    void onPrintSuccess(ReportName report);

    void onPrintFailure(ReportName report, Messaging message);

    void onPrintCancelled(ReportName report);

}