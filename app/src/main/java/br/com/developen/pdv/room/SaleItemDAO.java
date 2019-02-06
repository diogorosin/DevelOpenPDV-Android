package br.com.developen.pdv.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SaleItemDAO {

    String GET_ITEMS =
                    "SELECT " +
                    "Sle.identifier AS 'sale_identifier', " +
                    "Sle.number AS 'sale_number', " +
                    "Sle.status AS 'sale_status', " +
                    "Sle.dateTime AS 'sale_dateTime', " +
                    "Sbj.identifier AS 'sale_user_identifier', " +
                    "Sbj.active AS 'sale_user_active', " +
                    "Sbj.level AS 'sale_user_level', " +
                    "Ind.name AS 'sale_user_name', " +
                    "Usr.login AS 'sale_user_login', " +
                    "Usr.password AS 'sale_user_password', " +
                    "SleItm.item AS 'item', " +
                    "Slb.identifier AS 'saleable_identifier', " +
                    "Slb.catalog_identifier AS 'saleable_catalog_identifier', " +
                    "Slb.catalog_active AS 'saleable_catalog_active', " +
                    "Slb.catalog_position AS 'saleable_catalog_position', " +
                    "Slb.catalog_denomination AS 'saleable_catalog_denomination', " +
                    "Slb.position AS 'saleable_position', " +
                    "Slb.reference AS 'saleable_reference', " +
                    "Slb.'label' AS 'saleable_label', " +
                    "Slb.'type' AS 'saleable_type', " +
                    "MeU.identifier AS 'measureUnit_identifier', " +
                    "MeU.denomination AS 'measureUnit_denomination', " +
                    "MeU.acronym AS 'measureUnit_acronym', " +
                    "MeU.'group' AS 'measureUnit_group', " +
                    "SleItm.quantity AS 'quantity', " +
                    "SleItm.price AS 'price', " +
                    "SleItm.total AS 'total' " +
                    "FROM " +
                    "SaleItem SleItm " +
                    "INNER JOIN " +
                    "Sale Sle ON Sle.identifier = SleItm.sale " +
                    "INNER JOIN " +
                    "User Usr ON Usr.individual = Sle.user " +
                    "INNER JOIN " +
                    "Individual Ind ON Ind.subject = Usr.individual " +
                    "INNER JOIN " +
                    "Subject Sbj ON Sbj.identifier = Ind.subject " +
                    "INNER JOIN " +
                    "Saleable Slb ON Slb.identifier = SleItm.progeny " +
                    "INNER JOIN " +
                    "MeasureUnit MeU ON MeU.identifier = SleItm.measureUnit " +
                    "WHERE Sle.identifier = :sale " +
                    "ORDER BY Sle.identifier, SleItm.item";

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

    @Query("DELETE FROM SaleItem WHERE sale = :sale")
    void delete(Integer sale);

    @Query(GET_ITEMS)
    LiveData<List<SaleItemModel>> getItems(Integer sale);

    @Query(GET_ITEMS)
    List<SaleItemModel> getItemsAsList(Integer sale);

}