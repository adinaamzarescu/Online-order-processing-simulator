import java.util.List;
import java.util.concurrent.locks.Lock;

public class OrderProcessorThread extends Thread {
	private OrderProcessor orderProcessor;
	private Lock ordersLock;

	public OrderProcessorThread(OrderProcessor orderProcessor, Lock ordersLock) {
		this.orderProcessor = orderProcessor;
		this.ordersLock = ordersLock;
	}

	@Override
	public void run() {
		List<Order> orders = orderProcessor.getOrders();
		for (Order order : orders) {
			// Process each order
			orderProcessor.processOrder(order);
			// Ship the orders
			ordersLock.lock();
			try {
				order.setStatus("shipped");
			} finally {
				ordersLock.unlock();
			}
		}
	}
}
