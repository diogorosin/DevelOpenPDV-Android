package br.com.developen.pdv.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SaleReceiptCashDAO {

    @Insert
    void create(SaleReceiptCashVO saleReceiptCashVO);

    @Query("SELECT * FROM SaleReceiptCash WHERE sale = :sale AND receipt = :receipt AND cash = :cash")
    SaleReceiptCashVO retrieve(int sale, int receipt,int cash);

    @Query("SELECT COUNT(*) > 0 FROM SaleReceiptCash WHERE sale = :sale AND receipt = :receipt AND cash = :cash")
    Boolean exists(int sale, int receipt, int cash);

    @Update
    void update(SaleReceiptCashVO saleReceiptCashVO);

    @Delete
    void delete(SaleReceiptCashVO saleReceiptCashVO);

}