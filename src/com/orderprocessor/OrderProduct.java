package com.orderprocessor;

public class OrderProduct {
	private String orderId;
	private String productId;
	private String status;

	public OrderProduct(String orderId, String productId) {
		this.orderId = orderId;
		this.productId = productId;
		this.status = "in progress";
	}

	public String getOrderId() {
		return orderId;
	}

	public String getProductId() {
		return productId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
