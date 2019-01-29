package br.com.developen.pdv.jersey;

import java.util.List;

public class DatasetBean {

    private CompanyBean company;

    private List<DeviceBean> devices;

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

    public CompanyBean getCompany() {

        return company;

    }

    public void setCompany(CompanyBean company) {

        this.company = company;

    }

    public List<DeviceBean> getDevices() {

        return devices;

    }

    public void setDevices(List<DeviceBean> devices) {

        this.devices = devices;

    }

    public List<UserBean> getUsers() {

        return users;

    }

    public void setUsers(List<UserBean> users) {

        this.users = users;

    }

    public List<OrganizationBean> getOrganizations() {

        return organizations;

    }

    public void setOrganizations(List<OrganizationBean> organizations) {

        this.organizations = organizations;

    }

    public List<IndividualBean> getIndividuals() {

        return individuals;

    }

    public void setIndividuals(List<IndividualBean> individuals) {

        this.individuals = individuals;

    }

    public List<MeasureUnitBean> getMeasureUnits() {

        return measureUnits;

    }

    public void setMeasureUnits(List<MeasureUnitBean> measureUnits) {

        this.measureUnits = measureUnits;

    }

    public List<MeasureUnitMeasureUnitBean> getMeasureUnitMeasureUnits() {

        return measureUnitMeasureUnits;

    }

    public void setMeasureUnitMeasureUnits(List<MeasureUnitMeasureUnitBean> measureUnitMeasureUnits) {

        this.measureUnitMeasureUnits = measureUnitMeasureUnits;

    }

    public List<ProductBean> getProducts() {

        return products;

    }

    public void setProducts(List<ProductBean> products) {

        this.products = products;

    }

    public List<ProductProductBean> getProductProducts() {

        return productProducts;

    }

    public void setProductProducts(List<ProductProductBean> productProducts) {

        this.productProducts = productProducts;

    }

    public List<ServiceBean> getServices() {

        return services;

    }

    public void setServices(List<ServiceBean> services) {

        this.services = services;

    }

    public List<MerchandiseBean> getMerchandises() {

        return merchandises;

    }

    public void setMerchandises(List<MerchandiseBean> merchandises) {

        this.merchandises = merchandises;

    }

    public List<CatalogBean> getCatalogs() {

        return catalogs;

    }

    public void setCatalogs(List<CatalogBean> catalogs) {

        this.catalogs = catalogs;

    }

    public List<ReceiptMethodBean> getReceiptMethods() {

        return receiptMethods;

    }

    public void setReceiptMethods(List<ReceiptMethodBean> receiptMethods) {

        this.receiptMethods = receiptMethods;

    }

    public List<PaymentMethodBean> getPaymentMethods() {

        return paymentMethods;

    }

    public void setPaymentMethods(List<PaymentMethodBean> paymentMethods) {

        this.paymentMethods = paymentMethods;

    }

}