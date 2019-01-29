package br.com.developen.pdv.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MeasureUnitDAO {

    @Insert
    void create(MeasureUnitVO measureUnitVO);

    @Query("SELECT * FROM MeasureUnit WHERE identifier = :identifier")
    MeasureUnitVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 FROM MeasureUnit WHERE identifier = :identifier")
    Boolean exists(int identifier);

    @Update
    void update(MeasureUnitVO measureUnitVO);

    @Delete
    void delete(MeasureUnitVO measureUnitVO);

}