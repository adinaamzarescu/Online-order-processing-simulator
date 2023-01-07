import java.util.List;
import java.util.concurrent.locks.Lock;

public class OrderProcessor {
	private List<Order> orders;
	private List<OrderProduct> orderProducts;  // Add member variable for orderProducts
	private Lock ordersLock;
	private Lock orderProductsLock;

	public OrderProcessor(List<Order> orders, Lock ordersLock, Lock orderProductsLock, List<OrderProduct> orderProducts) {
		this.orders = orders;
		this.ordersLock = ordersLock;
		this.orderProductsLock = orderProductsLock;
		this.orderProducts = orderProducts;
	}

	public void processOrder(Order order) {
		// Check if all products in the order have been shipped
		orderProductsLock.lock();
		try {
			boolean allShipped = true;
			for (OrderProduct op : orderProducts) {
				// If the status of a products is still "in progress" then the order can't be shipped yet
				if (op.getOrderId().equals(order.getId()) && op.getStatus().equals("in progress")) {
					allShipped = false;
					break;
				}
			}
			// Ship an order only if all the products have been shipped
			if (allShipped) {
				// Acquire the lock before accessing the orders list
				ordersLock.lock();
				try {
					order.setStatus("shipped");
				} finally {
					// Release the lock after accessing the orders list
					ordersLock.unlock();
				}
			}
		} finally {
			orderProductsLock.unlock();
		}
	}

	public List<Order> getOrders() {
		return orders;
	}
}
