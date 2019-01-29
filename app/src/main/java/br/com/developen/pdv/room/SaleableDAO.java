package br.com.developen.pdv.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface SaleableDAO {

    @Query("SELECT * FROM Saleable")
    List<SaleableModel> getSaleables();

}