package br.com.developen.pdv.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import br.com.developen.pdv.room.CatalogReceiptModel;
import br.com.developen.pdv.room.ReceiptMethodModel;

public class CatalogReceiptRepository extends Observable {


    private Integer sequencial = 1;

    private static CatalogReceiptRepository INSTANCE = null;

    private List<CatalogReceiptModel> catalogReceipts;


    public static CatalogReceiptRepository getInstance() {

        if(INSTANCE == null) {

            INSTANCE = new CatalogReceiptRepository();

        }

        return INSTANCE;

    }


    private CatalogReceiptRepository() {

        catalogReceipts = new ArrayList<>();

    }


    public List<CatalogReceiptModel> getCatalogReceipts() {

        return catalogReceipts;

    }


    public void setCatalogReceipts(List<CatalogReceiptModel> catalogReceipts) {

        this.catalogReceipts = catalogReceipts;

        setChanged();

        notifyObservers();

    }

    public void updateMoneyReceiptValue(Double newValue){

        CatalogReceiptModel oldCatalogReceipt = null;

        for (CatalogReceiptModel catalogReceipt: catalogReceipts)

            if (catalogReceipt.getMethod().getIdentifier().equals("DIN"))

                oldCatalogReceipt = catalogReceipt;

        int index = -1;

        if (oldCatalogReceipt != null){

            index = catalogReceipts.indexOf(oldCatalogReceipt);

            catalogReceipts.remove(index);

        }

        ReceiptMethodModel moneyReceiptMethod = new ReceiptMethodModel();

        moneyReceiptMethod.setIdentifier("DIN");

        moneyReceiptMethod.setDenomination("Dinheiro");

        CatalogReceiptModel catalogReceipt = new CatalogReceiptModel();

        catalogReceipt.setIdentifier(sequencial);

        catalogReceipt.setMethod(moneyReceiptMethod);

        catalogReceipt.setValue(newValue);

        if (index != -1)

            catalogReceipts.add(index, catalogReceipt);

        else

            catalogReceipts.add(catalogReceipt);

        sequencial += 1;

        setChanged();

        notifyObservers();

    }

}