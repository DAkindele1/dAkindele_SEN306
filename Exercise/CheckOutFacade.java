package Exercise;

public class CheckOutFacade {
    private Inventory inventory;
    private Payment payment;
    private Shipping shipping;
    private Email email;
    private TaxCalculator taxCalculator;
    private Logger logger;
    
    public CheckOutFacade(Inventory inventory, Payment payment, Shipping shipping, Email email) {
        this(inventory, payment, shipping, email, new TaxCalculator(), new Logger());
    }

    public CheckOutFacade(Inventory inventory, Payment payment, Shipping shipping, Email email,
                          TaxCalculator taxCalculator, Logger logger) {
        this.inventory = inventory;
        this.payment = payment;
        this.shipping = shipping;
        this.email = email;
        this.taxCalculator = taxCalculator;
        this.logger = logger;
    }
    
    public OrderResult checkout(String productId, String userId, double amount, String address, String userEmail) {
        String state = extractState(address);
        double tax = taxCalculator.calculateTax(amount, state);
        double totalPrice = amount + tax;
        long timestamp = System.currentTimeMillis();
        String logMessage = timestamp + " | userId=" + userId;

        if (!inventory.checkStock(productId)) {
            logger.logCheckoutAttempt(userId, false);
            return new OrderResult(false, null, "Product out of stock");
        }

        inventory.reserve(productId);
        
        if (!payment.charge(userId, amount)) {
            inventory.release(productId);
            logger.logCheckoutAttempt(userId, false);
            return new OrderResult(false, null, "Payment failed");
        }
        
        String trackingNumber = shipping.createLabel(address);
        
        if (!shipping.isAvailable()) {
            payment.refund(userId, amount);
            inventory.release(productId);
            logger.logCheckoutAttempt(userId, false);
            return new OrderResult(false, null, "Shipping unavailable");
        }
        shipping.schedulePickup(trackingNumber);
        
        email.send(
            userEmail,
            "Order Confirmation",
            "Your tracking number: " + trackingNumber + "\n"
            + "Total price: " + totalPrice + "\n"
            + "Log: " + logMessage + " | Success"
        );
        logger.logCheckoutAttempt(userId, true);
        
        return new OrderResult(true, trackingNumber, "Order completed successfully");
    }

    private String extractState(String address) {
        if (address == null) {
            return "";
        }

        String[] parts = address.split(",");
        if (parts.length < 1) {
            return "";
        }

        String lastSegment = parts[parts.length - 1].trim();
        String[] lastSegmentParts = lastSegment.split("\\s+");
        return lastSegmentParts.length == 0 ? "" : lastSegmentParts[0].trim();
    }
}

class TaxCalculator {
    double calculateTax(double amount, String state) {
        double rate = "CA".equalsIgnoreCase(state) ? 0.08 : 0.0;
        return amount * rate;
    }
}

class Logger {
    void logCheckoutAttempt(String userId, boolean success) {
        System.out.println(System.currentTimeMillis() + " | userId=" + userId + " | " + (success ? "Success" : "Failure"));
    }
}