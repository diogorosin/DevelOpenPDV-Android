package br.com.developen.pdv.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import br.com.developen.pdv.room.SaleReceiptDAO;
import br.com.developen.pdv.room.SaleReceiptModel;
import br.com.developen.pdv.utils.DB;

public class SaleReceiptRepository extends AndroidViewModel {

    private LiveData<List<SaleReceiptModel>> receipts;

    public SaleReceiptRepository(Application application) {

        super(application);

    }

    public LiveData<List<SaleReceiptModel>> getReceipts(Integer sale) {

        if (receipts ==null){

            SaleReceiptDAO saleReceiptDAO = DB.getInstance(
                    getApplication()).
                    saleReceiptDAO();

            receipts = saleReceiptDAO.getReceipts(sale);

        }

        return receipts;

    }

}