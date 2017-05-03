package org.launchcode.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lauren on 4/18/2017.
 */
@Entity
public class Fighter {

    @Id
    @GeneratedValue
    private int id;

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


