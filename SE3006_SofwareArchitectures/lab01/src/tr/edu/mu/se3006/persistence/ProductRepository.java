package tr.edu.mu.se3006.persistence;

import tr.edu.mu.se3006.domain.Product;
import java.util.*;

public class ProductRepository {
    private Map<Long, Product> database = new HashMap<>();

    public ProductRepository() {
        database.put(1L, new Product(1L, "MacBook Pro", 5));
        database.put(2L, new Product(2L, "Logitech Mouse", 20));
    }

    // TODO: Write findById(Long id) method

    public Product findById(Long id) {
        if (database.containsKey(id)) {
            System.out.println(database.get(id));
            return database.get(id);
        } else {
            System.out.println("Product not found.");
            return null;
        }
    }

    // TODO: Write save(Product product) method

    public void save(Product product) {
        database.put(product.getId(), product);
        System.out.println("Product saved: " + product);
    }
}
