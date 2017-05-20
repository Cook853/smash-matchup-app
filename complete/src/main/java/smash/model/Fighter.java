package smash.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lauren on 5/15/2017.
 */
@Entity
public class Fighter {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "fighter_id")
    private List<Matchup> matchups = new ArrayList<>();

    private String picUrl;

    @NotNull(message="Must have a name")
    private String name;

    public Fighter(String picUrl, String name) {
        this.picUrl = picUrl;
        this.name = name;
    }

    public Fighter() {

    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Matchup> getMatchups() {
        return matchups;
    }
}
