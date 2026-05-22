
import java.io.Serializable;

public abstract class Person implements Serializable {

    private static final long serialVersionUID = 5L;

    private final String id;
    private final String name;
    private final String email;

    public Person(String id, String name, String email) {
        this.id    = id;
        this.name  = name;
        this.email = email;
    }

    // Abstract — subclasses must declare their role
    public abstract String getRole();

    // Shared getters
    public String getId()    { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("%s | Name: %s | Email: %s", getRole(), name, email);
    }
}
