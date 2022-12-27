package com.orderprocessor;

public class Order {
	private String id;
	private int numProducts;
	private String status;

	public Order(String id, int numProducts) {
		this.id = id;
		this.numProducts = numProducts;
		this.status = "in progress";
	}

	public String getId() {
		return id;
	}

	public int getNumProducts() {
		return numProducts;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
