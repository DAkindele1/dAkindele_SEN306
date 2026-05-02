package Exercise;

public class LegacyOrderFacade {
    private final LegacyOrderProcessor legacyOrderProcessor;

    public LegacyOrderFacade() {
        this(new LegacyOrderProcessor());
    }

    public LegacyOrderFacade(LegacyOrderProcessor legacyOrderProcessor) {
        this.legacyOrderProcessor = legacyOrderProcessor;
    }

    public void placeOrder(String customerEmail, String itemCode, double amount, String deliveryAddress) {
        legacyOrderProcessor.processOrder(customerEmail, itemCode, amount, deliveryAddress);
    }
}