public class OrderProduct {
	private String orderId;
	private String productId;
	private String status;
	private int abandoned;

	public OrderProduct(String orderId, String productId) {
		this.orderId = orderId;
		this.productId = productId;
		// The initial status of a product is "in progress"
		// because it has been added in cart but not shipped yet
		this.status = "in progress";
		// The abandoned variable is used to check if a product
		// was added in the cart but the order wasn't placed
		this.abandoned = 0;
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

	public int getAbandoned() {
		return abandoned;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public void setAbandoned(int abandoned) {
		this.abandoned = abandoned;
	}

}
