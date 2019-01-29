package br.com.developen.pdv.room;

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

}