package br.com.developen.pdv.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import br.com.developen.pdv.room.SaleDAO;
import br.com.developen.pdv.utils.DB;

public class SaleRepository extends AndroidViewModel {

    private LiveData<Double> subtotalOfSale;

    private LiveData<Double> totalOfSale;

    private LiveData<Double> receivedOfSale;

    private LiveData<Double> toReceiveOfSale;

    private LiveData<Integer> saleCountOfToday;

    private LiveData<Double> saleBillingOfToday;

    private LiveData<Integer> ticketCountOfToday;

    private LiveData<List<SaleDAO.SalesByPeriodBean>> salesByPeriodOfToday;

    private LiveData<List<SaleDAO.SalesByProgenyBean>> salesByProgenyOfToday;

    private LiveData<List<SaleDAO.SalesByUserBean>> salesByUserOfToday;

    public SaleRepository(Application application) {

        super(application);

    }

    public LiveData<Double> getSubtotalOfSale(Integer sale) {

        if (subtotalOfSale ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            subtotalOfSale = saleDAO.getSubtotalOfSale(sale);

        }

        return subtotalOfSale;

    }

    public LiveData<Double> getTotalOfSale(Integer sale) {

        if (totalOfSale ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            totalOfSale = saleDAO.getTotalOfSale(sale);

        }

        return totalOfSale;

    }

    public LiveData<Double> getReceivedOfSale(Integer sale) {

        if (receivedOfSale ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            receivedOfSale = saleDAO.getReceivedOfSale(sale);

        }

        return receivedOfSale;

    }

    public LiveData<Double> getToReceiveOfSale(Integer sale) {

        if (toReceiveOfSale ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            toReceiveOfSale = saleDAO.getToReceiveOfSale(sale);

        }

        return toReceiveOfSale;

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