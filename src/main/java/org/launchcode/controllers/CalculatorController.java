//package org.launchcode.controllers;
//
//import org.launchcode.models.data.FighterDao;
//import org.launchcode.models.data.MatchupDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.validation.Valid;
//
///**
// * Created by Lauren on 4/18/2017.
// */
//@Controller
//@RequestMapping("calculator")
//public class CalculatorController {
//
//    @Autowired
//    FighterDao fighterDao;
//
//    @Autowired
//    MatchupDao matchupDao;
//
//    @RequestMapping(value = "choose", method = RequestMethod.GET)
//    public String displayChooseFighterForm(Model model) {
//        model.addAttribute("fighters", fighterDao.findAll());
//        model.addAttribute("title", "Choose Your Characters");
//
//        return "smash/index";
//    }
//
//    @RequestMapping(value = "choose", method = RequestMethod.POST)
//    public String processChooseFighterForm(Model model) {
//
//
//        return "smash/results";
//    }
//
//}
