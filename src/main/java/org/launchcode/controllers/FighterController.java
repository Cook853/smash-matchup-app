package org.launchcode.controllers;

import org.launchcode.models.Fighter;
import org.launchcode.models.data.FighterDao;
//import org.launchcode.models.data.MatchupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by Lauren on 4/18/2017.
 */
@Controller
@RequestMapping("fighter")
public class FighterController {

    @Autowired
    FighterDao fighterDao;

//    @Autowired
//    MatchupDao matchupDao;

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
            return "fighter/add";
        }

        fighterDao.save(newFighter);
        return "redirect:";
    }
}
