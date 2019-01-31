package br.com.developen.pdv.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SaleReceiptDAO {

    @Insert
    void create(SaleReceiptVO saleReceiptVO);

    @Query("SELECT SR.* " +
            "FROM SaleReceipt SR " +
            "WHERE SR.sale = :sale AND SR.receipt = :receipt")
    SaleReceiptVO retrieve(int sale, int receipt);

    @Query("SELECT COUNT(*) > 0 " +
            "FROM SaleReceipt SR " +
            "WHERE SR.sale = :sale AND SR.receipt = :receipt")
    Boolean exists(int sale, int receipt);

    @Update
    void update(SaleReceiptVO saleReceiptVO);

    @Delete
    void delete(SaleReceiptVO saleReceiptVO);

    @Query("SELECT " +
            "Sle.identifier AS 'sale_identifier', " +
            "Sle.dateTime AS 'sale_dateTime', " +
            "Sbj.identifier AS 'sale_user_identifier', " +
            "Sbj.active AS 'sale_user_active', " +
            "Sbj.level AS 'sale_user_level', " +
            "Ind.name AS 'sale_user_name', " +
            "Usr.login AS 'sale_user_login', " +
            "Usr.password AS 'sale_user_password', " +
            "SleRpt.receipt AS 'receipt', " +
            "RptMth.identifier AS 'receiptMethod_identifier', " +
            "RptMth.denomination AS 'receiptMethod_denomination', " +
            "SleRpt.value AS 'value' " +
            "FROM " +
            "SaleReceipt SleRpt " +
            "INNER JOIN " +
            "Sale Sle ON Sle.identifier = SleRpt.sale " +
            "INNER JOIN " +
            "User Usr ON Usr.individual = Sle.user " +
            "INNER JOIN " +
            "Individual Ind ON Ind.subject = Usr.individual " +
            "INNER JOIN " +
            "Subject Sbj ON Sbj.identifier = Ind.subject " +
            "INNER JOIN " +
            "ReceiptMethod RptMth ON RptMth.identifier = SleRpt.receiptMethod " +
            "WHERE Sle.identifier = :sale " +
            "ORDER BY SleRpt.receipt")
    LiveData<List<SaleReceiptModel>> getReceipts(Integer sale);

}