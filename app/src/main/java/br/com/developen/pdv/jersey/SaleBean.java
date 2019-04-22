package br.com.developen.pdv.jersey;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class SaleBean implements Serializable {


	private Integer identifier;

	private Integer number;

	private String status;

	private Date dateTime;

	private Integer user;

	private String note;

	private Map<Integer, SaleItemBean> items;


	public Integer getIdentifier() {

		return identifier;

	}

	public void setIdentifier(Integer identifier) {

		this.identifier = identifier;

	}

	public Integer getNumber() {

		return number;

	}

	public void setNumber(Integer number) {

		this.number = number;

	}

	public String getStatus() {

		return status;

	}

	public void setStatus(String status) {

		this.status = status;

	}

	public Date getDateTime() {

		return dateTime;

	}

	public void setDateTime(Date dateTime) {

		this.dateTime = dateTime;

	}

	public Integer getUser() {

		return user;

	}

	public void setUser(Integer user) {

		this.user = user;

	}

	public String getNote() {

		return note;

	}

	public void setNote(String note) {

		this.note = note;

	}

	public Map<Integer, SaleItemBean> getItems() {

		if (items==null)

			items = new HashMap<Integer, SaleItemBean>();

		return items;

	}

	public void setItems(Map<Integer, SaleItemBean> items) {

		this.items = items;

	}

}