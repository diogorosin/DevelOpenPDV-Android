package br.com.developen.pdv.jersey;

public class ServiceBean extends ProgenyBean {

	private Integer catalog;
	
	private Integer position;

	private Integer reference;

	private String label;

	private Integer measureUnit;

	private Double price;

	public Integer getCatalog() {
		
		return catalog;
		
	}

	public void setCatalog(Integer catalog) {

		this.catalog = catalog;

	}

	public Integer getPosition() {

		return position;

	}

	public void setPosition(Integer position) {
		
		this.position = position;
		
	}

	public Integer getReference() {
		
		return reference;
		
	}

	public void setReference(Integer reference) {
		
		this.reference = reference;
		
	}

	public String getLabel() {
		
		return label;
		
	}

	public void setLabel(String label) {
		
		this.label = label;
		
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

}