
public class Student extends Person {

    private static final long serialVersionUID = 6L;

    private double balance;   // money available to pay fees

    public Student(String id, String name, String email, double balance) {
        super(id, name, email);
        this.balance = balance;
    }

    @Override
    public String getRole() { return "Student"; }

    public double getBalance() { return balance; }
    
    public boolean pay(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;   // insufficient funds
    }
}
