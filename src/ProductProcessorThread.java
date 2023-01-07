import java.util.List;
import java.util.concurrent.locks.Lock;

public class ProductProcessorThread extends Thread {
	private ProductProcessor orderProducts;
	private Lock orderProductsLock;

	public ProductProcessorThread(ProductProcessor orderProducts, Lock orderProductsLock) {
		this.orderProducts = orderProducts;
		this.orderProductsLock = orderProductsLock;
	}

	@Override
	public void run() {
		List<OrderProduct> orderProductsList = orderProducts.getProducts();
		for (OrderProduct orderProduct : orderProductsList) {
			// Process each product
			orderProducts.processOrderProducts(orderProduct);
			// Ship the products
			if (orderProduct.getAbandoned() == 0) {
				orderProductsLock.lock();
				try {
					orderProduct.setStatus("shipped");
				} finally {
					orderProductsLock.unlock();
				}
			}
		}
	}
}
