package org.launchcode.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lauren on 5/4/2017.
 */
public class Calculator {

    private ArrayList<Fighter> userFighters;

    private Fighter opponentFighter;

    private Iterable<Matchup> allMatchups;

    private ArrayList<Matchup> userFighterMatchups;

    public Calculator(ArrayList<Fighter> userFighters, Fighter opponentFighter,
                      Iterable<Matchup> allMatchups) {

        this.userFighters = userFighters;
        this.opponentFighter = opponentFighter;
        this.allMatchups = allMatchups;
        this.userFighterMatchups = new ArrayList<>();

        for (Fighter aFighter : userFighters) {
            for (Matchup aMatchup : allMatchups){
                if (aMatchup.getFighter().equals(aFighter)) {
                    if (aMatchup.getOpponentId() == opponentFighter.getId()) {
                        userFighterMatchups.add(aMatchup);
                    }
                }
            }
        }
    }

    public Matchup getBestMatchup() {

        Matchup finalBestMatchup = new Matchup();
        userFighterMatchups = getUserFighterMatchups();
        Integer bestMatchupValue = 0;

        for (Matchup aMatchup : userFighterMatchups) {
            if (aMatchup.getFighterMatchupValue() > bestMatchupValue) {
                bestMatchupValue = aMatchup.getFighterMatchupValue();
                finalBestMatchup.setFighterMatchupValue(aMatchup.getFighterMatchupValue());
                finalBestMatchup.setFighter(aMatchup.getFighter());
            }
        }
        return finalBestMatchup;
    }

    public ArrayList<Fighter> getUserFighters() {
        return userFighters;
    }

    public Fighter getOpponentFighter() {
        return opponentFighter;
    }

    public Iterable<Matchup> getAllMatchups() {
        return allMatchups;
    }

    public ArrayList<Matchup> getUserFighterMatchups() {
        return userFighterMatchups;
    }
}
