package br.com.developen.pdv.jersey;

public class ConfigurationBean {

    private String deviceSerialNumber;

    private String deviceManufacturer;

    private String deviceModel;

    private String deviceAlias;

    public String getDeviceSerialNumber() {

        return deviceSerialNumber;

    }

    public void setDeviceSerialNumber(String deviceSerialNumber) {

        this.deviceSerialNumber = deviceSerialNumber;

    }

    public String getDeviceManufacturer() {

        return deviceManufacturer;

    }

    public void setDeviceManufacturer(String deviceManufacturer) {

        this.deviceManufacturer = deviceManufacturer;

    }

    public String getDeviceModel() {

        return deviceModel;

    }

    public void setDeviceModel(String deviceModel) {

        this.deviceModel = deviceModel;

    }

    public String getDeviceAlias() {

        return deviceAlias;

    }

    public void setDeviceAlias(String deviceAlias) {

        this.deviceAlias = deviceAlias;

    }

}