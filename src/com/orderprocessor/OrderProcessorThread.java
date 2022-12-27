package com.orderprocessor;
import java.util.List;
public class OrderProcessorThread extends Thread {
	private OrderProcessor orderProcessor;

	public OrderProcessorThread(OrderProcessor orderProcessor) {
		this.orderProcessor = orderProcessor;
	}

	@Override
	public void run() {
		List<Order> orders = orderProcessor.getOrders();
		for (Order order : orders) {
			orderProcessor.processOrder(order);
		}
	}
}
