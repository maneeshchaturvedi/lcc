// Shared model: Order
public class Order {
    private String orderId;
    private Map<String, Integer> items;
    private double totalAmount;

    // Constructor, getters, setters, etc.
    public Order(String orderId, Map<String, Integer> items, double totalAmount) {
        this.orderId = orderId;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    // Getters and setters
    public String getOrderId() { return orderId; }
    public Map<String, Integer> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
}

// Shared model: Item
public class Item {
    private String itemId;
    private int quantity;

    // Constructor, getters, setters
    public Item(String itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public String getItemId() { return itemId; }
    public int getQuantity() { return quantity; }
}

// PaymentService working with shared models
public class StripePaymentService {
    public boolean processPayment(Order order) {
        // Logic to process payment using shared Order model
        System.out.println("Processing payment for Order: " + order.getOrderId());
        return true;
    }
}

// InventoryService working with shared models
public class RemoteInventoryService {
    public boolean reserveItems(Order order) {
        // Logic to reserve items using shared Order and Item models
        System.out.println("Reserving items for Order: " + order.getOrderId());
        return true;
    }
}

// OrderService depends on shared model structures
public class OrderService {
    private final StripePaymentService paymentService;
    private final RemoteInventoryService inventoryService;

    public OrderService(StripePaymentService paymentService, RemoteInventoryService inventoryService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }

    public void placeOrder(Order order) {
        if (inventoryService.reserveItems(order)) {
            if (paymentService.processPayment(order)) {
                System.out.println("Order processed successfully.");
            } else {
                System.out.println("Payment failed.");
            }
        } else {
            System.out.println("Items unavailable.");
        }
    }
}

// Main program demonstrating model sharing
public class Main {
    public static void main(String[] args) {
        StripePaymentService paymentService = new StripePaymentService();
        RemoteInventoryService inventoryService = new RemoteInventoryService();
        OrderService orderService = new OrderService(paymentService, inventoryService);

        Map<String, Integer> items = new HashMap<>();
        items.put("item123", 2);
        Order order = new Order("order123", items, 150.0);

        orderService.placeOrder(order);
    }
}
