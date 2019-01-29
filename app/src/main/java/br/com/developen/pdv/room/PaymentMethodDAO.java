package br.com.developen.pdv.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PaymentMethodDAO {

    @Insert
    void create(PaymentMethodVO paymentMethod);

    @Query("SELECT PM.* FROM PaymentMethod PM WHERE PM.identifier = :identifier")
    PaymentMethodVO retrieve(String identifier);

    @Query("SELECT COUNT(*) > 0 FROM PaymentMethod PM WHERE PM.identifier = :identifier")
    Boolean exists(String identifier);

    @Update
    void update(PaymentMethodVO paymentMethod);

    @Delete
    void delete(PaymentMethodVO paymentMethod);

    @Query("SELECT COUNT(*) FROM PaymentMethod PM")
    Integer count();

    @Query("SELECT PM.* " +
            "FROM PaymentMethod PM " +
            "ORDER BY PM.identifier")
    LiveData<List<PaymentMethodModel>> getPaymentMethods();

}