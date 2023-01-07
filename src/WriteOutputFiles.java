import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteOutputFiles {
	public static void writeOrders(String filePath, List<Order> orders) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			for (Order order : orders) {
				String line = String.format("%s,%d,%s", order.getId(), order.getNumProducts(), order.getStatus());
				// Write only the orders that have at least one product
				if (order.getNumProducts() != 0) {
					bw.write(line);
					bw.newLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeOrderProducts(String filePath, List<OrderProduct> orderProducts) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			for (OrderProduct orderProduct : orderProducts) {
				String line = String.format("%s,%s,%s", orderProduct.getOrderId(), orderProduct.getProductId(), orderProduct.getStatus());
				// Write only the products that exist and weren't abandoned
				if (orderProduct.getAbandoned() == 0) {
					bw.write(line);
					bw.newLine();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
