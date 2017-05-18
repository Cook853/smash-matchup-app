package smash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import smash.data.UsersDao;
import smash.model.Role;
import smash.model.Users;

/**
 * Created by Lauren on 5/17/2017.
 */
@Controller
public class LoginController {

    @Autowired
    UsersDao userDao;

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex() {
        return "user/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String displayUserCreatePage(Model model) {
        Users users = new Users();
        model.addAttribute("users", users);
        model.addAttribute("title", "Sign Up");
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String postUserCreateForm(Model model,
                                     @ModelAttribute Users users, Errors errors,
                                     @RequestParam String unHashedPassword,
                                     @RequestParam String username) {
        if (errors.hasErrors()) {
            return "signup";
        }

        if (userDao.exists(users.getId())) {
            String userExistsError = "That users already exists";
            model.addAttribute("userExistsError", userExistsError);
            return "signup";
        }

        if (unHashedPassword.isEmpty()) {
            String passwordError = "Must enter password";
            model.addAttribute("passwordError", passwordError);
            return "signup";
        } else {
            users.setUsername(username);
            users.setPassword(unHashedPassword);
            users.setRole(Role.USER);
            userDao.save(users);
            return "redirect:/" + users.getId();
        }
    }
}
