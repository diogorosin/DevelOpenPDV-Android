package br.com.developen.pdv.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface IndividualDAO {

    @Insert
    Long create(IndividualVO individualVO);

    @Query("SELECT I.* FROM Individual I WHERE I.subject = :subject")
    IndividualVO retrieve(int subject);

    @Query("SELECT COUNT(*) > 0 FROM Individual I WHERE I.subject = :subject")
    Boolean exists(int subject);

    @Update
    void update(IndividualVO individualVO);

    @Delete
    void delete(IndividualVO individualVO);

}