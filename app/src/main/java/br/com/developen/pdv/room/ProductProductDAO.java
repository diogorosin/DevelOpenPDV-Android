package br.com.developen.pdv.room;

import java.util.List;

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

    @Query("SELECT " +
            "PgnPar.identifier AS 'parent_identifier'," +
            "PgnPar.denomination AS 'parent_denomination'," +
            "PgnChi.identifier AS 'child_identifier'," +
            "PgnChi.denomination AS 'child_identifier'," +
            "PdtPdt.quantity " +
            "FROM ProductProduct PdtPdt " +
            "INNER JOIN Product PdtPar ON PdtPar.progeny = PdtPdt.parent " +
            "INNER JOIN Progeny PgnPar ON PgnPar.identifier = PdtPar.progeny " +
            "INNER JOIN Product PdtChi ON PdtChi.progeny = PdtPdt.child " +
            "INNER JOIN Progeny PgnChi ON PgnChi.identifier = PdtChi.progeny " +
            "WHERE PdtPar.progeny = :product AND PdtPdt.active = 1")
    List<ProductProductModel> getCompositionOfProduct(int product);

}