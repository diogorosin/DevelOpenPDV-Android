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

    String GET_SALE_BY_IDENTIFIER =
            "SELECT Sle.identifier AS 'identifier', " +
                   "Sle.dateTime AS 'dateTime', " +
                   "Sbj.identifier AS 'user_identifier', " +
                   "Sbj.active AS 'user_active', " +
                   "Sbj.level AS 'user_level', " +
                   "Ind.name AS 'user_name' " +
                   "FROM Sale Sle " +
                   "INNER JOIN User Usr ON Usr.individual = Sle.user " +
                   "INNER JOIN Individual Ind ON Ind.subject = Usr.individual " +
                   "INNER JOIN Subject Sbj ON Sbj.identifier = Ind.subject " +
                   "WHERE Sle.identifier = :identifier";

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

    @SuppressWarnings("AndroidUnresolvedRoomSqlReference")
    @Query("SELECT " +
            "Sle.identifier AS 'sale_identifier', " +
            "Sle.dateTime AS 'sale_dateTime', " +
            "Sbj.identifier AS 'sale_user_identifier', " +
            "Sbj.active AS 'sale_user_active', " +
            "Sbj.level AS 'sale_user_level', " +
            "Ind.name AS 'sale_user_name', " +
            "Usr.login AS 'sale_user_login', " +
            "Usr.password AS 'sale_user_password', " +
            "SleItm.item AS 'item', " +
            "Slb.identifier AS 'saleable_identifier', " +
            "Slb.active AS 'saleable_active', " +
            "Slb.catalog_identifier AS 'saleable_catalog_identifier', " +
            "Slb.catalog_active AS 'saleable_catalog_active', " +
            "Slb.catalog_position AS 'saleable_catalog_position', " +
            "Slb.catalog_denomination AS 'saleable_catalog_denomination', " +
            "Slb.position AS 'saleable_position', " +
            "Slb.reference AS 'saleable_reference', " +
            "Slb.'label' AS 'saleable_label', " +
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
            "ORDER BY Sle.identifier, SleItm.item")
    LiveData<List<SaleItemModel>> getItems(Integer sale);

    @Query("SELECT " +
            "SUM(SleItm.total) " +
            "FROM " +
            "SaleItem SleItm " +
            "INNER JOIN Sale Sle ON Sle.identifier = SleItm.sale " +
            "WHERE Sle.identifier = :sale")
    LiveData<Double> getSubtotal(Integer sale);

    @Query("SELECT " +
            "SUM(SleItm.total) " +
            "FROM " +
            "SaleItem SleItm " +
            "INNER JOIN Sale Sle ON Sle.identifier = SleItm.sale " +
            "WHERE Sle.identifier = :sale")
    LiveData<Double> getTotal(Integer sale);

}