package br.com.developen.pdv.repository;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.room.SaleItemModel;
import br.com.developen.pdv.room.SaleModel;
import br.com.developen.pdv.room.SaleableModel;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.DB;

public class SaleItemRepository extends Observable {


    private static SaleItemRepository INSTANCE = null;

    private List<SaleItemModel> saleItems;


    public static SaleItemRepository getInstance() {

        if(INSTANCE == null) {

            INSTANCE = new SaleItemRepository();

        }

        return INSTANCE;

    }


    private SaleItemRepository() {

        final Handler handler = new Handler();

        handler.post(new Runnable() {

            public void run() {

                try {

                    DB database = DB.getInstance(App.getContext());

                    if (database==null)

                        throw new CannotInitializeDatabaseException();

                    List<SaleItemModel> saleItems = new ArrayList<>();

                    List<SaleableModel> saleables = database.saleableDAO().getSaleables();

                    if (saleables != null && !saleables.isEmpty()){

                        int item = 1;

                        SaleModel saleModel = new SaleModel();

                        saleModel.setIdentifier(0);

                        saleModel.setDateTime(new Date());

                        for (SaleableModel saleableModel : saleables) {

                            SaleItemModel saleItemModel = new SaleItemModel();

                            saleItemModel.setSale(saleModel);

                            saleItemModel.setItem(item);

                            saleItemModel.setSaleable(saleableModel);

                            saleItemModel.setMeasureUnit(saleableModel.getMeasureUnit());

                            saleItemModel.setPrice(saleableModel.getPrice());

                            saleItemModel.setQuantity(0.0);

                            saleItemModel.setTotal(0.0);

                            saleItems.add(saleItemModel);

                            item++;

                        }

                    }

                    setSaleItems(saleItems);

                } catch(Exception e) {

                    e.printStackTrace();

                }

            }

        });

    }


    public List<SaleItemModel> getSaleItems() {

        return saleItems;

    }


    public void setSaleItems(List<SaleItemModel> saleItems) {

        this.saleItems = saleItems;

        setChanged();

        notifyObservers();

    }


/*    public void incrementSaleItemQuantity(SaleItemModel saleItem){

        if (saleItems.contains(saleItem)){

            SaleItemModel newSaleItem =  saleItems.get( saleItems.indexOf(saleItem) );

            newSaleItem.setQuantity( newSaleItem.getQuantity() + 1 );

            setChanged();

            notifyObservers();

        }

    } */


    public void updateSaleItem(SaleItemModel saleItem){

        if (saleItems.contains(saleItem)){

            int index = saleItems.indexOf(saleItem);

            saleItems.remove( index );

            saleItems.add( index, saleItem );

            setChanged();

            notifyObservers();

        }

    }


}