package br.com.developen.pdv.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SaleItemDAO {

    @Insert
    void create(SaleItemVO saleItemVO);

    @Query("SELECT SI.* " +
            "FROM SaleItem SI " +
            "WHERE SI.sale = :sale AND SI.item = :item")
    SaleItemVO retrieve(int sale, int item);

    @Query("SELECT COUNT(*) > 0 " +
            "FROM SaleItem SI " +
            "WHERE SI.sale = :sale AND SI.item = :item")
    Boolean exists(int sale, int item);

    @Update
    void update(SaleItemVO saleItemVO);

    @Delete
    void delete(SaleItemVO saleItemVO);

}