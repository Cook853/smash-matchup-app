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
                                        @ModelAttribute Matchup newMatchup,
                                        @RequestParam int fighterId,
                                        @RequestParam int fighterOneMatchup,
                                        @PathVariable int id) {

        int fighterTwoMatchup = 0;
        Fighter fighterOne = fighterDao.findOne(id);
        Fighter fighterTwo = fighterDao.findOne(fighterId);
        newMatchup.setFighterTwo(fighterTwo);
        newMatchup.setFighterOne(fighterOne);
        newMatchup.setFighterOneMatchup(fighterOneMatchup);
        if (fighterOneMatchup == 5) {
            fighterTwoMatchup = 5;
        }

        if (fighterOneMatchup == 6) {
            fighterTwoMatchup = 4;
        }

        newMatchup.setFighterTwoMatchup(fighterTwoMatchup);
        newMatchup.setFighter(fighterOne);

        matchupDao.save(newMatchup);
        return "redirect:fighter";
    }

}
