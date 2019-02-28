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


    private LiveData<Integer> saleCountOfWeek;

    private LiveData<Double> saleBillingOfWeek;

    private LiveData<Integer> ticketCountOfWeek;

    private LiveData<List<SaleDAO.SalesByPeriodBean>> salesByPeriodOfWeek;

    private LiveData<List<SaleDAO.SalesByProgenyBean>> salesByProgenyOfWeek;

    private LiveData<List<SaleDAO.SalesByUserBean>> salesByUserOfWeek;


    private LiveData<Integer> saleCountOfMonth;

    private LiveData<Double> saleBillingOfMonth;

    private LiveData<Integer> ticketCountOfMonth;

    private LiveData<List<SaleDAO.SalesByPeriodBean>> salesByPeriodOfMonth;

    private LiveData<List<SaleDAO.SalesByProgenyBean>> salesByProgenyOfMonth;

    private LiveData<List<SaleDAO.SalesByUserBean>> salesByUserOfMonth;


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


    /* TODAY */


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


    /* WEEK */


    public LiveData<Double> getSaleBillingOfWeek() {

        if (saleBillingOfWeek ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            saleBillingOfWeek = saleDAO.getSaleBillingOfWeek();

        }

        return saleBillingOfWeek;

    }


    public LiveData<Integer> getSaleCountOfWeek() {

        if (saleCountOfWeek ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            saleCountOfWeek = saleDAO.getSaleCountOfWeek();

        }

        return saleCountOfWeek;

    }


    public LiveData<Integer> getTicketCountOfWeek() {

        if (ticketCountOfWeek ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            ticketCountOfWeek = saleDAO.getTicketCountOfWeek();

        }

        return ticketCountOfWeek;

    }


    public LiveData<List<SaleDAO.SalesByPeriodBean>> getSalesByPeriodOfWeek() {

        if (salesByPeriodOfWeek ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            salesByPeriodOfWeek = saleDAO.getSalesByPeriodOfWeek();

        }

        return salesByPeriodOfWeek;

    }


    public LiveData<List<SaleDAO.SalesByProgenyBean>> getSalesByProgenyOfWeek() {

        if (salesByProgenyOfWeek ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            salesByProgenyOfWeek = saleDAO.getSalesByProgenyOfWeek();

        }

        return salesByProgenyOfWeek;

    }


    public LiveData<List<SaleDAO.SalesByUserBean>> getSalesByUserOfWeek() {

        if (salesByUserOfWeek ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            salesByUserOfWeek = saleDAO.getSalesByUserOfWeek();

        }

        return salesByUserOfWeek;

    }


    /* MONTH */


    public LiveData<Double> getSaleBillingOfMonth() {

        if (saleBillingOfMonth ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            saleBillingOfMonth = saleDAO.getSaleBillingOfMonth();

        }

        return saleBillingOfMonth;

    }


    public LiveData<Integer> getSaleCountOfMonth() {

        if (saleCountOfMonth ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            saleCountOfMonth = saleDAO.getSaleCountOfMonth();

        }

        return saleCountOfMonth;

    }


    public LiveData<Integer> getTicketCountOfMonth() {

        if (ticketCountOfMonth ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            ticketCountOfMonth = saleDAO.getTicketCountOfMonth();

        }

        return ticketCountOfMonth;

    }


    public LiveData<List<SaleDAO.SalesByPeriodBean>> getSalesByPeriodOfMonth() {

        if (salesByPeriodOfMonth ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            salesByPeriodOfMonth = saleDAO.getSalesByPeriodOfMonth();

        }

        return salesByPeriodOfMonth;

    }


    public LiveData<List<SaleDAO.SalesByProgenyBean>> getSalesByProgenyOfMonth() {

        if (salesByProgenyOfMonth ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            salesByProgenyOfMonth = saleDAO.getSalesByProgenyOfMonth();

        }

        return salesByProgenyOfMonth;

    }


    public LiveData<List<SaleDAO.SalesByUserBean>> getSalesByUserOfMonth() {

        if (salesByUserOfMonth ==null){

            SaleDAO saleDAO = DB.getInstance(
                    getApplication()).
                    saleDAO();

            salesByUserOfMonth = saleDAO.getSalesByUserOfMonth();

        }

        return salesByUserOfMonth;

    }


}