import java.util.Scanner;
public class Practice {
    public class Customer{
      int amount = 1000;
      synchronized void withdraw(int amount){
         System.out.println("The withdraw is started");
         if(this.amount<amount){
            try{
               wait();
            }catch(Exception e){}
            this.amount-=amount;
            System.out.println("Amount withdraw completed balance is : "+this.amount);
      
      }
    }
    synchronized void deposit(int amount) {
      System.out.println("The process of deposit initiated");
      this.amount += amount;
      System.out.println("Deposit of " + amount + " completed. New balance: " + this.amount);
      notify(); // Notify waiting threads
  }
}
public static void main(String[] args) {
   Practice obj = new Practice();
   Customer c = obj.new Customer();
   Scanner sc = new Scanner(System.in);
   new Thread(()->{
      
      c.withdraw(10000);
   }).start();
   new Thread(() -> {
      c.deposit(20000);
  }).start();
  sc.close();
}
}
