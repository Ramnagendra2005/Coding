import java.util.Scanner;

public class Bank {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final Customer customer = new Customer();

        // Withdrawal Thread
        new Thread(() -> {
            System.out.print("Enter withdrawal amount: ");
            int withdrawAmount = sc.nextInt();
            customer.withdraw(withdrawAmount);
        }).start();

        // Deposit Thread
        new Thread(() -> {
            System.out.print("Enter deposit amount: ");
            int depositAmount = sc.nextInt();
            customer.deposit(depositAmount);
        }).start();

        sc.close();
    }
}