package br.com.developen.pdv.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CatalogDAO {

    String GET_CATALOGS =
            "SELECT " +
            "Cat.* " +
            "FROM " +
            "Catalog Cat " +
            "WHERE Cat.active = 1 " +
            "ORDER BY Cat.position";

    @Insert
    void create(CatalogVO catalogVO);

    @Query("SELECT C.* FROM Catalog C WHERE C.position = :identifier")
    CatalogVO retrieve(int identifier);

    @Query("SELECT COUNT(*) > 0 FROM Catalog C WHERE C.identifier = :identifier")
    Boolean exists(int identifier);

    @Query("SELECT COUNT(*) FROM Catalog C")
    Integer count();

    @Update
    void update(CatalogVO catalogVO);

    @Delete
    void delete(CatalogVO catalogVO);

    @Query(GET_CATALOGS)
    LiveData<List<CatalogModel>> getCatalogs();

    @Query(GET_CATALOGS)
    List<CatalogModel> getCatalogsAsList();

}