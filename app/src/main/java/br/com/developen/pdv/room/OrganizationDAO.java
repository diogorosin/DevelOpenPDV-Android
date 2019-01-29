package br.com.developen.pdv.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface OrganizationDAO {

    @Insert
    Long create(OrganizationVO organizationVO);

    @Query("SELECT O.* FROM Organization O WHERE O.subject = :subject")
    OrganizationVO retrieve(int subject);

    @Query("SELECT COUNT(*) > 0 FROM Organization O WHERE O.subject = :subject")
    Boolean exists(int subject);

    @Update
    void update(OrganizationVO organizationVO);

    @Delete
    void delete(OrganizationVO organizationVO);

}