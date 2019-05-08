package br.com.developen.pdv.jersey;

import java.util.ArrayList;
import java.util.List;

public class CompanyDeviceDatasetBean {


    private CompanyBean company;

    private DeviceBean device;

    private Boolean allow;

    private String alias;

    private List<OrganizationBean> organizations;

    private List<IndividualBean> individuals;

    private List<UserBean> users;

    private List<MeasureUnitBean> measureUnits;

    private List<MeasureUnitMeasureUnitBean> measureUnitMeasureUnits;

    private List<ProductBean> products;

    private List<ProductProductBean> productProducts;

    private List<ServiceBean> services;

    private List<MerchandiseBean> merchandises;

    private List<CatalogBean> catalogs;

    private List<ReceiptMethodBean> receiptMethods;

    private List<PaymentMethodBean> paymentMethods;

    private List<SaleBean> sales;


    public CompanyBean getCompany() {

        return company;

    }

    public void setCompany(CompanyBean company) {

        this.company = company;

    }

    public DeviceBean getDevice() {

        return device;

    }

    public void setDevice(DeviceBean device) {

        this.device = device;

    }

    public Boolean getAllow() {

        return allow;

    }

    public void setAllow(Boolean allow) {

        this.allow = allow;

    }

    public String getAlias() {

        return alias;

    }

    public void setAlias(String alias) {

        this.alias = alias;

    }

    public List<UserBean> getUsers() {

        if (this.users==null)

            this.users = new ArrayList<>();

        return users;

    }

    public void setUsers(List<UserBean> users) {

        this.users = users;

    }

    public List<OrganizationBean> getOrganizations() {

        if (this.organizations==null)

            this.organizations = new ArrayList<>();

        return organizations;

    }

    public void setOrganizations(List<OrganizationBean> organizations) {

        this.organizations = organizations;

    }

    public List<IndividualBean> getIndividuals() {

        if (this.individuals==null)

            this.individuals = new ArrayList<>();

        return individuals;

    }

    public void setIndividuals(List<IndividualBean> individuals) {

        this.individuals = individuals;

    }

    public List<MeasureUnitBean> getMeasureUnits() {

        if (this.measureUnits==null)

            this.measureUnits = new ArrayList<>();

        return measureUnits;

    }

    public void setMeasureUnits(List<MeasureUnitBean> measureUnits) {

        this.measureUnits = measureUnits;

    }

    public List<MeasureUnitMeasureUnitBean> getMeasureUnitMeasureUnits() {

        if (this.measureUnitMeasureUnits==null)

            this.measureUnitMeasureUnits = new ArrayList<>();

        return measureUnitMeasureUnits;

    }

    public void setMeasureUnitMeasureUnits(List<MeasureUnitMeasureUnitBean> measureUnitMeasureUnits) {

        this.measureUnitMeasureUnits = measureUnitMeasureUnits;

    }

    public List<ProductBean> getProducts() {

        if (this.products==null)

            this.products = new ArrayList<>();

        return products;

    }

    public void setProducts(List<ProductBean> products) {

        this.products = products;

    }

    public List<ProductProductBean> getProductProducts() {

        if (this.productProducts==null)

            this.productProducts = new ArrayList<>();

        return productProducts;

    }

    public void setProductProducts(List<ProductProductBean> productProducts) {

        this.productProducts = productProducts;

    }

    public List<ServiceBean> getServices() {

        if (this.services==null)

            this.services = new ArrayList<>();

        return services;

    }

    public void setServices(List<ServiceBean> services) {

        this.services = services;

    }

    public List<MerchandiseBean> getMerchandises() {

        if (this.merchandises==null)

            this.merchandises = new ArrayList<>();

        return merchandises;

    }

    public void setMerchandises(List<MerchandiseBean> merchandises) {

        this.merchandises = merchandises;

    }

    public List<CatalogBean> getCatalogs() {

        if (this.catalogs==null)

            this.catalogs = new ArrayList<>();

        return catalogs;

    }

    public void setCatalogs(List<CatalogBean> catalogs) {

        this.catalogs = catalogs;

    }

    public List<ReceiptMethodBean> getReceiptMethods() {

        if (this.receiptMethods==null)

            this.receiptMethods = new ArrayList<>();

        return receiptMethods;

    }

    public void setReceiptMethods(List<ReceiptMethodBean> receiptMethods) {

        this.receiptMethods = receiptMethods;

    }

    public List<PaymentMethodBean> getPaymentMethods() {

        if (this.paymentMethods==null)

            this.paymentMethods = new ArrayList<>();

        return paymentMethods;

    }

    public void setPaymentMethods(List<PaymentMethodBean> paymentMethods) {

        this.paymentMethods = paymentMethods;

    }

    public List<SaleBean> getSales() {

        if (this.sales==null)

            this.sales = new ArrayList<>();

        return sales;

    }

    public void setSales(List<SaleBean> sales) {

        this.sales = sales;

    }


}