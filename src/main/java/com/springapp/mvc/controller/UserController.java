package com.springapp.mvc.controller;

import com.springapp.mvc.model.User;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.springapp.mvc.model.Gender.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String redirectLogin() {
        return "redirect:/login";
    }

    @GetMapping("/regForm" )
    public String showRegistration(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("msg", "Registration form");
        return "register";
    }

    @GetMapping("/secret" )
    public String showSecretPage(){
        return "secret";
    }

    //    public String registerUser(@ModelAttribute User user, Model model){
    @PostMapping("/registration")
    public String registerUser(
            @Valid @ModelAttribute("user") User user
            , BindingResult bindingResult
            , Model model) {


        boolean test = true;

        if (!user.getPassword().equals(user.getPasswConfirm())) {
            model.addAttribute("pswnotequal", "Passowrds do not match");
            test = false;
        }
        if (userService.existsInDb(user)) {
            model.addAttribute("msgExist", "An user with such name already exists !");
            test = false;
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (test == false) {
            return "register";
        }

        boolean saved = userService.register(user);
        if (saved) {
            return "redirect:/allusers";
        }
        else{
        } return "redirect:/allusers";
    }



//        else{
//
//            model.addAttribute("msgExist", "An user with such name already exists !");
//            return "register";
//        }




    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String printWelcome(Model model) {
        model.addAttribute("message", "Hi there! Log in, please");
        return "index";
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String printWelcomeC(Model model) {
//        model.addAttribute("message", "Hi there! Log in, please");
//        return "index";
//    }

    @RequestMapping(value = "/error")
    public String returnError(){
        return "error";
    }

    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "welcome";
    }

    @RequestMapping(value = "/showMales", method = RequestMethod.GET)
    public String showOnlyMales(Model model) {
        model.addAttribute("gender", "boys");
        model.addAttribute("list", userService.getAllByGender(MALE));
        return "gender";
    }

    @RequestMapping(value = "/showFemales", method = RequestMethod.GET)
    public String showOnlyFemales(Model model) {
        model.addAttribute("gender", "girls");
        model.addAttribute("list", userService.getAllByGender(FEMALE));
        return "gender";
    }

}