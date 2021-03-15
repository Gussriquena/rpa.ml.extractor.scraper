package rpa.ml.extractor.model;

import java.math.BigDecimal;

public class ProductOutput {
	
	private String name;
	private BigDecimal price;
	private int salesAmount;
	private String url;
	
	public ProductOutput() {
		
	}
	
	public ProductOutput(String name, BigDecimal price, int salesAmount, String url) {
		this.name = name;
		this.price = price;
		this.salesAmount = salesAmount;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(int salesAmount) {
		this.salesAmount = salesAmount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ProductOutput [name=" + name + ", price=" + price + ", salesAmount=" + salesAmount + ", url=" + url + "]";
	}

}
