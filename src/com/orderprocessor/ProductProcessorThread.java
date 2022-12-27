package com.orderprocessor;

import java.util.List;
import java.util.concurrent.locks.Lock;

public class ProductProcessorThread extends Thread {
	private List<OrderProduct> orderProducts;
	private Lock orderProductsLock;

	public ProductProcessorThread(List<OrderProduct> orderProducts, Lock orderProductsLock) {
		this.orderProducts = orderProducts;
		this.orderProductsLock = orderProductsLock;
	}

	@Override
	public void run() {
		for (OrderProduct orderProduct : orderProducts) {
			orderProductsLock.lock();
			try {
				orderProduct.setStatus("shipped");
			} finally {
				orderProductsLock.unlock();
			}
		}
	}
}
