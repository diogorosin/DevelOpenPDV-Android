package br.com.developen.pdv.repository;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import br.com.developen.pdv.room.SaleItemDAO;
import br.com.developen.pdv.room.SaleItemModel;
import br.com.developen.pdv.utils.DB;

public class SaleItemRepository extends AndroidViewModel {

    private LiveData<List<SaleItemModel>> items;

    public SaleItemRepository(Application application) {

        super(application);

    }

    public LiveData<List<SaleItemModel>> getItems(Integer sale) {

        if (items ==null){

            SaleItemDAO saleItemDAO = DB.getInstance(
                    getApplication()).
                    saleItemDAO();

            items = saleItemDAO.getItems(sale);

        }

        return items;

    }

}