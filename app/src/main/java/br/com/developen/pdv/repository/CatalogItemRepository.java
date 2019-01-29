package br.com.developen.pdv.repository;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.room.CatalogItemModel;
import br.com.developen.pdv.room.SaleableModel;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.DB;

public class CatalogItemRepository extends Observable {


    private static CatalogItemRepository INSTANCE = null;

    private List<CatalogItemModel> catalogItems;


    public static CatalogItemRepository getInstance() {

        if(INSTANCE == null) {

            INSTANCE = new CatalogItemRepository();

        }

        return INSTANCE;

    }


    private CatalogItemRepository() {

        final Handler handler = new Handler();

        handler.post(new Runnable() {

            public void run() {

                try {

                    DB database = DB.getInstance(App.getContext());

                    if (database==null)

                        throw new CannotInitializeDatabaseException();

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

                    setCatalogItems(catalogItems);

                } catch(Exception e) {

                    e.printStackTrace();

                }

            }

        });

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


}