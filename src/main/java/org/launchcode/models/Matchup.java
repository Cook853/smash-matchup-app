package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Lauren on 4/18/2017.
 */
@Entity
public class Matchup {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Fighter fighterOne;

    private int fighterOneMatchup;

    @ManyToOne
    private Fighter fighterTwo;

    private int fighterTwoMatchup;

    public Matchup() {

    }
//
//    public Matchup(Fighter fighterOne, int fighterOneMatchup,
//                   Fighter fighterTwo, int fighterTwoMatchup) {
//        this.fighterOne = fighterOne;
//        this.fighterOneMatchup = fighterOneMatchup;
//        this.fighterTwo = fighterTwo;
//        this.fighterTwoMatchup = fighterTwoMatchup;
//    }

    public int getId() {
        return id;
    }

    public Fighter getFighterOne() {
        return fighterOne;
    }

    public void setFighterOne(Fighter fighterOne) {
        this.fighterOne = fighterOne;
    }

    public int getFighterOneMatchup() {
        return fighterOneMatchup;
    }

    public void setFighterOneMatchup(int fighterOneMatchup) {
        this.fighterOneMatchup = fighterOneMatchup;
    }

    public Fighter getFighterTwo() {
        return fighterTwo;
    }

    public void setFighterTwo(Fighter fighterTwo) {
        this.fighterTwo = fighterTwo;
    }

    public int getFighterTwoMatchup() {
        return fighterTwoMatchup;
    }

    public void setFighterTwoMatchup(int fighterTwoMatchup) {
        this.fighterTwoMatchup = fighterTwoMatchup;
    }
}
