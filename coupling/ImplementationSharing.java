// StripePaymentService exposes its business logic
public class StripePaymentService {
    private String apiKey = "sk_test_12345";

    public boolean processPayment(String orderId, double amount) {
        // Logic to interact with Stripe's API using apiKey
        System.out.println("Processing payment for Order: " + orderId + " with Stripe API.");
        return true;
    }

    public String getPaymentApiKey() {
        return apiKey;  // Exposing internal logic
    }
}

// InventoryService exposes its internal logic
public class RemoteInventoryService {
    private Map<String, Integer> inventoryMap = new HashMap<>();

    public RemoteInventoryService() {
        inventoryMap.put("item123", 10);
    }

    public boolean reserveItems(String orderId, Map<String, Integer> items) {
        // Directly working with inventory data
        for (Map.Entry<String, Integer> item : items.entrySet()) {
            if (inventoryMap.getOrDefault(item.getKey(), 0) < item.getValue()) {
                return false;
            }
        }
        return true;
    }

    public Map<String, Integer> getInventoryData() {
        return inventoryMap;  // Exposing internal logic
    }
}

// OrderService relies on business logic of other services
public class OrderService {
    private final StripePaymentService paymentService;
    private final RemoteInventoryService inventoryService;

    public OrderService(StripePaymentService paymentService, RemoteInventoryService inventoryService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }

    public void placeOrder(String orderId, Map<String, Integer> items, double amount) {
        // Directly accessing inventory and payment business logic
        if (inventoryService.getInventoryData().getOrDefault("item123", 0) >= 2) {
            System.out.println("Using Stripe API Key: " + paymentService.getPaymentApiKey());

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

