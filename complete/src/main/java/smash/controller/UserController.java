package smash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import smash.data.UserDao;
import smash.model.CurrentUser;
import smash.model.LoggedIn;
import smash.model.User;

/**
 * Created by Lauren on 5/11/2017.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserDao userDao;

    LoggedIn loggedIn = new LoggedIn();
    CurrentUser currentUser = new CurrentUser();

    @RequestMapping("/{id}")
    public String displayUserPage(@PathVariable int id, Model model) {

        if (!loggedIn.isNowLoggedIn()) {
            return "redirect:/login";
        }

        if (userDao.exists(id)) {
            model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("user", userDao.findOne(id));
            model.addAttribute("title", "Your Account");
            return "user/view";
        }

        else {
            model.addAttribute("userError", "User does not exist");
            return "redirect:/login";
        }
    }
}
