package smash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import smash.data.FighterDao;
import smash.data.MatchupDao;
import smash.model.Fighter;
import smash.model.Matchup;

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

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("fighters", fighterDao.findAll());
        model.addAttribute("title", "Fighters");

        return "fighter/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddFighterForm(Model model) {
        model.addAttribute("title", "New Fighter");
        model.addAttribute("fighter", new Fighter());
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

        Fighter oldFighter = fighterDao.findOne(id);

        model.addAttribute("oldFighter", oldFighter);
        model.addAttribute("editedFighter", new Fighter());
        model.addAttribute("title", oldFighter.getName());

        return "fighter/edit";
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.POST)
    public String processEditFighterForm(@ModelAttribute @Valid Fighter editedFighter,
                                         Errors errors, Model model,
                                         @PathVariable int id) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "New Fighter");
            return "fighter/add";
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
