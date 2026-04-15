package tr.edu.mu.se3006;

import tr.edu.mu.se3006.catalog.CatalogService;
import tr.edu.mu.se3006.catalog.CatalogFactory;
import tr.edu.mu.se3006.orders.OrderController;
import tr.edu.mu.se3006.orders.OrdersFactory;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 System Starting in Modular Monolith Mode...");
        System.out.println("----------------------------------------------\n");

        // TODO 1: Create the Catalog Module via its Factory
        // CatalogService catalog = ...
        CatalogService catalog = CatalogFactory.create();

        // TODO 2: Create the Orders Module via its Factory, passing the catalog module
        // OrderController controller = ...
        OrderController controller = OrdersFactory.create(catalog);

        System.out.println("--- Test Scenarios ---");
        // TODO 3: Call handleUserRequest via the controller to test the system
        // e.g., controller.handleUserRequest(1L, 2);
        controller.handleUserRequest(1L, 2); // Valid order
        controller.handleUserRequest(1L, 100); // Invalid order (insufficient stock)
        controller.handleUserRequest(999L, 1); // Invalid order (non-existent product)
        controller.handleUserRequest(1L, -5); // Invalid order (negative quantity)
    }
}
