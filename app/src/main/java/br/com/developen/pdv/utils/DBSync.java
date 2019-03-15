package br.com.developen.pdv.utils;

import br.com.developen.pdv.jersey.CatalogBean;
import br.com.developen.pdv.jersey.DatasetBean;
import br.com.developen.pdv.jersey.MeasureUnitBean;
import br.com.developen.pdv.jersey.MeasureUnitMeasureUnitBean;
import br.com.developen.pdv.jersey.MerchandiseBean;
import br.com.developen.pdv.jersey.PaymentMethodBean;
import br.com.developen.pdv.jersey.ProductBean;
import br.com.developen.pdv.jersey.ProductProductBean;
import br.com.developen.pdv.jersey.ReceiptMethodBean;
import br.com.developen.pdv.jersey.ServiceBean;
import br.com.developen.pdv.jersey.UserBean;
import br.com.developen.pdv.room.CatalogVO;
import br.com.developen.pdv.room.IndividualVO;
import br.com.developen.pdv.room.MeasureUnitMeasureUnitVO;
import br.com.developen.pdv.room.MeasureUnitVO;
import br.com.developen.pdv.room.MerchandiseVO;
import br.com.developen.pdv.room.PaymentMethodVO;
import br.com.developen.pdv.room.ProductProductVO;
import br.com.developen.pdv.room.ProductVO;
import br.com.developen.pdv.room.ProgenyVO;
import br.com.developen.pdv.room.ReceiptMethodVO;
import br.com.developen.pdv.room.ServiceVO;
import br.com.developen.pdv.room.SubjectVO;
import br.com.developen.pdv.room.UserVO;

public class DBSync {

    private DB database;

    public DBSync(DB database){

        this.database = database;

    }

    public void syncDataset(DatasetBean datasetBean) {

        try {

            database.beginTransaction();

            if (datasetBean.getUsers() != null &&
                    !datasetBean.getUsers().isEmpty()){

                for (UserBean userBean : datasetBean.getUsers()) {

                    SubjectVO subjectVO = new SubjectVO();

                    subjectVO.setIdentifier(userBean.getIdentifier());

                    subjectVO.setActive(userBean.getActive());

                    subjectVO.setLevel(userBean.getLevel());

                    if (database.subjectDAO().exists(subjectVO.getIdentifier()))

                        database.subjectDAO().update(subjectVO);

                    else

                        database.subjectDAO().create(subjectVO);

                    IndividualVO individualVO = new IndividualVO();

                    individualVO.setSubject(subjectVO.getIdentifier());

                    individualVO.setName(userBean.getName());

                    if (database.individualDAO().exists(individualVO.getSubject()))

                        database.individualDAO().update(individualVO);

                    else

                        database.individualDAO().create(individualVO);

                    UserVO userVO = new UserVO();

                    userVO.setIndividual(individualVO.getSubject());

                    userVO.setLogin(userBean.getLogin());

                    userVO.setPassword(userBean.getPassword());

                    userVO.setNumericPassword(userBean.getNumericPassword());

                    if (database.userDAO().exists(userVO.getIndividual()))

                        database.userDAO().update(userVO);

                    else

                        database.userDAO().create(userVO);

                }

            }

            if (datasetBean.getMeasureUnits() != null &&
                    !datasetBean.getMeasureUnits().isEmpty()) {

                for (MeasureUnitBean measureUnitBean : datasetBean.getMeasureUnits()) {

                    MeasureUnitVO measureUnitVO = new MeasureUnitVO();

                    measureUnitVO.setIdentifier(measureUnitBean.getIdentifier());

                    measureUnitVO.setDenomination(measureUnitBean.getDenomination());

                    measureUnitVO.setAcronym(measureUnitBean.getAcronym());

                    measureUnitVO.setGroup(measureUnitBean.getGroup());

                    if (database.measureUnitDAO().exists(measureUnitVO.getIdentifier()))

                        database.measureUnitDAO().update(measureUnitVO);

                    else

                        database.measureUnitDAO().create(measureUnitVO);

                }

            }

            if (datasetBean.getCatalogs() != null &&
                    !datasetBean.getCatalogs().isEmpty()) {

                for (CatalogBean catalogBean : datasetBean.getCatalogs()) {

                    CatalogVO catalogVO = new CatalogVO();

                    catalogVO.setIdentifier(catalogBean.getIdentifier());

                    catalogVO.setActive(catalogBean.getActive());

                    catalogVO.setPosition(catalogBean.getPosition());

                    catalogVO.setDenomination(catalogBean.getDenomination());

                    if (database.catalogDAO().exists(catalogVO.getIdentifier()))

                        database.catalogDAO().update(catalogVO);

                    else

                        database.catalogDAO().create(catalogVO);

                }

            }

            if (datasetBean.getMeasureUnitMeasureUnits() != null &&
                    !datasetBean.getMeasureUnitMeasureUnits().isEmpty()) {

                for (MeasureUnitMeasureUnitBean measureUnitMeasureUnitBean : datasetBean.getMeasureUnitMeasureUnits()) {

                    MeasureUnitMeasureUnitVO measureUnitMeasureUnitVO = new MeasureUnitMeasureUnitVO();

                    measureUnitMeasureUnitVO.setFromIdentifier(measureUnitMeasureUnitBean.getFrom());

                    measureUnitMeasureUnitVO.setToIdentifier(measureUnitMeasureUnitBean.getTo());

                    measureUnitMeasureUnitVO.setFactor(measureUnitMeasureUnitBean.getFactor());

                    if (database.measureUnitMeasureUnitDAO().exists(measureUnitMeasureUnitVO.getFromIdentifier(), measureUnitMeasureUnitVO.getToIdentifier()))

                        database.measureUnitMeasureUnitDAO().update(measureUnitMeasureUnitVO);

                    else

                        database.measureUnitMeasureUnitDAO().create(measureUnitMeasureUnitVO);

                }

            }

            if (datasetBean.getProducts() != null &&
                    !datasetBean.getProducts().isEmpty()) {

                for (ProductBean productBean : datasetBean.getProducts()) {

                    ProgenyVO progenyVO = new ProgenyVO();

                    progenyVO.setIdentifier(productBean.getIdentifier());

                    progenyVO.setDenomination(productBean.getDenomination());

                    progenyVO.setActive(productBean.getActive());

                    if (database.progenyDAO().exists(progenyVO.getIdentifier()))

                        database.progenyDAO().update(progenyVO);

                    else

                        database.progenyDAO().create(progenyVO);

                    ProductVO productVO = new ProductVO();

                    productVO.setProgeny(progenyVO.getIdentifier());

                    productVO.setStockUnit(productBean.getStockUnit());

                    productVO.setWidthValue(productBean.getWidthValue());

                    productVO.setWidthUnit(productBean.getWidthUnit());

                    productVO.setHeightValue(productBean.getHeightValue());

                    productVO.setHeightUnit(productBean.getHeightUnit());

                    productVO.setLengthValue(productBean.getLengthValue());

                    productVO.setLengthUnit(productBean.getLengthUnit());

                    productVO.setContentValue(productBean.getContentValue());

                    productVO.setContentUnit(productBean.getContentUnit());

                    productVO.setGrossWeightValue(productBean.getGrossWeightValue());

                    productVO.setGrossWeightUnit(productBean.getGrossWeightUnit());

                    productVO.setNetWeightValue(productBean.getNetWeightValue());

                    productVO.setNetWeightUnit(productBean.getNetWeightUnit());

                    if (database.productDAO().exists(productVO.getProgeny()))

                        database.productDAO().update(productVO);

                    else

                        database.productDAO().create(productVO);

                }

            }

            if (datasetBean.getServices() != null &&
                    !datasetBean.getServices().isEmpty()) {

                for (ServiceBean serviceBean : datasetBean.getServices()) {

                    ProgenyVO progenyVO = new ProgenyVO();

                    progenyVO.setIdentifier(serviceBean.getIdentifier());

                    progenyVO.setDenomination(serviceBean.getDenomination());

                    progenyVO.setActive(serviceBean.getActive());

                    if (database.progenyDAO().exists(progenyVO.getIdentifier()))

                        database.progenyDAO().update(progenyVO);

                    else

                        database.progenyDAO().create(progenyVO);

                    ServiceVO serviceVO = new ServiceVO();

                    serviceVO.setProgeny(progenyVO.getIdentifier());

                    serviceVO.setCatalog(serviceBean.getCatalog());

                    serviceVO.setPosition(serviceBean.getPosition());

                    serviceVO.setReference(serviceBean.getReference());

                    serviceVO.setLabel(serviceBean.getLabel());

                    serviceVO.setMeasureUnit(serviceBean.getMeasureUnit());

                    serviceVO.setPrice(serviceBean.getPrice());

                    if (database.serviceDAO().exists(serviceVO.getProgeny()))

                        database.serviceDAO().update(serviceVO);

                    else

                        database.serviceDAO().create(serviceVO);

                }

            }

            if (datasetBean.getMerchandises() != null &&
                    !datasetBean.getMerchandises().isEmpty()) {

                for (MerchandiseBean merchandiseBean : datasetBean.getMerchandises()) {

                    ProgenyVO progenyVO = new ProgenyVO();

                    progenyVO.setIdentifier(merchandiseBean.getIdentifier());

                    progenyVO.setDenomination(merchandiseBean.getDenomination());

                    progenyVO.setActive(merchandiseBean.getActive());

                    if (database.progenyDAO().exists(progenyVO.getIdentifier()))

                        database.progenyDAO().update(progenyVO);

                    else

                        database.progenyDAO().create(progenyVO);

                    ProductVO productVO = new ProductVO();

                    productVO.setProgeny(progenyVO.getIdentifier());

                    productVO.setStockUnit(merchandiseBean.getStockUnit());

                    productVO.setWidthValue(merchandiseBean.getWidthValue());

                    productVO.setWidthUnit(merchandiseBean.getWidthUnit());

                    productVO.setHeightValue(merchandiseBean.getHeightValue());

                    productVO.setHeightUnit(merchandiseBean.getHeightUnit());

                    productVO.setLengthValue(merchandiseBean.getLengthValue());

                    productVO.setLengthUnit(merchandiseBean.getLengthUnit());

                    productVO.setContentValue(merchandiseBean.getContentValue());

                    productVO.setContentUnit(merchandiseBean.getContentUnit());

                    productVO.setGrossWeightValue(merchandiseBean.getGrossWeightValue());

                    productVO.setGrossWeightUnit(merchandiseBean.getGrossWeightUnit());

                    productVO.setNetWeightValue(merchandiseBean.getNetWeightValue());

                    productVO.setNetWeightUnit(merchandiseBean.getNetWeightUnit());

                    if (database.productDAO().exists(productVO.getProgeny()))

                        database.productDAO().update(productVO);

                    else

                        database.productDAO().create(productVO);

                    MerchandiseVO merchandiseVO = new MerchandiseVO();

                    merchandiseVO.setProduct(progenyVO.getIdentifier());

                    merchandiseVO.setCatalog(merchandiseBean.getCatalog());

                    merchandiseVO.setPosition(merchandiseBean.getPosition());

                    merchandiseVO.setReference(merchandiseBean.getReference());

                    merchandiseVO.setLabel(merchandiseBean.getLabel());

                    merchandiseVO.setPrice(merchandiseBean.getPrice());

                    if (database.merchandiseDAO().exists(merchandiseVO.getProduct()))

                        database.merchandiseDAO().update(merchandiseVO);

                    else

                        database.merchandiseDAO().create(merchandiseVO);

                }

            }

            if (datasetBean.getProductProducts() != null &&
                    !datasetBean.getProductProducts().isEmpty()) {

                for (ProductProductBean productProductBean : datasetBean.getProductProducts()) {

                    ProductProductVO productProductVO = new ProductProductVO();

                    productProductVO.setParent(productProductBean.getParent());

                    productProductVO.setChild(productProductBean.getChild());

                    productProductVO.setActive(productProductBean.getActive());

                    productProductVO.setQuantity(productProductBean.getQuantity());

                    if (database.productProductDAO().exists(productProductVO.getParent(), productProductVO.getChild()))

                        database.productProductDAO().update(productProductVO);

                    else

                        database.productProductDAO().create(productProductVO);

                }

            }

            if (datasetBean.getReceiptMethods() != null &&
                    !datasetBean.getReceiptMethods().isEmpty()) {

                for (ReceiptMethodBean receiptMethodBean : datasetBean.getReceiptMethods()) {

                    ReceiptMethodVO receiptMethodVO = new ReceiptMethodVO();

                    receiptMethodVO.setIdentifier(receiptMethodBean.getIdentifier());

                    receiptMethodVO.setDenomination(receiptMethodBean.getDenomination());

                    if (database.receiptMethodDAO().exists(receiptMethodVO.getIdentifier()))

                        database.receiptMethodDAO().update(receiptMethodVO);

                    else

                        database.receiptMethodDAO().create(receiptMethodVO);

                }

            }

            if (datasetBean.getPaymentMethods() != null &&
                    !datasetBean.getPaymentMethods().isEmpty()) {

                for (PaymentMethodBean paymentMethodBean : datasetBean.getPaymentMethods()) {

                    PaymentMethodVO paymentMethodVO = new PaymentMethodVO();

                    paymentMethodVO.setIdentifier(paymentMethodBean.getIdentifier());

                    paymentMethodVO.setDenomination(paymentMethodBean.getDenomination());

                    if (database.paymentMethodDAO().exists(paymentMethodVO.getIdentifier()))

                        database.paymentMethodDAO().update(paymentMethodVO);

                    else

                        database.paymentMethodDAO().create(paymentMethodVO);

                }

            }

            database.setTransactionSuccessful();

        } finally {

            if (database.isOpen() && database.inTransaction())

                database.endTransaction();

        }

    }

}