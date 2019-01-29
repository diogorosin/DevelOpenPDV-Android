package br.com.developen.pdv.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ServiceDAO {

    @Insert
    void create(ServiceVO serviceVO);

    @Query("SELECT Ser.* " +
            "FROM Service Ser " +
            "INNER JOIN Progeny Pgn ON Pgn.identifier = Ser.progeny " +
            "WHERE Pgn.identifier = :identifier")
    ServiceVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 " +
            "FROM Service Ser " +
            "INNER JOIN Progeny Pgn ON Pgn.identifier = Ser.progeny " +
            "WHERE Pgn.identifier = :identifier")
    Boolean exists(int identifier);

    @Update
    void update(ServiceVO serviceVO);

    @Delete
    void delete(ServiceVO serviceVO);

    @Query("SELECT " +
            "Pgn.identifier AS 'identifier', " +
            "Pgn.denomination AS 'denomination', " +
            "Pgn.active AS 'active', " +
            "Catalog.identifier AS 'catalog_identifier', " +
            "Catalog.denomination AS 'catalog_denomination', " +
            "Catalog.position AS 'catalog_position', " +
            "Ser.position AS 'position', " +
            "Ser.reference AS 'reference', " +
            "Ser.label AS 'label', " +
            "MeasureUnit.identifier AS 'measureUnit_identifier', " +
            "MeasureUnit.denomination AS 'measureUnit_denomination', " +
            "MeasureUnit.acronym AS 'measureUnit_acronym', " +
            "MeasureUnit.'group' AS 'measureUnit_group', " +
            "Ser.price AS 'price' " +
            "FROM Service Ser " +
            "INNER JOIN Progeny Pgn ON Pgn.identifier = Ser.progeny " +
            "LEFT OUTER JOIN Catalog Catalog ON Catalog.identifier = Ser.catalog " +
            "LEFT OUTER JOIN MeasureUnit MeasureUnit ON MeasureUnit.identifier = Ser.measureUnit")
    LiveData<List<ServiceModel>> getServices();

}