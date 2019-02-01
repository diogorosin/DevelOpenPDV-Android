package br.com.developen.pdv.repository;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import br.com.developen.pdv.room.SaleDAO;
import br.com.developen.pdv.utils.DB;

public class SaleRepository extends AndroidViewModel {

    private LiveData<Double> subtotal;

    private LiveData<Double> total;

    private LiveData<Double> received;

    private LiveData<Double> toReceive;

    public SaleRepository(Application application) {

        super(application);

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

    public LiveData<Double> getReceived(Integer sale) {

        if (received ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            received = saleDAO.getReceived(sale);

        }

        return received;

    }

    public LiveData<Double> getToReceive(Integer sale) {

        if (toReceive ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            toReceive = saleDAO.getToReceive(sale);

        }

        return toReceive;

    }

}