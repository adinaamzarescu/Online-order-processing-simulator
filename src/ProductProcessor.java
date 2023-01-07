
import java.util.List;
import java.util.concurrent.locks.Lock;

public class ProductProcessor {
	private List<Order> orders;
	private List<OrderProduct> orderProducts; // Add member variable for orderProducts
	private Lock orderProductsLock;

	public ProductProcessor(List<Order> orders, Lock orderProductsLock, List<OrderProduct> orderProducts) {
		this.orders = orders;
		this.orderProductsLock = orderProductsLock;
		this.orderProducts = orderProducts;
	}

	public void processOrderProducts(OrderProduct orderProduct) {
		// At first all products are considered not abandoned
		boolean notAbandoned = true;
		// At first no product exists. If the product is found
		// within an order then the variable becomes true
		boolean exists = false;
		for (Order orderIndex : orders) {
			// Check if the order has been placed
			if (orderIndex.getId().equals(orderProduct.getOrderId()) && orderIndex.getNumProducts() == 0) {
				notAbandoned = false;
				break;
			}
			// Check if the product exists
			if (orderIndex.getId().equals(orderProduct.getOrderId())) {
				exists = true;
			}
		}
		// If the product is abandoned or doesn't exist then it won't be shipped
		if (!notAbandoned || !exists) {
			// Acquire the lock before accessing the products list
			orderProductsLock.lock();
			try {
				orderProduct.setAbandoned(1);
			} finally {
				// Release the lock after accessing the products list
				orderProductsLock.unlock();
			}
		} 
	}

	public List<OrderProduct> getProducts() {
		return orderProducts;
	}
}