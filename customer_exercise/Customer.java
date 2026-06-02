package week12;

// Using a record provides an immutable, low-maintenance struct to reduce parameter count.
public record Customer(String name, String address, double balance, int type, String email, boolean isVip) {}

