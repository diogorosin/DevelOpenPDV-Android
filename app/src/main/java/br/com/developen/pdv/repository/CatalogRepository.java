package br.com.developen.pdv.repository;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.room.CatalogItemModel;
import br.com.developen.pdv.room.CatalogModel;
import br.com.developen.pdv.room.SaleableModel;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.DB;

public class CatalogRepository extends Observable {


    private static CatalogRepository INSTANCE = null;

    private Integer sale;

    private List<CatalogModel> catalogs;

    private List<CatalogItemModel> catalogItems;


    public static CatalogRepository getInstance() {

        if(INSTANCE == null) {

            INSTANCE = new CatalogRepository();

        }

        return INSTANCE;

    }


    private CatalogRepository() {

        final Handler handler = new Handler();

        handler.post(new Runnable() {

            public void run() {

                try {

                    DB database = DB.getInstance(App.getContext());

                    if (database==null)

                        throw new CannotInitializeDatabaseException();

                    CatalogRepository.this.catalogs = database.catalogDAO().getCatalogsAsList();

                    List<CatalogItemModel> catalogItems = new ArrayList<>();

                    List<SaleableModel> saleables = database.saleableDAO().getSaleables();

                    if (saleables != null && !saleables.isEmpty()){

                        for (SaleableModel saleableModel : saleables) {

                            CatalogItemModel catalogItem = new CatalogItemModel();

                            catalogItem.setSaleable(saleableModel);

                            catalogItem.setQuantity(0.0);

                            catalogItem.setTotal(0.0);

                            catalogItems.add(catalogItem);

                        }

                    }

                    CatalogRepository.this.catalogItems = catalogItems;

                    setChanged();

                    notifyObservers();

                } catch(Exception e) {

                    e.printStackTrace();

                }

            }

        });

    }


    public List<CatalogModel> getCatalogs() {

        return catalogs;

    }


    public void setCatalogs(List<CatalogModel> catalogs) {

        this.catalogs = catalogs;

        setChanged();

        notifyObservers();

    }


    public List<CatalogItemModel> getCatalogItems() {

        return catalogItems;

    }


    public void setCatalogItems(List<CatalogItemModel> catalogItems) {

        this.catalogItems = catalogItems;

        setChanged();

        notifyObservers();

    }


    public void updateCatalogItem(CatalogItemModel catalogItem){

        if (catalogItems.contains(catalogItem)){

            int index = catalogItems.indexOf(catalogItem);

            catalogItems.remove(index);

            catalogItems.add(index, catalogItem);

            setChanged();

            notifyObservers();

        }

    }


    public void reset(){

        this.sale = null;

        for (CatalogItemModel catalogItemModel: getCatalogItems()) {

            catalogItemModel.setTotal(0.0);

            catalogItemModel.setQuantity(0.0);

        }

        setChanged();

        notifyObservers();

    }


    public Integer getSale() {

        return sale;

    }


    public void setSale(Integer sale) {

        this.sale = sale;

        setChanged();

        notifyObservers();

    }


}