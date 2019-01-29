package br.com.developen.pdv.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MeasureUnitMeasureUnitDAO {

    @Insert
    void create(MeasureUnitMeasureUnitVO measureUnitMeasureUnitVO);

    @Query("SELECT * FROM MeasureUnitMeasureUnit WHERE fromIdentifier = :from AND toIdentifier = :to")
    MeasureUnitMeasureUnitVO retrieve(int from, int to);

    @Query("SELECT COUNT(*) > 0 FROM MeasureUnitMeasureUnit WHERE fromIdentifier = :from AND toIdentifier = :to")
    Boolean exists(int from, int to);

    @Update
    void update(MeasureUnitMeasureUnitVO measureUnitMeasureUnitVO);

    @Delete
    void delete(MeasureUnitMeasureUnitVO measureUnitMeasureUnitVO);

}