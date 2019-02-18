package br.com.developen.pdv.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SaleDAO {

    String GET_SALE_COUNT_OF_TODAY =
                    "SELECT " +
                    "COUNT(*) " +
                    "FROM " +
                    "Sale Sle " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now')";

    String GET_SALE_BILLING_OF_TODAY =
                    "SELECT " +
                    "IFNULL(SUM(SleItm.total), 0) " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now')";

    String GET_TICKET_COUNT_OF_TODAY =
                    "SELECT " +
                    "COUNT(*) " +
                    "FROM " +
                    "SaleItemTicket SleItmTkt " +
                    "INNER JOIN " +
                    "SaleItem SleItm ON SleItm.sale = SleItmTkt.sale AND SleItm.item = SleItmTkt.item " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now') AND SleItmTkt.printed = 1";

    String GET_SALES_BY_PERIOD_OF_TODAY =
                    "SELECT " +
                    "STRFTIME('%H', Sle.dateTime) AS 'period', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now') " +
                    "GROUP BY " +
                    "1";

    String GET_SALES_BY_PROGENY_OF_TODAY =
                    "SELECT " +
                    "Slb.identifier AS 'progeny', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "INNER JOIN " +
                    "Saleable Slb ON Slb.identifier = SleItm.progeny " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now') " +
                    "GROUP BY " +
                    "1 " +
                    "ORDER BY " +
                    "2 DESC";

    String GET_SALES_BY_USER_OF_TODAY =
                    "SELECT " +
                    "Usr.individual AS 'user', " +
                    "SUM(SleItm.total) AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "INNER JOIN " +
                    "User Usr ON Usr.individual = Sle.user " +
                    "WHERE " +
                    "Sle.status = 'F' AND DATE(Sle.dateTime) = DATE('now') " +
                    "GROUP BY " +
                    "1 " +
                    "ORDER BY " +
                    "2 DESC";

    String GET_SALE_BY_IDENTIFIER =
                    "SELECT " +
                    "Sle.identifier AS 'identifier', " +
                    "Sle.number AS 'number', " +
                    "Sle.status AS 'status', " +
                    "Sle.dateTime AS 'dateTime', " +
                    "Sbj.identifier AS 'user_identifier', " +
                    "Sbj.active AS 'user_active', " +
                    "Sbj.level AS 'user_level', " +
                    "Ind.name AS 'user_name' " +
                    "FROM " +
                    "Sale Sle " +
                    "INNER JOIN " +
                    "User Usr ON Usr.individual = Sle.user " +
                    "INNER JOIN " +
                    "Individual Ind ON Ind.subject = Usr.individual " +
                    "INNER JOIN " +
                    "Subject Sbj ON Sbj.identifier = Ind.subject " +
                    "WHERE " +
                    "Sle.identifier = :identifier";

    String GET_RECEIVED =
                    "SELECT " +
                    "IFNULL(SUM(SleRpt.value), 0) " +
                    "FROM " +
                    "SaleReceipt SleRpt " +
                    "WHERE " +
                    "SleRpt.sale = :sale";

    String GET_TO_RECEIVE =
                    "SELECT " +
                    "(SELECT IFNULL(SUM(SleItm.total), 0) FROM SaleItem SleItm WHERE SleItm.sale = :sale)" +
                    " - " +
                    "(SELECT IFNULL(SUM(SleRpt.value), 0) FROM SaleReceipt SleRpt WHERE SleRpt.sale = :sale)";

    String GET_TOTAL =
                    "SELECT " +
                    "IFNULL(SUM(SleItm.total), 0) " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN Sale Sle ON Sle.identifier = SleItm.sale " +
                    "WHERE Sle.identifier = :sale";

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

    @Query("SELECT " +
            "IFNULL(SUM(SleItm.total), 0) " +
            "FROM " +
            "SaleItem SleItm " +
            "INNER JOIN Sale Sle ON Sle.identifier = SleItm.sale " +
            "WHERE Sle.identifier = :sale")
    LiveData<Double> getSubtotal(Integer sale);

    @Query(GET_TOTAL)
    LiveData<Double> getTotal(Integer sale);

    @Query(GET_TOTAL)
    Double getTotalAsDouble(Integer sale);

    @Query(GET_RECEIVED)
    LiveData<Double> getReceived(Integer sale);

    @Query(GET_RECEIVED)
    Double getReceivedAsDouble(Integer sale);

    @Query(GET_TO_RECEIVE)
    LiveData<Double> getToReceive(Integer sale);

    @Query(GET_TO_RECEIVE)
    Double getToReceiveAsDouble(Integer sale);

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