package br.com.developen.pdv.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SaleReceiptCashDAO {

    String GET_SALE_RECEIPT_CASH_LIST_BY_SALE =
            "SELECT " +
            "Sle.identifier AS 'saleReceipt_sale_identifier', " +
            "Sle.number AS 'saleReceipt_sale_number', " +
            "Sle.status AS 'saleReceipt_sale_status', " +
            "Sle.dateTime AS 'saleReceipt_sale_dateTime', " +
            "Sbj.identifier AS 'saleReceipt_sale_user_identifier', " +
            "Sbj.active AS 'saleReceipt_sale_user_active', " +
            "Sbj.level AS 'saleReceipt_sale_user_level', " +
            "Ind.name AS 'saleReceipt_sale_user_name', " +
            "Usr.login AS 'saleReceipt_sale_user_login', " +
            "Usr.password AS 'saleReceipt_sale_user_password', " +
            "SleRpt.receipt AS 'saleReceipt_receipt', " +
            "RptMth.identifier AS 'saleReceipt_receiptMethod_identifier', " +
            "RptMth.denomination AS 'saleReceipt_receiptMethod_denomination', " +
            "SleRpt.value AS 'saleReceipt_value', " +
            "Cas.identifier AS 'cash_identifier', " +
            "Cas.operation AS 'cash_operation', " +
            "Cas.type AS 'cash_type', " +
            "Cas.value AS 'cash_value', " +
            "Cas.note AS 'cash_note', " +
            "CasUsrIndSub.identifier AS 'cash_user_identifier', " +
            "CasUsrIndSub.active AS 'cash_user_active', " +
            "CasUsrIndSub.level AS 'cash_user_level', " +
            "CasUsrInd.name AS 'cash_user_name', " +
            "CasUsr.login AS 'cash_user_login', " +
            "CasUsr.password AS 'cash_user_password', " +
            "Rev.identifier AS 'reversal_identifier', " +
            "Rev.operation AS 'reversal_operation', " +
            "Rev.type AS 'reversal_type', " +
            "Rev.value AS 'reversal_value', " +
            "Rev.note AS 'reversal_note', " +
            "RevUsrIndSub.identifier AS 'reversal_user_identifier', " +
            "RevUsrIndSub.active AS 'reversal_user_active', " +
            "RevUsrIndSub.level AS 'reversal_user_level', " +
            "RevUsrInd.name AS 'reversal_user_name', " +
            "RevUsr.login AS 'reversal_user_login', " +
            "RevUsr.password AS 'reversal_user_password' " +
            "FROM " +
            "SaleReceiptCash SleRptCas " +
            "INNER JOIN " +
            "SaleReceipt SleRpt ON SleRpt.sale = SleRptCas.sale AND SleRpt.receipt = SleRptCas.receipt " +
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
            "INNER JOIN " +
            "Cash Cas ON Cas.identifier = SleRptCas.cash " +
            "INNER JOIN " +
            "User CasUsr ON CasUsr.individual = Cas.user " +
            "INNER JOIN " +
            "Individual CasUsrInd on CasUsrInd.subject = CasUsr.individual " +
            "INNER JOIN " +
            "Subject CasUsrIndSub on CasUsrIndSub.identifier = CasUsrInd.subject " +
            "LEFT OUTER JOIN " +
            "Cash Rev ON Rev.identifier = SleRptCas.reversal " +
            "LEFT OUTER JOIN " +
            "User RevUsr ON RevUsr.individual = Rev.user " +
            "LEFT OUTER JOIN " +
            "Individual RevUsrInd on RevUsrInd.subject = RevUsr.individual " +
            "LEFT OUTER JOIN " +
            "Subject RevUsrIndSub on RevUsrIndSub.identifier = RevUsrInd.subject " +
            "WHERE Sle.identifier = :sale "+
            "ORDER BY SleRpt.receipt";


    @Insert
    void create(SaleReceiptCashVO saleReceiptCashVO);

    @Query("SELECT * FROM SaleReceiptCash WHERE sale = :sale AND receipt = :receipt AND cash = :cash")
    SaleReceiptCashVO retrieve(int sale, int receipt, int cash);

    @Query("SELECT COUNT(*) > 0 FROM SaleReceiptCash WHERE sale = :sale AND receipt = :receipt AND cash = :cash")
    Boolean exists(int sale, int receipt, int cash);

    @Update
    void update(SaleReceiptCashVO saleReceiptCashVO);

    @Delete
    void delete(SaleReceiptCashVO saleReceiptCashVO);

    @Query(GET_SALE_RECEIPT_CASH_LIST_BY_SALE)
    List<SaleReceiptCashModel> getListBySale(int sale);


}