package br.com.developen.pdv.jersey;

public class ProductProductBean {

	private Integer parent;

	private Integer child;

	private Boolean active;

	private Double quantity;

	public Integer getParent() {
		
		return parent;
		
	}

	public void setParent(Integer parent) {

		this.parent = parent;

	}

	public Integer getChild() {

		return child;

	}

	public void setChild(Integer child) {

		this.child = child;

	}

	public Boolean getActive() {

		return active;

	}

	public void setActive(Boolean active) {

		this.active = active;

	}

	public Double getQuantity() {

		return quantity;

	}

	public void setQuantity(Double quantity) {

		this.quantity = quantity;

	}

}