package br.com.developen.pdv.report.layout;

import java.util.Date;
import java.util.List;

import br.com.developen.pdv.room.SaleDAO;

public abstract class SalesByCatalogLayout implements Layout {

    private String reportName;

    private Date periodStart;

    private Date periodEnd;

    private List<SaleDAO.SalesByCatalogBean> rows;


    public String getReportName() {

        return reportName;

    }

    public void setReportName(String reportName) {

        this.reportName = reportName;

    }

    public Date getPeriodStart() {

        return periodStart;

    }

    public void setPeriodStart(Date periodStart) {

        this.periodStart = periodStart;

    }

    public Date getPeriodEnd() {

        return periodEnd;

    }

    public void setPeriodEnd(Date periodEnd) {

        this.periodEnd = periodEnd;

    }

    public List<SaleDAO.SalesByCatalogBean> getRows() {

        return rows;

    }

    public void setRows(List<SaleDAO.SalesByCatalogBean> rows) {

        this.rows = rows;

    }

}