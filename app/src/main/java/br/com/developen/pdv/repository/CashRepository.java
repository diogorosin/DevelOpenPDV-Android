package br.com.developen.pdv.repository;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import br.com.developen.pdv.room.CashDAO;
import br.com.developen.pdv.room.CashModel;
import br.com.developen.pdv.utils.DB;

public class CashRepository extends AndroidViewModel {

    private LiveData<Boolean> isOpen;

    private LiveData<Double> value;

    private LiveData<List<CashModel>> cashSummary;

    private LiveData<List<CashModel>> cashEntry;

    public CashRepository(@NonNull Application application) {

        super(application);

    }

    public LiveData<Boolean> isOpen() {

        if (isOpen==null) {

            CashDAO cashDAO = DB.getInstance(
                    getApplication()).
                    cashDAO();

            isOpen = cashDAO.isOpen();

        }

        return isOpen;

    }

    public LiveData<Double> value() {

        if (value ==null) {

            CashDAO cashDAO = DB.getInstance(
                    getApplication()).
                    cashDAO();

            value = cashDAO.value();

        }

        return value;

    }

    public LiveData<List<CashModel>> cashSummary() {

        if (cashSummary ==null) {

            CashDAO cashDAO = DB.getInstance(
                    getApplication()).
                    cashDAO();

            cashSummary = cashDAO.cashSummary();

        }

        return cashSummary;

    }

    public LiveData<List<CashModel>> cashEntry() {

        if (cashEntry ==null) {

            CashDAO cashDAO = DB.getInstance(
                    getApplication()).
                    cashDAO();

            cashEntry = cashDAO.cashEntry();

        }

        return cashEntry;

    }

}