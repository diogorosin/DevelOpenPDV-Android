package br.com.developen.pdv.report.layout;

import java.util.List;

import br.com.developen.pdv.room.SaleableModel;

public abstract class SaleablesLayout implements Layout {

    private String reportName;

    private List<SaleableModel> rows;

    public String getReportName() {

        return reportName;

    }

    public void setReportName(String reportName) {

        this.reportName = reportName;

    }

    public List<SaleableModel> getRows() {

        return rows;

    }

    public void setRows(List<SaleableModel> rows) {

        this.rows = rows;

    }

}