package br.com.developen.pdv.jersey;

import java.io.Serializable;



public class SaleItemBean implements Serializable {


	private Integer progeny;

	private Double quantity;

	private Integer measureUnit;

	private Double price;

	private Double total;


	public Integer getProgeny() {

		return progeny;

	}

	public void setProgeny(Integer progeny) {

		this.progeny = progeny;

	}

	public Double getQuantity() {

		return quantity;

	}

	public void setQuantity(Double quantity) {

		this.quantity = quantity;

	}

	public Integer getMeasureUnit() {

		return measureUnit;

	}

	public void setMeasureUnit(Integer measureUnit) {

		this.measureUnit = measureUnit;

	}

	public Double getPrice() {

		return price;

	}

	public void setPrice(Double price) {

		this.price = price;

	}

	public Double getTotal() {

		return total;

	}

	public void setTotal(Double total) {

		this.total = total;

	}

}