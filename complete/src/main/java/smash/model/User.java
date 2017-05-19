package smash.model;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * Created by Lauren on 5/11/2017.
 */
@Entity
public class User {

    @NotNull
    @Size(min=5, max=15)
    private String username;

    private String password;

    @Id
    @GeneratedValue
    private int id;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }

    public void setPassword(String unhashedPassword) {
        this.password = unhashedPassword;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
