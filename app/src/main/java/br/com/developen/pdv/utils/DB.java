package br.com.developen.pdv.utils;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import br.com.developen.pdv.room.CashDAO;
import br.com.developen.pdv.room.CashVO;
import br.com.developen.pdv.room.CatalogDAO;
import br.com.developen.pdv.room.CatalogVO;
import br.com.developen.pdv.room.IndividualDAO;
import br.com.developen.pdv.room.IndividualVO;
import br.com.developen.pdv.room.MeasureUnitDAO;
import br.com.developen.pdv.room.MeasureUnitMeasureUnitDAO;
import br.com.developen.pdv.room.MeasureUnitMeasureUnitVO;
import br.com.developen.pdv.room.MeasureUnitVO;
import br.com.developen.pdv.room.MerchandiseDAO;
import br.com.developen.pdv.room.MerchandiseVO;
import br.com.developen.pdv.room.OrganizationDAO;
import br.com.developen.pdv.room.OrganizationVO;
import br.com.developen.pdv.room.PaymentMethodDAO;
import br.com.developen.pdv.room.PaymentMethodVO;
import br.com.developen.pdv.room.ProductDAO;
import br.com.developen.pdv.room.ProductProductDAO;
import br.com.developen.pdv.room.ProductProductVO;
import br.com.developen.pdv.room.ProductVO;
import br.com.developen.pdv.room.ProgenyDAO;
import br.com.developen.pdv.room.ProgenyVO;
import br.com.developen.pdv.room.ReceiptMethodDAO;
import br.com.developen.pdv.room.ReceiptMethodVO;
import br.com.developen.pdv.room.SaleDAO;
import br.com.developen.pdv.room.SaleItemDAO;
import br.com.developen.pdv.room.SaleItemTicketDAO;
import br.com.developen.pdv.room.SaleItemTicketVO;
import br.com.developen.pdv.room.SaleItemVO;
import br.com.developen.pdv.room.SaleReceiptCashDAO;
import br.com.developen.pdv.room.SaleReceiptCashVO;
import br.com.developen.pdv.room.SaleReceiptDAO;
import br.com.developen.pdv.room.SaleReceiptVO;
import br.com.developen.pdv.room.SaleVO;
import br.com.developen.pdv.room.SaleableDAO;
import br.com.developen.pdv.room.SaleableModel;
import br.com.developen.pdv.room.ServiceDAO;
import br.com.developen.pdv.room.ServiceVO;
import br.com.developen.pdv.room.SubjectDAO;
import br.com.developen.pdv.room.SubjectVO;
import br.com.developen.pdv.room.UserDAO;
import br.com.developen.pdv.room.UserVO;


@Database(entities = {
        SubjectVO.class,
        IndividualVO.class,
        OrganizationVO.class,
        UserVO.class,
        CatalogVO.class,
        MeasureUnitVO.class,
        MeasureUnitMeasureUnitVO.class,
        ProgenyVO.class,
        ProductVO.class,
        ProductProductVO.class,
        ServiceVO.class,
        MerchandiseVO.class,
        PaymentMethodVO.class,
        ReceiptMethodVO.class,
        SaleReceiptVO.class,
        SaleReceiptCashVO.class,
        CashVO.class,
        SaleVO.class,
        SaleItemVO.class,
        SaleItemTicketVO.class}, views = {SaleableModel.class}, version = 002, exportSchema = false)
public abstract class DB extends RoomDatabase {



    static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        public void migrate(SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE 'SaleReceiptCash' ADD 'reversal' INTEGER");

        }

    };

    private static DB INSTANCE;

    public static DB getInstance(Context context) {

        if (INSTANCE == null) {

            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), DB.class, "developenpdv")
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries().
                            build();

        }

        return INSTANCE;

    }

    public abstract SubjectDAO subjectDAO();

    public abstract IndividualDAO individualDAO();

    public abstract OrganizationDAO organizationDAO();

    public abstract UserDAO userDAO();

    public abstract CatalogDAO catalogDAO();

    public abstract MeasureUnitDAO measureUnitDAO();

    public abstract MeasureUnitMeasureUnitDAO measureUnitMeasureUnitDAO();

    public abstract ProgenyDAO progenyDAO();

    public abstract ProductDAO productDAO();

    public abstract ProductProductDAO productProductDAO();

    public abstract ServiceDAO serviceDAO();

    public abstract MerchandiseDAO merchandiseDAO();

    public abstract ReceiptMethodDAO receiptMethodDAO();

    public abstract PaymentMethodDAO paymentMethodDAO();

    public abstract CashDAO cashDAO();

    public abstract SaleDAO saleDAO();

    public abstract SaleItemDAO saleItemDAO();

    public abstract SaleItemTicketDAO saleItemTicketDAO();

    public abstract SaleReceiptDAO saleReceiptDAO();

    public abstract SaleReceiptCashDAO saleReceiptCashDAO();

    public abstract SaleableDAO saleableDAO();

}