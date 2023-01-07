public class Order {
	private String id;
	private int numProducts;
	private String status;

	public Order(String id, int numProducts) {
		this.id = id;
		this.numProducts = numProducts;
		// The initial status of an order is "in progress"
		// because it has been placed but not shipped yet
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
