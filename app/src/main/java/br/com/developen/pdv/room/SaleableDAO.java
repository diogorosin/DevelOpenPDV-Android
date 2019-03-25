package br.com.developen.pdv.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface SaleableDAO {

    @Query("SELECT Slb.* FROM Saleable Slb ORDER BY Slb.label")
    List<SaleableModel> getSaleables();

    @Query("SELECT Slb.* FROM Saleable Slb WHERE Slb.identifier = :identifier")
    SaleableModel getSaleable(Integer identifier);

}