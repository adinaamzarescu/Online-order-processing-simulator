package com.orderprocessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadInputFiles {
	public static List<Order> readOrders(String filePath) {
		List<Order> orders = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				String id = parts[0];
				int numProducts = Integer.parseInt(parts[1]);
				orders.add(new Order(id, numProducts));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return orders;
	}

	public static List<OrderProduct> readOrderProducts(String filePath) {
		List<OrderProduct> orderProducts = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				String orderId = parts[0];
				String productId = parts[1];
				orderProducts.add(new OrderProduct(orderId, productId));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return orderProducts;
	}
}
