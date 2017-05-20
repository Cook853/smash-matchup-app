package smash.model;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lauren on 5/11/2017.
 */
@Entity
public class User {

    @NotNull
    @Size(min=4, max=15)
    private String username;

    private String password;

    private String salt;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Matchup> matchups = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Fighter> fighters = new ArrayList<>();

    @Id
    @GeneratedValue
    private int id;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public List<Fighter> getFighters() {
        return fighters;
    }

    public List<Matchup> getAllMatchups() {
        return matchups;
    }

    public List<Matchup> getFighterMatchups(Fighter fighter) {
        ArrayList<Matchup> allFighterMatchups = new ArrayList<Matchup>();
        List<Matchup> allMatchups = this.getAllMatchups();
        for (Matchup aMatchup : allMatchups) {
            if (aMatchup.getFighter().getId() == fighter.getId()) {
                allFighterMatchups.add(aMatchup);
            }
        }
        return allFighterMatchups;
    }
}
