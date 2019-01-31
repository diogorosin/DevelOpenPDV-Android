package br.com.developen.pdv.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import br.com.developen.pdv.room.SaleDAO;
import br.com.developen.pdv.room.SaleItemModel;
import br.com.developen.pdv.utils.DB;

public class SaleRepository extends AndroidViewModel {

    private LiveData<List<SaleItemModel>> items;

    private LiveData<Double> subtotal;

    private LiveData<Double> total;

    public SaleRepository(Application application) {

        super(application);

    }

    public LiveData<List<SaleItemModel>> getItems(Integer sale) {

        if (items ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            items = saleDAO.getItems(sale);

        }

        return items;

    }

    public LiveData<Double> getSubtotal(Integer sale) {

        if (subtotal ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            subtotal = saleDAO.getSubtotal(sale);

        }

        return subtotal;

    }

    public LiveData<Double> getTotal(Integer sale) {

        if (total ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            total = saleDAO.getTotal(sale);

        }

        return total;

    }

}