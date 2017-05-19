package smash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import smash.data.UserDao;
import smash.model.CurrentUser;
import smash.model.LoggedIn;
import smash.model.Role;
import smash.model.User;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Lauren on 5/17/2017.
 */
@Controller
public class LoginController {

    @Autowired
    UserDao userDao;

    LoggedIn loggedIn = new LoggedIn();
    CurrentUser currentUser = new CurrentUser();

    @RequestMapping("/")
    public String root(Model model) {

        if (loggedIn.isNowLoggedIn()) {
            return "redirect:user/fighters";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {

        if (loggedIn.isNowLoggedIn()) {
            model.addAttribute("loggedInError", "You're already logged in!");
            return "redirect:user/fighters";
        }

        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        model.addAttribute("title", "Login");
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLogin(Model model, @RequestParam(name = "username", required = false) String username,
                               @RequestParam(name = "password", required = false) String password) {
        Iterable<User> users = userDao.findAll();

        if (username == null || password == null) {
            return "redirect:/login-error";
        }

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    loggedIn.setNowLoggedIn(true);
                    currentUser.setCurrentUserId(user.getId());
                    model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
                    model.addAttribute("currentUser", currentUser);
                    return "redirect:user/fighters";
                }
            }
        }
        String userNotFound = "Account not found";
        model.addAttribute("userNotFound", userNotFound);
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String displayUserCreatePage(Model model) {

        if (loggedIn.isNowLoggedIn()) {
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
            model.addAttribute("mustLogOutError", "You must log out before making a new account");
            return "user/fighters";
        }

        User user = new User();
        model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
        model.addAttribute("user", user);
        model.addAttribute("title", "Sign Up");
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String postUserCreateForm(Model model,
                                     @ModelAttribute User user, Errors errors,
                                     @RequestParam String unHashedPassword,
                                     @RequestParam String username) {
        if (errors.hasErrors()) {
            return "signup";
        }

        if (userDao.exists(user.getId())) {
            String userExistsError = "That user already exists";
            model.addAttribute("userExistsError", userExistsError);
            return "signup";
        }

        if (unHashedPassword.isEmpty()) {
            String passwordError = "Must enter password";
            model.addAttribute("passwordError", passwordError);
            return "signup";
        } else {
            user.setUsername(username);
            user.setPassword(unHashedPassword);
            user.setRole(Role.USER);
            userDao.save(user);

            loggedIn.setNowLoggedIn(true);
            currentUser.setCurrentUserId(user.getId());
            model.addAttribute("loggedIn", loggedIn.isNowLoggedIn());
            model.addAttribute("currentUser", currentUser);
            return "redirect:/user/" + user.getId();
        }
    }

    @RequestMapping(value = "/logout")
    public String postLogoutForm(Model model) {
        currentUser.eraseCurrentUserId();
        loggedIn.setNowLoggedIn(false);
        model.addAttribute("logoutMessage", "You have been successfully logged out");
        return "redirect:/login";
    }
}
