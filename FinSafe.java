import java.util.*;
//CUSTOM EXCEPTION
class InSufficientFundsException extends Exception{
    public InSufficientFundsException(String message){
        super(message);
    }
}
// Account class (Encapsulation)
class Account{
    private double balance;
    private String accountHolder;
    private List<Double> transactions=new ArrayList<>();
    public Account(double balance, String accountHolder){
        this.balance=balance;
        this.accountHolder=accountHolder;
    }
    // PROCESS TRANSACTION METHOD TO VALIDATE INPUT FOR DEPOSIT AND WITHDRAWL
    private void processTransaction(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Amount must be positive");
    }
    }
    public void deposit(double amount){
        processTransaction(amount);
        balance+=amount;
        addTransaction(amount);
        System.out.println("Deposited: Rs. " + amount);
    }
    public void withdraw(double amount) throws InSufficientFundsException{
        processTransaction(amount);
        // MAKING THE WALLET ROBUST BY CHECKING WITHDRAWL AND BALANCE
        if (amount>balance)
            throw new InSufficientFundsException("Insufficient funds!");
        balance-=amount;
        addTransaction(-amount);
        System.out.println("Withdrawn: Rs. " + amount);
    }
    // STORING PREVIOUS 5 TRANSACTIONS TO DISPLAY TO THE USER
    private void addTransaction(double amount){
        if (transactions.size()==5)
            transactions.remove(0);
        transactions.add(amount);
    }
    // DISPLAY OF MINISTATEMENT
    public void printMiniStatement(){
        System.out.println("\nPrevious 5 Transactions");
        for (double t : transactions){
            if (t > 0)
                System.out.println("Deposit: Rs. " + t);
            else
                System.out.println("Withdraw: Rs. " + (-t));
        }
        System.out.println("Current Balance: Rs. " + balance);
    }
}
// Validating password
class PasswordValidator{
    public static boolean isValidPassword(String password){
        boolean hasUpperCase=false;
        boolean hasDigit=false;
        boolean hasLowerCase=false;
        if (password.length() < 8){
            System.out.println("Too short (minimum 8 characters)");
            return false;
        }
        for (int i=0; i<password.length(); i++){
            char ch=password.charAt(i);
            if (Character.isUpperCase(ch))
                hasUpperCase=true;
            if (Character.isDigit(ch))
                hasDigit=true;
            if (Character.isLowerCase(ch))
                hasLowerCase=true;
        }
        if (!hasUpperCase)
            System.out.println("Missing uppercase letter");
        if (!hasLowerCase)
            System.out.println("Missing lowercase letter");
        if (!hasDigit)
            System.out.println("Missing digit");
        return hasUpperCase && hasDigit && hasLowerCase;
    }
}
// Main clas
public class FinSafe{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("Welcome to FinSafe!");
        while (true) {
            // Users create valid password 
            System.out.print("Create Password: ");
            String pwd=sc.nextLine();
            if (PasswordValidator.isValidPassword(pwd)){
                System.out.println("Password accepted!\n");
                break;
            } else {
                System.out.println("Try again.\n");
            }
        }
        System.out.println("Enter Account Holder name:");
        String accountHolder=sc.nextLine();
        Account acc=new Account(0,accountHolder);
        while (true) {
            System.out.println("\n1. Deposit\n2. Withdraw\n3. View History\n4. Exit");
            System.out.print("Enter choice: ");
            int choice=sc.nextInt();
            try {
                switch (choice){
                    case 1:
                        System.out.print("Enter amount: ");
                        double d=sc.nextDouble();
                        acc.deposit(d);
                        break;
                    case 2:
                        System.out.print("Enter amount: ");
                        double w=sc.nextDouble();
                        acc.withdraw(w);
                        break;
                    case 3:
                        acc.printMiniStatement();
                        break;
                    case 4:
                        System.out.println("Thank you "+accountHolder+"!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice");
                }
            } catch (IllegalArgumentException e){
                System.out.println("Error: " + e.getMessage());
            } catch (InSufficientFundsException e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}