package smash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import smash.data.FighterDao;
import smash.data.MatchupDao;
import smash.data.UserDao;
import smash.model.CurrentUser;
import smash.model.Fighter;
import smash.model.LoggedIn;
import smash.model.Matchup;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Lauren on 5/15/2017.
 */
@Controller
@RequestMapping(value = "user/matchups")
public class MatchupController {

    @Autowired
    MatchupDao matchupDao;

    @Autowired
    FighterDao fighterDao;

    @Autowired
    UserDao userDao;

    LoggedIn loggedIn = new LoggedIn();
    CurrentUser currentUser = new CurrentUser();

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String displayAddMatchupForm(Model model) {

        if (!loggedIn.isNowLoggedIn()) {
            return "redirect:/login";
        }

        model.addAttribute("title", "Add Matchup");
        model.addAttribute("matchup", new Matchup());
        model.addAttribute("fighters", userDao.findOne(currentUser.getCurrentUserId()).getFighters());
        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));

        return "matchup/add";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String processAddMatchupForm(Model model,
                                        @ModelAttribute Matchup newFighterMatchup,
                                        @RequestParam int opponentId,
                                        @RequestParam int fighterMatchupValue,
                                        @PathVariable int id) {

        if (!fighterDao.exists(opponentId)) {

            String opponentError = "Not a valid fighter!";
            model.addAttribute("opponentError", opponentError);
            model.addAttribute("title", "Add Matchup");
            model.addAttribute("matchup", new Matchup());
            model.addAttribute("fighters", userDao.findOne(currentUser.getCurrentUserId()).getFighters());
            model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));
            model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());

            return "matchup/add";
        }

        List<Matchup> allMatchups = userDao.findOne(currentUser.getCurrentUserId()).getAllMatchups();

        int opponentMatchupValue = 0;
        Fighter fighter = fighterDao.findOne(id);
        Fighter opponent = fighterDao.findOne(opponentId);

        for (Matchup matchup : allMatchups) {
            Fighter matchupFighter = matchup.getFighter();
            if (fighter.equals(matchupFighter)) {
                if (matchup.getOpponentId() == opponentId) {

                    matchupDao.delete(matchup.getId());

                    for (Matchup opponentMatchup : allMatchups) {
                        if (opponentMatchup.getFighter().getId() == opponentId) {
                            if (opponentMatchup.getOpponentId() == matchupFighter.getId()) {
                                matchupDao.delete(opponentMatchup.getId());
                            }
                        }
                    }
                }
            }
        }

        if (id == opponentId) {
            if (fighterMatchupValue != 5) {
                String dittoError = "Dittos are ALWAYS even!";
                model.addAttribute("dittoError", dittoError);
                model.addAttribute("title", "Add Matchup");
                model.addAttribute("matchup", new Matchup());
                model.addAttribute("fighters", fighterDao.findAll());
                model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
                model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));

                return "matchup/add";
            }

        }
        switch(fighterMatchupValue) {
            case 1 : opponentMatchupValue = 9;
                break;
            case 2 : opponentMatchupValue = 8;
                break;
            case 3 : opponentMatchupValue = 7;
                break;
            case 4 : opponentMatchupValue = 6;
                break;
            case 5 : opponentMatchupValue = 5;
                break;
            case 6 : opponentMatchupValue = 4;
                break;
            case 7 : opponentMatchupValue = 3;
                break;
            case 8 : opponentMatchupValue = 2;
                break;
            case 9 : opponentMatchupValue = 1;
                break;
        }

        newFighterMatchup.setFighter(fighter);
        newFighterMatchup.setOpponentId(opponentId);
        newFighterMatchup.setFighterMatchupValue(fighterMatchupValue);
        newFighterMatchup.setUser(userDao.findOne(currentUser.getCurrentUserId()));

        matchupDao.save(newFighterMatchup);

        if (id != opponentId) {
            Matchup newOpponentMatchup = new Matchup();
            newOpponentMatchup.setFighter(opponent);
            newOpponentMatchup.setFighterMatchupValue(opponentMatchupValue);
            newOpponentMatchup.setOpponentId(fighter.getId());
            newOpponentMatchup.setUser(userDao.findOne(currentUser.getCurrentUserId()));

            matchupDao.save(newOpponentMatchup);
        }

        return "redirect:../fighters";
    }

    @RequestMapping(value = "/{id}/view")
    public String displayMatchups(Model model, @PathVariable int id) {

        if (!loggedIn.isNowLoggedIn()) {
            return "redirect:/login";
        }

        Fighter thisFighter = fighterDao.findOne(id);

        Iterable<Matchup> matchups = matchupDao.findAll();
        HashMap<String, String> opponents = new HashMap<>();

        for (Matchup matchup : matchups) {
            if (matchup.getOpponentId() == thisFighter.getId()) {
                Fighter aMatchupOpponent = matchup.getFighter();
                String matchupString = "";

                switch(matchup.getFighterMatchupValue()) {
                    case 1 : matchupString = "+4";
                        break;
                    case 2 : matchupString = "+3";
                        break;
                    case 3 : matchupString = "+2";
                        break;
                    case 4 : matchupString = "+1";
                        break;
                    case 5 : matchupString = "Even";
                        break;
                    case 6 : matchupString = "-1";
                        break;
                    case 7 : matchupString = "-2";
                        break;
                    case 8 : matchupString = "-3";
                        break;
                    case 9 : matchupString = "-4";
                        break;
                }

                opponents.put(aMatchupOpponent.getPicUrl(), matchupString);
            }
        }

        model.addAttribute("opponents", opponents);
        model.addAttribute("fighter", thisFighter);
        model.addAttribute("title", "Matchups");
        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));

        return "matchup/view";
    }

}
