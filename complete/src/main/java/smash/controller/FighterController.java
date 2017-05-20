package smash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import smash.data.FighterDao;
import smash.data.MatchupDao;
import smash.data.UserDao;
import smash.model.*;

import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    UserDao userDao;

    LoggedIn loggedIn = new LoggedIn();
    CurrentUser currentUser = new CurrentUser();

    @RequestMapping(value = "")
    public String index(Model model) {

        if (!loggedIn.isNowLoggedIn()) {
            return "redirect:/login";
        }

        model.addAttribute("fighters", userDao.findOne(currentUser.getCurrentUserId()).getFighters());
        model.addAttribute("title", "Fighters");
        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));

        return "fighter/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddFighterForm(Model model) {

        if (!loggedIn.isNowLoggedIn()) {
            return "redirect:/login";
        }

        model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));
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
            model.addAttribute("errors", errors);
            model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));
            model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
            return "fighter/add";
        }

        List<Fighter> currentUserFighters = userDao.findOne(currentUser.getCurrentUserId()).getFighters();
        for (Fighter fighter : currentUserFighters) {
            if (newFighter.getName().equals(fighter.getName())) {
                model.addAttribute("title", "New Fighter");
                model.addAttribute("fighter", new Fighter());
                model.addAttribute("fighterExistsError", "You've already added that fighter");
                model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));
                model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
                return "fighter/add";
            }
        }

        newFighter.setUser(userDao.findOne(currentUser.getCurrentUserId()));
        fighterDao.save(newFighter);
        return "redirect:../fighters";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveFighterForm(Model model) {

        if (!loggedIn.isNowLoggedIn()) {
            return "redirect:/login";
        }

        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));
        model.addAttribute("fighters", userDao.findOne(currentUser.getCurrentUserId()).getFighters());
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

        return "redirect:../fighters";
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
        model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));

        return "fighter/edit";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
    public String processEditFighterForm(@ModelAttribute @Valid Fighter editedFighter,
                                         Errors errors, Model model,
                                         @PathVariable int id) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "New Fighter");
            model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));
            model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
            return "fighter/edit";
        }

        Fighter oldFighter = fighterDao.findOne(id);
        oldFighter.setName(editedFighter.getName());
        oldFighter.setPicUrl(editedFighter.getPicUrl());
        fighterDao.save(oldFighter);

        model.addAttribute("oldFighter", oldFighter);
        model.addAttribute("title", oldFighter.getName());
        model.addAttribute("success", "Fighter successfully updated");
        model.addAttribute("editedFighter", new Fighter());
        model.addAttribute("currentUser", userDao.findOne(currentUser.getCurrentUserId()));
        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());

        return "fighter/edit";
    }

}
