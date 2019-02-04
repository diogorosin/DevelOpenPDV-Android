package br.com.developen.pdv.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SaleDAO {

    String GET_SALE_BY_IDENTIFIER =
                    "SELECT " +
                    "Sle.identifier AS 'identifier', " +
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

}