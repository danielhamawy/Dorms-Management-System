
public class Manager extends Person {

    private static final long serialVersionUID = 7L;

    private final String department;

    public Manager(String id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
    }

    @Override
    public String getRole() { return "Manager"; }

    public String getDepartment() { return department; }
}
