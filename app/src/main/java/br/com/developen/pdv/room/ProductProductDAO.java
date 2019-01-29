package br.com.developen.pdv.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProductProductDAO {

    @Insert
    void create(ProductProductVO productProductVO);

    @Query("SELECT * FROM ProductProduct WHERE parent = :parent AND child = :child")
    ProductProductVO retrieve(int parent, int child);

    @Query("SELECT COUNT(*) > 0 FROM ProductProduct WHERE parent = :parent AND child = :child")
    Boolean exists(int parent, int child);

    @Update
    void update(ProductProductVO productProductVO);

    @Delete
    void delete(ProductProductVO productProductVO);

}