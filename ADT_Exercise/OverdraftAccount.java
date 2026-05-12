public class OverdraftAccount extends BankAccount {
    private static final double OVERDRAFT_LIMIT = -500.0;

    @Override
    public void deposit(double amount) {
        double before = balance;
        super.deposit(amount);
        System.out.println("Deposit: amount=" + amount + ", before=" + before + ", after=" + balance);
    }

    @Override
    public void withdraw(double amount) {
        double before = balance;
        boolean approved = amount > 0 && (balance - amount) >= OVERDRAFT_LIMIT;
        if (approved) {
            balance -= amount;
        }
        System.out.println(
            "Withdraw: amount=" + amount + ", approved=" + approved + ", before=" + before + ", after=" + balance
        );
    }
}
