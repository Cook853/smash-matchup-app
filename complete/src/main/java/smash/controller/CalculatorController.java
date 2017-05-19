package smash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import smash.data.FighterDao;
import smash.data.MatchupDao;
import smash.model.*;

import java.util.ArrayList;

/**
 * Created by Lauren on 5/15/2017.
 */
@Controller
@RequestMapping("user/calculator")
public class CalculatorController {

    @Autowired
    FighterDao fighterDao;

    @Autowired
    MatchupDao matchupDao;

    LoggedIn loggedIn = new LoggedIn();
    CurrentUser currentUser = new CurrentUser();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayChooseFighterForm(Model model) {

        if (!loggedIn.isNowLoggedIn()) {
            return "redirect:/login";
        }

        ArrayList<Fighter> userFighters = new ArrayList<>();
        model.addAttribute("fighters", fighterDao.findAll());
        model.addAttribute("title", "Matchup Help");
        model.addAttribute("userFighters", userFighters);
        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        model.addAttribute("currentUser", currentUser);

        return "calculator/index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String processChooseFighterForm(Model model,
                                           @RequestParam ArrayList<Fighter> userFighters,
                                           @RequestParam int opponentFighterId) {

        Fighter opponentFighter = fighterDao.findOne(opponentFighterId);

        Calculator calculator = new Calculator(userFighters,
                opponentFighter, matchupDao.findAll());

        Matchup bestMatchup = matchupDao.findOne(calculator.getBestMatchupId());
        Fighter bestFighter = bestMatchup.getFighter();
        String matchupValue = "";

        switch(bestMatchup.getFighterMatchupValue()) {
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

        model.addAttribute("matchupMessage", matchupMessage);
        model.addAttribute("bestFighter", bestFighter);
        model.addAttribute("bestMatchup", bestMatchup);
        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        model.addAttribute("currentUser", currentUser);

        return "calculator/results";
    }

}
