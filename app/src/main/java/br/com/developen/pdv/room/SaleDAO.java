package br.com.developen.pdv.room;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import static br.com.developen.pdv.utils.Constants.GET_RECEIVED_OF_SALE;
import static br.com.developen.pdv.utils.Constants.GET_SALES;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_CATALOG_OF_DATE;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_CATALOG_OF_PERIOD;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_PERIOD_OF_MONTH;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_PERIOD_OF_DATE;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_PERIOD_OF_WEEK;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_PROGENY_OF_MONTH;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_PROGENY_OF_PERIOD;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_PROGENY_OF_DATE;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_PROGENY_OF_WEEK;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_USER_OF_MONTH;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_USER_OF_DATE;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_USER_OF_PERIOD;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_USER_OF_WEEK;
import static br.com.developen.pdv.utils.Constants.GET_SALE_BILLING_OF_MONTH;
import static br.com.developen.pdv.utils.Constants.GET_SALE_BILLING_OF_DATE;
import static br.com.developen.pdv.utils.Constants.GET_SALE_BILLING_OF_WEEK;
import static br.com.developen.pdv.utils.Constants.GET_SALE_BY_IDENTIFIER;
import static br.com.developen.pdv.utils.Constants.GET_SALE_COUNT_OF_MONTH;
import static br.com.developen.pdv.utils.Constants.GET_SALE_COUNT_OF_DATE;
import static br.com.developen.pdv.utils.Constants.GET_SALE_COUNT_OF_WEEK;
import static br.com.developen.pdv.utils.Constants.GET_SUBTOTAL_OF_SALE;
import static br.com.developen.pdv.utils.Constants.GET_TICKET_COUNT_OF_MONTH;
import static br.com.developen.pdv.utils.Constants.GET_TICKET_COUNT_OF_DATE;
import static br.com.developen.pdv.utils.Constants.GET_TICKET_COUNT_OF_WEEK;
import static br.com.developen.pdv.utils.Constants.GET_TOTAL_OF_SALE;
import static br.com.developen.pdv.utils.Constants.GET_TO_RECEIVE_OF_SALE;

@Dao
public interface SaleDAO {

    @Insert
    Long create(SaleVO saleVO);

    @Query("SELECT S.* FROM Sale S WHERE S.identifier = :identifier")
    SaleVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 FROM Sale S WHERE S.identifier = :identifier")
    Boolean exists(int identifier);

    @Query("SELECT MAX(number) FROM Sale")
    Integer getCurrentSaleNumber();

    @Query("SELECT identifier FROM Sale WHERE uploaded = 0")
    List<Integer> getSalesToUpload();

    @Update
    void update(SaleVO saleVO);

    @Query("UPDATE Sale SET uploaded = 1 WHERE identifier IN (:sales)")
    void setUploaded(Integer... sales);

    @Delete
    void delete(SaleVO saleVO);

    @Query(GET_SALE_BY_IDENTIFIER)
    SaleModel getSaleByIdentifier(Integer identifier);

    @Query(GET_SUBTOTAL_OF_SALE)
    LiveData<Double> getSubtotalOfSale(Integer sale);

    @Query(GET_TOTAL_OF_SALE)
    LiveData<Double> getTotalOfSale(Integer sale);

    @Query(GET_SALES)
    DataSource.Factory<Integer, SaleModel> getSalesPaged();

    @Query(GET_TOTAL_OF_SALE)
    Double getTotalOfSaleAsDouble(Integer sale);

    @Query(GET_RECEIVED_OF_SALE)
    LiveData<Double> getReceivedOfSale(Integer sale);

    @Query(GET_RECEIVED_OF_SALE)
    Double getReceivedOfSaleAsDouble(Integer sale);

    @Query(GET_TO_RECEIVE_OF_SALE)
    LiveData<Double> getToReceiveOfSale(Integer sale);

    @Query(GET_TO_RECEIVE_OF_SALE)
    Double getToReceiveOfSaleAsDouble(Integer sale);

    /* GRAPHS MONTH */

    @Query(GET_SALES_BY_PERIOD_OF_MONTH)
    LiveData<List<SalesByPeriodBean>> getSalesByPeriodOfMonth();

    @Query(GET_SALES_BY_PROGENY_OF_MONTH)
    LiveData<List<SalesByProgenyBean>> getSalesByProgenyOfMonth();

    @Query(GET_SALES_BY_USER_OF_MONTH)
    LiveData<List<SalesByUserBean>> getSalesByUserOfMonth();

    @Query(GET_SALE_BILLING_OF_MONTH)
    LiveData<Double> getSaleBillingOfMonth();

    @Query(GET_SALE_COUNT_OF_MONTH)
    LiveData<Integer> getSaleCountOfMonth();

    @Query(GET_TICKET_COUNT_OF_MONTH)
    LiveData<Integer> getTicketCountOfMonth();

    /* GRAPHS WEEK */

    @Query(GET_SALES_BY_PERIOD_OF_WEEK)
    LiveData<List<SalesByPeriodBean>> getSalesByPeriodOfWeek();

    @Query(GET_SALES_BY_PROGENY_OF_WEEK)
    LiveData<List<SalesByProgenyBean>> getSalesByProgenyOfWeek();

    @Query(GET_SALES_BY_USER_OF_WEEK)
    LiveData<List<SalesByUserBean>> getSalesByUserOfWeek();

    @Query(GET_SALE_BILLING_OF_WEEK)
    LiveData<Double> getSaleBillingOfWeek();

    @Query(GET_SALE_COUNT_OF_WEEK)
    LiveData<Integer> getSaleCountOfWeek();

    @Query(GET_TICKET_COUNT_OF_WEEK)
    LiveData<Integer> getTicketCountOfWeek();

    /* GRAPHS TODAY */

    @Query(GET_SALES_BY_PERIOD_OF_DATE)
    LiveData<List<SalesByPeriodBean>> getSalesByPeriodOfDate(
            @TypeConverters({TimestampConverter.class}) Date date);

    @Query(GET_SALES_BY_PROGENY_OF_DATE)
    LiveData<List<SalesByProgenyBean>> getSalesByProgenyOfDate(
            @TypeConverters({TimestampConverter.class}) Date date);

    @Query(GET_SALES_BY_PROGENY_OF_DATE)
    List<SalesByProgenyBean> getSalesByProgenyOfDateAsList(
            @TypeConverters({TimestampConverter.class}) Date date);

    @Query(GET_SALES_BY_PROGENY_OF_PERIOD)
    List<SalesByProgenyBean> getSalesByProgenyOfPeriodAsList(
            @TypeConverters({TimestampConverter.class}) Date start,
            @TypeConverters({TimestampConverter.class}) Date end);

    @Query(GET_SALES_BY_USER_OF_DATE)
    LiveData<List<SalesByUserBean>> getSalesByUserOfDate(
            @TypeConverters({TimestampConverter.class}) Date date);

    @Query(GET_SALES_BY_USER_OF_DATE)
    List<SalesByUserBean> getSalesByUserOfDateAsList(
            @TypeConverters({TimestampConverter.class}) Date date);

    @Query(GET_SALES_BY_USER_OF_PERIOD)
    List<SalesByUserBean> getSalesByUserOfPeriodAsList(
            @TypeConverters({TimestampConverter.class}) Date start,
            @TypeConverters({TimestampConverter.class}) Date end);

    @Query(GET_SALES_BY_CATALOG_OF_DATE)
    List<SalesByCatalogBean> getSalesByCatalogOfDateAsList(
            @TypeConverters({TimestampConverter.class}) Date date);

    @Query(GET_SALES_BY_CATALOG_OF_PERIOD)
    List<SalesByCatalogBean> getSalesByCatalogOfPeriodAsList(
            @TypeConverters({TimestampConverter.class}) Date start,
            @TypeConverters({TimestampConverter.class}) Date end);

    @Query(GET_SALE_BILLING_OF_DATE)
    LiveData<Double> getSaleBillingOfDate(
            @TypeConverters({TimestampConverter.class}) Date date);

    @Query(GET_SALE_COUNT_OF_DATE)
    LiveData<Integer> getSaleCountOfDate(
            @TypeConverters({TimestampConverter.class}) Date date);

    @Query(GET_TICKET_COUNT_OF_DATE)
    LiveData<Integer> getTicketCountOfDate(
            @TypeConverters({TimestampConverter.class}) Date date);


    class SalesByPeriodBean {

        private String period;

        private Double total;

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

    }


    class SalesByProgenyBean {

        private Integer progeny;

        private String reference;

        private String label;

        private Double quantity;

        private Double total;

        public Integer getProgeny() { return progeny; }

        public void setProgeny(Integer progeny) {
            this.progeny = progeny;
        }

        public String getReference() { return reference; }

        public void setReference(String reference) { this.reference = reference; }

        public String getLabel() { return label; }

        public void setLabel(String label) { this.label = label; }

        public Double getQuantity() { return quantity; }

        public void setQuantity(Double quantity) { this.quantity = quantity; }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

    }


    class SalesByUserBean {

        private Integer user;

        private String name;

        private Double percentage;

        private Double total;

        public Integer getUser() {
            return user;
        }

        public void setUser(Integer user) {
            this.user = user;
        }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public Double getPercentage() { return percentage; }

        public void setPercentage(Double percentage) { this.percentage = percentage; }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

    }


    class SalesByCatalogBean {

        private Integer catalog;

        private String denomination;

        private Double percentage;

        private Double total;

        public Integer getCatalog() { return catalog; }

        public void setCatalog(Integer catalog) { this.catalog = catalog; }

        public String getDenomination() { return denomination; }

        public void setDenomination(String denomination) { this.denomination = denomination; }

        public Double getPercentage() { return percentage; }

        public void setPercentage(Double percentage) { this.percentage = percentage; }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

    }


}