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

    public int getId() {
        return id;
    }

    public Fighter getFighter() {
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
