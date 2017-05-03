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
    private Fighter fighter;

    private int fighterMatchupValue;

    private int opponentId;

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

    public Fighter getfighter() {
        return fighter;
    }

    public void setFighter(Fighter fighter) {
        this.fighter = fighter;
    }

    public int getFighterMatchupValue() {
        return fighterMatchupValue;
    }

    public void setFighterMatchupValue(int fighterMatchupValue) {
        this.fighterMatchupValue = fighterMatchupValue;
    }

    public int getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(int opponentId) {
        this.opponentId = opponentId;
    }
}
