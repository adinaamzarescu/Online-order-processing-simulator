import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.orderprocessor.Order;
import com.orderprocessor.OrderProduct;
import com.orderprocessor.OrderProcessor;
import com.orderprocessor.OrderProcessorThread;
import com.orderprocessor.ProductProcessorThread;
import com.orderprocessor.ReadInputFiles;
import com.orderprocessor.WriteOutputFiles;

public class a {
	public static void main(String[] args) {
		// Parse command line arguments
		String inputFolder = args[0];
		int numThreads = Integer.parseInt(args[1]);

		// Read input files
		List<Order> orders = ReadInputFiles.readOrders(inputFolder + "/orders.txt");
		List<OrderProduct> orderProducts = ReadInputFiles.readOrderProducts(inputFolder + "/order_products.txt");

		// Create locks for shared resources
		Lock ordersLock = new ReentrantLock();
		Lock orderProductsLock = new ReentrantLock();

		// Create and start order processing threads
		OrderProcessor orderProcessor = new OrderProcessor(orders, ordersLock, orderProductsLock, orderProducts);
		int numOrderThreads = numThreads / 2;
		Thread[] orderThreads = new Thread[numOrderThreads];
		for (int i = 0; i < numOrderThreads; i++) {
			orderThreads[i] = new OrderProcessorThread(orderProcessor);
			orderThreads[i].start();
		}

		// Create and start product processing threads
		int numProductThreads = numThreads / 2;
		Thread[] productThreads = new Thread[numProductThreads];
		for (int i = 0; i < numProductThreads; i++) {
			productThreads[i] = new ProductProcessorThread(orderProducts, orderProductsLock);
			productThreads[i].start();
		}

		// Wait for all threads to finish
		try {
			for (int i = 0; i < numOrderThreads; i++) {
				orderThreads[i].join();
			}
			for (int i = 0; i < numProductThreads; i++) {
				productThreads[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Write output files
		WriteOutputFiles.writeOrders(inputFolder + "/orders_out.txt", orders);
		WriteOutputFiles.writeOrderProducts(inputFolder + "/order_products_out.txt", orderProducts);
	}
}

