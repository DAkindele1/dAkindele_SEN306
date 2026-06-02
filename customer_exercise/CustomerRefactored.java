package week12;

public class CustomerRefactored {

    // Main routine delegating to functionally cohesive sub-routines
    public static double processCustomerOrders(Customer customer, double[] orders) {
        double sum = calculateTotalOrders(orders);
        double discount = calculateDiscount(customer.type());
        double total = sum - (sum * discount);
        
        String message = generateInvoiceMessage(customer, total);
        System.out.println(message);
        
        notifyCustomer(customer.email(), message);
        
        // Return the updated balance to solve the parameter passing issue
        return total; 
    }

    // 1. Noun for function returning a value. Added validation for non-negative orders.
    private static double calculateTotalOrders(double[] orders) {
        double sum = 0;
        for (double order : orders) {
            if (order < 0) {
                throw new IllegalArgumentException("Order value cannot be negative.");
            }
            sum += order;
        }
        return sum;
    }

    // 2. Noun for function returning a value. Added validation for customer type.
    private static double calculateDiscount(int customerType) {
        if (customerType == 1) return 0.1;
        if (customerType == 2) return 0.2;
        if (customerType == 0) return 0.0; // Assuming 0 is a standard tier
        throw new IllegalArgumentException("Invalid customer type provided.");
    }

    // 3. Verb+Object for procedure/function generating data.
    private static String generateInvoiceMessage(Customer customer, double total) {
        String msg = "Hello " + customer.name() + " of " + customer.address() + ", your total is " + total;
        if (customer.isVip()) {
            msg += " (VIP)";
        }
        return msg;
    }

    // 4. Verb+Object for action-oriented procedure.
    private static void notifyCustomer(String email, String message) {
        if (email != null && !email.trim().isEmpty()) {
            sendEmail(email, message);
        }
    }

    // Stub for the email service logic
    private static void sendEmail(String email, String message) {
        // Implementation here
    }
}