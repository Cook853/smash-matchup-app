package org.launchcode.controllers;

import org.launchcode.models.Calculator;
import org.launchcode.models.Fighter;
import org.launchcode.models.data.FighterDao;
import org.launchcode.models.data.MatchupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Lauren on 4/18/2017.
 */
@Controller
@RequestMapping("calculator")
public class CalculatorController {

    @Autowired
    FighterDao fighterDao;

    @Autowired
    MatchupDao matchupDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayChooseFighterForm(Model model) {

        model.addAttribute("fighters", fighterDao.findAll());
        model.addAttribute("title", "Matchup Help");

        return "calculator/index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processChooseFighterForm(Model model,
                                           @RequestParam Fighter aFighter,
                                           @RequestParam Fighter opponentFighter) {

        ArrayList<Fighter> userFighters = new ArrayList<>();
        userFighters.add(aFighter);

        Calculator calculator = new Calculator(userFighters,
                opponentFighter, matchupDao.findAll());

        Map.Entry<Fighter, Integer> bestMatchup = calculator.getBestMatchup();
        Fighter bestFighter = bestMatchup.getKey();
        String matchupValue = "";

        switch(bestMatchup.getValue()) {
            case 1:
                matchupValue = " loses -4";
                break;
            case 2:
                matchupValue = " loses -3";
                break;
            case 3:
                matchupValue = " loses -2";
                break;
            case 4:
                matchupValue = " loses -1";
                break;
            case 5:
                matchupValue = " has an even matchup";
                break;
            case 6:
                matchupValue = " wins +1";
                break;
            case 7:
                matchupValue = " wins +2";
                break;
            case 8:
                matchupValue = " wins +3";
                break;
            case 9:
                matchupValue = " wins +4";
                break;
        }

        String matchupMessage = bestFighter.getName() + matchupValue;

        model.addAttribute(matchupMessage);
        model.addAttribute(bestFighter);

        return "calculator/results";
    }

}
