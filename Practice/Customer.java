class Customer {
    int amount = 1000;

    synchronized void withdraw(int amount) {
        System.out.println("The process of withdrawal initiated");
        while (this.amount < amount) { // Use while to handle spurious wakeups
            System.out.println("Insufficient funds, waiting for deposit...");
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.amount -= amount;
        System.out.println("Withdrawal of " + amount + " completed. Remaining balance: " + this.amount);
    }

    synchronized void deposit(int amount) {
        System.out.println("The process of deposit initiated");
        this.amount += amount;
        System.out.println("Deposit of " + amount + " completed. New balance: " + this.amount);
        notify(); // Notify waiting threads
    }

    void showBalance() {
        System.out.println("The balance is: " + this.amount);
    }
}