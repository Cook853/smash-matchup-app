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
                if (aMatchup.getFighter().getId() == aFighter.getId()) {
                    if (aMatchup.getOpponentId() == opponentFighter.getId()) {
                        userFighterMatchups.add(aMatchup);
                    }
                }
            }
        }
    }

    public int getBestMatchupId() {

        ArrayList<Matchup> userFighterMatchups = getUserFighterMatchups();
        int bestMatchupValue = userFighterMatchups.get(0).getFighterMatchupValue();
//        int bestMatchupValue = 0;
        int finalBestMatchupId = userFighterMatchups.get(0).getId();

        for (Matchup aMatchup : userFighterMatchups) {
            if (aMatchup.getFighterMatchupValue() >= bestMatchupValue) {
                bestMatchupValue = aMatchup.getFighterMatchupValue();
                finalBestMatchupId = aMatchup.getId();
            }
        }

        return finalBestMatchupId;
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
