package tr.edu.mu.se3006.business;

import tr.edu.mu.se3006.domain.Product;
import tr.edu.mu.se3006.persistence.ProductRepository;

public class OrderService {
    // TODO: Define ProductRepository dependency

    private ProductRepository productRepository;

    // TODO: Implement Constructor Injection

    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void placeOrder(Long productId, int quantity) {
        // TODO 1: Find product via repository
        Product product = productRepository.findById(productId);

        // TODO 2: Check stock (throw IllegalArgumentException if insufficient)
        if (product == null || product.getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for product ID: " + productId);
        }

        // TODO 3: Reduce stock
        int newStock = product.getStock() - quantity;
        product.setStock(newStock);

        // TODO 4: Save updated product
        productRepository.save(product);
    }
}
