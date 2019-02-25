package br.com.developen.pdv.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static br.com.developen.pdv.utils.Constants.GET_RECEIVED_OF_SALE;
import static br.com.developen.pdv.utils.Constants.GET_SALES;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_PERIOD_OF_TODAY;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_PROGENY_OF_TODAY;
import static br.com.developen.pdv.utils.Constants.GET_SALES_BY_USER_OF_TODAY;
import static br.com.developen.pdv.utils.Constants.GET_SALE_BILLING_OF_TODAY;
import static br.com.developen.pdv.utils.Constants.GET_SALE_BY_IDENTIFIER;
import static br.com.developen.pdv.utils.Constants.GET_SALE_COUNT_OF_TODAY;
import static br.com.developen.pdv.utils.Constants.GET_SUBTOTAL_OF_SALE;
import static br.com.developen.pdv.utils.Constants.GET_TICKET_COUNT_OF_TODAY;
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

    @Update
    void update(SaleVO saleVO);

    @Delete
    void delete(SaleVO saleVO);

    @Query(GET_SALE_BY_IDENTIFIER)
    SaleModel getSaleByIdentifier(Integer identifier);

    @Query(GET_SUBTOTAL_OF_SALE)
    LiveData<Double> getSubtotalOfSale(Integer sale);

    @Query(GET_TOTAL_OF_SALE)
    LiveData<Double> getTotalOfSale(Integer sale);

    @Query(GET_SALES)
    List<SaleModel> getSales();

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

    @Query(GET_SALES_BY_PERIOD_OF_TODAY)
    LiveData<List<SalesByPeriodBean>> getSalesByPeriodOfToday();

    @Query(GET_SALES_BY_PROGENY_OF_TODAY)
    LiveData<List<SalesByProgenyBean>> getSalesByProgenyOfToday();

    @Query(GET_SALES_BY_USER_OF_TODAY)
    LiveData<List<SalesByUserBean>> getSalesByUserOfToday();

    @Query(GET_SALE_BILLING_OF_TODAY)
    LiveData<Double> getSaleBillingOfToday();

    @Query(GET_SALE_COUNT_OF_TODAY)
    LiveData<Integer> getSaleCountOfToday();

    @Query(GET_TICKET_COUNT_OF_TODAY)
    LiveData<Integer> getTicketCountOfToday();

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

        private Double total;

        public Integer getProgeny() {
            return progeny;
        }

        public void setProgeny(Integer progeny) {
            this.progeny = progeny;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

    }

    class SalesByUserBean {

        private Integer user;

        private Double total;

        public Integer getUser() {
            return user;
        }

        public void setUser(Integer user) {
            this.user = user;
        }

        public Double getTotal() {
            return total;
        }

        public void setTotal(Double total) {
            this.total = total;
        }

    }

}