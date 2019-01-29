package br.com.developen.pdv.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProgenyDAO {

    @Insert
    void create(ProgenyVO progenyVO);

    @Query("SELECT * FROM Progeny WHERE identifier = :identifier")
    ProgenyVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 " +
            "FROM Progeny " +
            "WHERE identifier = :identifier")
    Boolean exists(int identifier);

    @Update
    void update(ProgenyVO progenyVO);

    @Delete
    void delete(ProgenyVO progenyVO);

}