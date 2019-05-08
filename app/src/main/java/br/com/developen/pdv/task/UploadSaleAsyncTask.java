package br.com.developen.pdv.task;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Process;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.developen.pdv.exception.CannotInitializeDatabaseException;
import br.com.developen.pdv.exception.HttpRequestException;
import br.com.developen.pdv.jersey.CompanyDeviceDatasetBean;
import br.com.developen.pdv.jersey.DeviceBean;
import br.com.developen.pdv.jersey.ExceptionBean;
import br.com.developen.pdv.jersey.SaleBean;
import br.com.developen.pdv.jersey.SaleItemBean;
import br.com.developen.pdv.room.SaleItemModel;
import br.com.developen.pdv.room.SaleVO;
import br.com.developen.pdv.utils.App;
import br.com.developen.pdv.utils.Constants;
import br.com.developen.pdv.utils.DB;
import br.com.developen.pdv.utils.Messaging;
import br.com.developen.pdv.utils.RequestBuilder;

public final class UploadSaleAsyncTask<
        A extends UploadSaleAsyncTask.Listener,
        B extends Integer,
        C extends Integer,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;

    private SharedPreferences preferences;


    public UploadSaleAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

        this.preferences = App.getInstance().
                getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, 0);

    }


    protected void onPreExecute(){

        A listener = this.activity.get();

        if (listener != null)

            listener.onUploadSalesPreExecute();

    }


    protected Object doInBackground(Integer... sales) {

        //MENOR PRIORIDADE
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        DB database = DB.getInstance(App.getContext());

        if (database==null)

            return new CannotInitializeDatabaseException();

        //DATASET
        CompanyDeviceDatasetBean companyDeviceDatasetBean = new CompanyDeviceDatasetBean();

        //DEVICE
        DeviceBean deviceBean = new DeviceBean();

        deviceBean.setIdentifier(preferences.getInt(Constants.DEVICE_IDENTIFIER_PROPERTY, 0));

        companyDeviceDatasetBean.setDevice(deviceBean);

        //SALES
        for (Integer sale: sales) {

            SaleVO saleVO = database.saleDAO().retrieve(sale);

            SaleBean saleBean = new SaleBean();

            saleBean.setIdentifier(saleVO.getIdentifier());

            saleBean.setNumber(saleVO.getNumber());

            saleBean.setDateTime(saleVO.getDateTime());

            saleBean.setStatus(saleVO.getStatus());

            saleBean.setUser(saleVO.getUser());

            saleBean.setNote(saleVO.getNote());

            for (SaleItemModel saleItemModel: database.saleItemDAO().getItemsAsList(saleVO.getIdentifier())) {

                SaleItemBean saleItemBean = new SaleItemBean();

                saleItemBean.setProgeny(saleItemModel.getSaleable().getIdentifier());

                saleItemBean.setMeasureUnit(saleItemModel.getMeasureUnit().getIdentifier());

                saleItemBean.setQuantity(saleItemModel.getQuantity());

                saleItemBean.setPrice(saleItemModel.getPrice());

                saleItemBean.setTotal(saleItemModel.getTotal());

                saleBean.getItems().put(saleItemModel.getItem(), saleItemBean);

            }

            companyDeviceDatasetBean.getSales().add(saleBean);

        }

        try {

            Response response = RequestBuilder.
                    build("device", "sale").
                    request(MediaType.APPLICATION_JSON).
                    header(HttpHeaders.AUTHORIZATION,
                            Constants.TOKEN_PREFIX +
                                    preferences.getString(Constants.TOKEN_PROPERTY, "")).
                    post(Entity.entity(companyDeviceDatasetBean, MediaType.APPLICATION_JSON));

            switch (response.getStatus()) {

                case HttpURLConnection.HTTP_OK:

                    return response.readEntity(new GenericType<Map<Integer, Boolean>>(){});

                default:

                    return response.readEntity(ExceptionBean.class);

            }

        } catch (Exception e){

            return new HttpRequestException();

        }

    }


    protected void onPostExecute(Object callResult) {

        A listener = this.activity.get();

        if (listener != null) {

            if (callResult != null) {

                if (callResult instanceof Map){

                    List<Integer> salesToUpdate = new ArrayList<>();

                    Map<Integer, Boolean> sales = (Map<Integer, Boolean>) callResult;

                    for(Map.Entry<Integer, Boolean> entry : sales.entrySet()) {

                        if (entry.getValue())

                            salesToUpdate.add(entry.getKey());

                    }

                    DB database = DB.getInstance(App.getContext());

                    database.saleDAO().
                            setUploaded(salesToUpdate.toArray(new Integer[salesToUpdate.size()]));

                    listener.onUploadSalesSuccess();

                } else {

                    if (callResult instanceof Messaging){

                        listener.onUploadSalesFailure((Messaging) callResult);

                    }

                }

            }

        }

    }


    public interface Listener {

        void onUploadSalesPreExecute();

        void onUploadSalesSuccess();

        void onUploadSalesFailure(Messaging messaging);

    }


}