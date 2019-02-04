package br.com.developen.pdv.task;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.exception.InternalException;
import br.com.developen.pdv.repository.CatalogRepository;
import br.com.developen.pdv.room.CatalogItemModel;
import br.com.developen.pdv.room.SaleItemVO;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.Messaging;

public final class UpdateSaleFromCatalogAsyncTask<L extends UpdateSaleFromCatalogAsyncTask.Listener>
        extends AsyncTask<Void, Void, Object> {


    private WeakReference<L> listener;

    private SharedPreferences sharedPreferences;


    public UpdateSaleFromCatalogAsyncTask(L listener) {

        this.listener = new WeakReference<>(listener);

        this.sharedPreferences = App.getContext().getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME, 0);

    }


    protected Object doInBackground(Void... parameters) {

        DB database = DB.getInstance(App.getContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        try {

            database.beginTransaction();

            database.saleItemDAO().delete(CatalogRepository.
                    getInstance().getSale());

            int item = 1;

            for (CatalogItemModel catalogItemModel: CatalogRepository.
                    getInstance().getCatalogItems()) {

                if (catalogItemModel.getQuantity() > 0) {

                    SaleItemVO saleItemVO = new SaleItemVO();

                    saleItemVO.setSale(CatalogRepository.
                            getInstance().getSale());

                    saleItemVO.setItem(item);

                    saleItemVO.setProgeny(catalogItemModel.
                            getSaleable().
                            getIdentifier());

                    saleItemVO.setMeasureUnit(catalogItemModel.
                            getSaleable().
                            getMeasureUnit().
                            getIdentifier());

                    saleItemVO.setPrice(catalogItemModel.
                            getSaleable().
                            getPrice());

                    saleItemVO.setQuantity(catalogItemModel.
                            getQuantity());

                    saleItemVO.setTotal(catalogItemModel.
                            getTotal());

                    database.saleItemDAO().create(saleItemVO);

                    item++;

                }

            }

            database.setTransactionSuccessful();

            return Boolean.TRUE;

        } catch(Exception e) {

            return new InternalException();

        } finally {

            if (database.inTransaction())

                database.endTransaction();

        }

    }


    protected void onPostExecute(Object callResult) {

        L listener = this.listener.get();

        if (listener != null) {

            if (callResult instanceof Boolean) {

                listener.onUpdateSaleSuccess();

            } else {

                if (callResult instanceof Messaging) {

                    listener.onUpdateSaleFailure((Messaging) callResult);

                }

            }

        }

    }


    public interface Listener {

        void onUpdateSaleSuccess();

        void onUpdateSaleFailure(Messaging messaging);

    }


}
