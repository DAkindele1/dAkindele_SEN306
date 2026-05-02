package Exercise;

public class CheckOutFacadeDemo {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Payment payment = new Payment();
        Shipping shipping = new Shipping();
        Email email = new Email();
        
        CheckOutFacade facade = new CheckOutFacade(inventory, payment, shipping, email);
        
        System.out.println("=== Test Case 1: Successful Order ===");
        OrderResult result1 = facade.checkout(
            "PROD123",
            "USER001",
            49.99,
            "123 Main St, City, State 12345",
            "customer@email.com"
        );
        System.out.println("Success: " + result1.isSuccess());
        System.out.println("Tracking Number: " + result1.getTrackingNumber());
        System.out.println("Message: " + result1.getMessage());
        
        System.out.println("\n=== Test Case 2: Another Order ===");
        OrderResult result2 = facade.checkout(
            "PROD456",
            "USER002",
            99.99,
            "456 Oak Ave, Town, State 67890",
            "buyer@email.com"
        );
        System.out.println("Success: " + result2.isSuccess());
        System.out.println("Tracking Number: " + result2.getTrackingNumber());
        System.out.println("Message: " + result2.getMessage());
    }
}