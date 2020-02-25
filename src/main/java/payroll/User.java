package payroll;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class User {
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String description;

    User() {}

    User(String firstName, String lastName, String email, String role, String description){
        this.firstName       = firstName;
        this.lastName        = lastName;
        this.role            = role;
        this.description     = description;
    }
    public String getName(){
        return this.firstName + " " + this.lastName;
    }
    public void setName(String name){
        String[] parts       = name.split(" ");
        this.firstName       = parts[0];
        this.lastName        = parts[1];

    }
}
