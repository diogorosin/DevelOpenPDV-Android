package br.com.developen.pdv.jersey;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class CompanyBean extends OrganizationBean {


    @JsonIgnore
    private Integer level;

    private String couponTitle;

    private String couponSubtitle;


    @Deprecated
    public Integer getLevel() {

        return level;

    }

    @Deprecated
    public void setLevel(Integer level) {

        this.level = level;

    }

    public String getCouponTitle() {

        return couponTitle;

    }

    public void setCouponTitle(String couponTitle) {

        this.couponTitle = couponTitle;

    }

    public String getCouponSubtitle() {

        return couponSubtitle;

    }

    public void setCouponSubtitle(String couponSubtitle) {

        this.couponSubtitle = couponSubtitle;

    }


}