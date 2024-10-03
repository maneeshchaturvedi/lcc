// Contract for PaymentService
public interface PaymentService {
    boolean processPayment(String orderId, double amount);
}

// Contract for InventoryService
public interface InventoryService {
    boolean reserveItems(String orderId, Map<String, Integer> items);
}

// Concrete PaymentService (Stripe)
public class StripePaymentService implements PaymentService {
    @Override
    public boolean processPayment(String orderId, double amount) {
        // Implementation logic using Stripe's API
        return true;  // Assume success
    }
}

// Concrete InventoryService
public class RemoteInventoryService implements InventoryService {
    @Override
    public boolean reserveItems(String orderId, Map<String, Integer> items) {
        // Implementation logic to reserve items in the inventory system
        return true;  // Assume items are available
    }
}

// OrderService interacts with services using contracts
public class OrderService {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;

    public OrderService(PaymentService paymentService, InventoryService inventoryService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }

    public void placeOrder(String orderId, Map<String, Integer> items, double amount) {
        if (inventoryService.reserveItems(orderId, items)) {
            if (paymentService.processPayment(orderId, amount)) {
                System.out.println("Order processed successfully.");
            } else {
                System.out.println("Payment failed.");
            }
        } else {
            System.out.println("Items unavailable.");
        }
    }
}

// Main program demonstrating contract sharing
public class Main {
    public static void main(String[] args) {
        PaymentService paymentService = new StripePaymentService();
        InventoryService inventoryService = new RemoteInventoryService();
        OrderService orderService = new OrderService(paymentService, inventoryService);

        Map<String, Integer> items = new HashMap<>();
        items.put("item123", 2);

        orderService.placeOrder("order123", items, 150.0);
    }
}
