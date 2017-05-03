package org.launchcode.controllers;

import org.launchcode.models.Fighter;
import org.launchcode.models.Matchup;
import org.launchcode.models.data.FighterDao;
import org.launchcode.models.data.MatchupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Lauren on 4/18/2017.
 */
@Controller
@RequestMapping(value = "matchups")
public class MatchupController {

    @Autowired
    MatchupDao matchupDao;

    @Autowired
    FighterDao fighterDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String displayAddMatchupForm(Model model) {

        model.addAttribute("title", "Add Matchup");
        model.addAttribute("matchup", new Matchup());
        model.addAttribute("fighters", fighterDao.findAll());

        return "matchup/add";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String processAddMatchupForm(Model model,
                                        @ModelAttribute Matchup newFighterMatchup,
                                        @RequestParam int opponentId,
                                        @RequestParam int fighterMatchupValue,
                                        @PathVariable int id) {

        int opponentMatchupValue = 0;
        Fighter fighter = fighterDao.findOne(id);
        Fighter opponent = fighterDao.findOne(opponentId);

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

        matchupDao.save(newFighterMatchup);

        Matchup newOpponentMatchup = new Matchup();
        newOpponentMatchup.setFighter(opponent);
        newOpponentMatchup.setFighterMatchupValue(opponentMatchupValue);
        newOpponentMatchup.setOpponentId(fighter.getId());

        matchupDao.save(newOpponentMatchup);

        return "redirect:../fighter";
    }

}
