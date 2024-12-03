package factories;

import models.Product;

public class ProductFactory {
	public static Product create(String product_id, String product_name, String product_description, String vendor_id) {
		return new Product(product_id, product_name, product_description, vendor_id);
	}

}
