package org.launchcode.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lauren on 5/4/2017.
 */
public class Calculator {

    private List<Fighter> userFighters;

    private Fighter opponentFighter;

    private Iterable<Matchup> allMatchups;

    private HashMap<Fighter, Integer> userFightersWithMatchups;

    public Calculator(List<Fighter> userFighters, Fighter opponentFighter,
                      Iterable<Matchup> allMatchups) {

        this.userFighters = userFighters;
        this.opponentFighter = opponentFighter;
        this.allMatchups = allMatchups;
        this.userFightersWithMatchups = new HashMap<>();

        for (Fighter aFighter : userFighters) {
            for (Matchup aMatchup : allMatchups){
                if (aMatchup.getfighter().equals(aFighter)) {
                    if (aMatchup.getOpponentId() == opponentFighter.getId()) {
                        userFightersWithMatchups.put(aFighter, aMatchup.getFighterMatchupValue());
                    }
                }
            }
        }
    }

    public Map.Entry<Fighter, Integer> getBestMatchup() {

        userFightersWithMatchups = getUserFightersWithMatchups();
        Integer bestMatchupValue = 0;

        for (Map.Entry<Fighter, Integer> matchup : userFightersWithMatchups.entrySet()) {
            if (matchup.getValue() > bestMatchupValue) {
                Fighter bestMatchupFighter = matchup.getKey();
                Integer bestMatchupEntryValue = matchup.getValue();
                Map.Entry<Fighter, Integer> bestMatchup = new Map.Entry<bestMatchupFighter, be>;
            }
        }
        if (bestMatchup != null) {
            return bestMatchup;
        }
        else {
            bestMatchup.
        }
    }

    public List<Fighter> getUserFighters() {
        return userFighters;
    }

    public Fighter getOpponentFighter() {
        return opponentFighter;
    }

    public Iterable<Matchup> getAllMatchups() {
        return allMatchups;
    }

    public HashMap<Fighter, Integer> getUserFightersWithMatchups() {
        return userFightersWithMatchups;
    }
}
