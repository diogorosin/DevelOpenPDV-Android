package br.com.developen.pdv.repository;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.developen.pdv.room.CatalogDAO;
import br.com.developen.pdv.room.CatalogModel;
import br.com.developen.pdv.utils.DB;

public class CatalogRepository extends AndroidViewModel {

    private LiveData<List<CatalogModel>> catalogs;

    public CatalogRepository(Application application) {

        super(application);

    }

    public LiveData<List<CatalogModel>> getCatalogs() {

        if (catalogs ==null){

            CatalogDAO catalogDAO = DB.getInstance(
                    getApplication()).
                    catalogDAO();

            catalogs = catalogDAO.getCatalogs();

        }

        return catalogs;

    }

}