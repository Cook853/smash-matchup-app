package smash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import smash.data.FighterDao;
import smash.data.MatchupDao;
import smash.model.*;

import javax.validation.Valid;

/**
 * Created by Lauren on 5/15/2017.
 */
@Controller
@RequestMapping("user/fighters")
public class FighterController {

    @Autowired
    FighterDao fighterDao;

    @Autowired
    MatchupDao matchupDao;

    LoggedIn loggedIn = new LoggedIn();
    CurrentUser currentUser = new CurrentUser();

    @RequestMapping(value = "")
    public String index(Model model) {

        if (!loggedIn.isNowLoggedIn()) {
            return "redirect:/login";
        }

        model.addAttribute("fighters", fighterDao.findAll());
        model.addAttribute("title", "Fighters");
        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        model.addAttribute("currentUser", currentUser);

        return "fighter/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddFighterForm(Model model) {

        if (!loggedIn.isNowLoggedIn()) {
            return "redirect:/login";
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("title", "New Fighter");
        model.addAttribute("fighter", new Fighter());
        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        return "fighter/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddFighterForm(@ModelAttribute @Valid Fighter newFighter,
                                        Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "New Fighter");
            model.addAttribute("fighter", new Fighter());
            return "fighter/add";
        }

        fighterDao.save(newFighter);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveFighterForm(Model model) {

        if (!loggedIn.isNowLoggedIn()) {
            return "redirect:/login";
        }

        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("fighters", fighterDao.findAll());
        model.addAttribute("title", "Delete Fighter?");

        return "fighter/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveFighterForm(@RequestParam int[] fighterIds) {

        for (int fighterId : fighterIds) {
            for (Matchup matchup : matchupDao.findAll()) {
                if (fighterId == matchup.getFighter().getId() || fighterId == matchup.getOpponentId()) {
                    matchupDao.delete(matchup.getId());
                }
            }
            fighterDao.delete(fighterId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String displayEditFighterForm(@PathVariable int id, Model model) {

        if (!loggedIn.isNowLoggedIn()) {
            return "redirect:/login";
        }

        Fighter oldFighter = fighterDao.findOne(id);

        model.addAttribute("oldFighter", oldFighter);
        model.addAttribute("editedFighter", new Fighter());
        model.addAttribute("title", oldFighter.getName());
        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        model.addAttribute("currentUser", currentUser);

        return "fighter/edit";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
    public String processEditFighterForm(@ModelAttribute @Valid Fighter editedFighter,
                                         Errors errors, Model model,
                                         @PathVariable int id) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "New Fighter");
            return "fighter/edit";
        }

        Fighter oldFighter = fighterDao.findOne(id);
        oldFighter.setName(editedFighter.getName());
        oldFighter.setPicUrl(editedFighter.getPicUrl());
        fighterDao.save(oldFighter);

        model.addAttribute("success", "Fighter successfully updated");
        model.addAttribute("editedFighter", new Fighter());

        return "redirect:user/fighters/edit/{id}";
    }

}
