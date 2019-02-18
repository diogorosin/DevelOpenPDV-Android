package br.com.developen.pdv.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import br.com.developen.pdv.room.SaleDAO;
import br.com.developen.pdv.utils.DB;

public class SaleRepository extends AndroidViewModel {

    private LiveData<Double> subtotal;

    private LiveData<Double> total;

    private LiveData<Double> received;

    private LiveData<Double> toReceive;

    private LiveData<Integer> saleCountOfToday;

    private LiveData<Double> saleBillingOfToday;

    private LiveData<Integer> ticketCountOfToday;

    private LiveData<List<SaleDAO.SalesByPeriodBean>> salesByPeriodOfToday;

    private LiveData<List<SaleDAO.SalesByProgenyBean>> salesByProgenyOfToday;

    private LiveData<List<SaleDAO.SalesByUserBean>> salesByUserOfToday;

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


    public LiveData<Double> getSaleBillingOfToday() {

        if (saleBillingOfToday ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            saleBillingOfToday = saleDAO.getSaleBillingOfToday();

        }

        return saleBillingOfToday;

    }

    public LiveData<Integer> getSaleCountOfToday() {

        if (saleCountOfToday ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            saleCountOfToday = saleDAO.getSaleCountOfToday();

        }

        return saleCountOfToday;

    }

    public LiveData<Integer> getTicketCountOfToday() {

        if (ticketCountOfToday ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            ticketCountOfToday = saleDAO.getTicketCountOfToday();

        }

        return ticketCountOfToday;

    }

    public LiveData<List<SaleDAO.SalesByPeriodBean>> getSalesByPeriodOfToday() {

        if (salesByPeriodOfToday ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            salesByPeriodOfToday = saleDAO.getSalesByPeriodOfToday();

        }

        return salesByPeriodOfToday;

    }

    public LiveData<List<SaleDAO.SalesByProgenyBean>> getSalesByProgenyOfToday() {

        if (salesByProgenyOfToday ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            salesByProgenyOfToday = saleDAO.getSalesByProgenyOfToday();

        }

        return salesByProgenyOfToday;

    }

    public LiveData<List<SaleDAO.SalesByUserBean>> getSalesByUserOfToday() {

        if (salesByUserOfToday ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            salesByUserOfToday = saleDAO.getSalesByUserOfToday();

        }

        return salesByUserOfToday;

    }

}