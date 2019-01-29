package br.com.developen.pdv.repository;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import br.com.developen.pdv.room.ReceiptMethodDAO;
import br.com.developen.pdv.room.ReceiptMethodModel;
import br.com.developen.pdv.utils.DB;

public class ReceiptMethodRepository extends AndroidViewModel {

    private LiveData<List<ReceiptMethodModel>> receiptMethods;

    public ReceiptMethodRepository(@NonNull Application application) {

        super(application);

    }

    public LiveData<List<ReceiptMethodModel>> getReceiptMethods() {

        if (receiptMethods ==null) {

            ReceiptMethodDAO receiptMethodDAO = DB.getInstance(
                    getApplication()).
                    receiptMethodDAO();

            receiptMethods = receiptMethodDAO.getReceitpMethods();

        }

        return receiptMethods;

    }

}