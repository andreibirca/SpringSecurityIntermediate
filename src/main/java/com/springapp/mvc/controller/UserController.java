package com.springapp.mvc.controller;

import com.springapp.mvc.model.User;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

import static com.springapp.mvc.model.Gender.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/")
    public String redirectLogin() {
        return "redirect:/login";
    }

    @GetMapping("/registration" )
    public String showRegistration(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("msg", "Registration form");
        return "registration";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String printWelcome(Model model) {
        model.addAttribute("message", "Hi there! Log in, please");
        return "login";
    }

    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("activeUser", SecurityContextHolder.getContext().getAuthentication().getName());
        return "welcome";
    }

    @RequestMapping(value = "/failedAccess")
    public String returnFailureAccess(){
        return "failAccess";
    }

    @GetMapping("/secret" )
    public String showSecretPage(){
        return "secret";
    }

    //    public String registerUser(@ModelAttribute User user, Model model){

    @GetMapping("/updateCurrentUser")
    public String registerUpdatedUser(Model model, Authentication authentication){

        User user = userService.getUserByName(authentication.getName()).get();
        model.addAttribute("user",user);

        return "updateUser";
    }

    @PostMapping("/updateCurrentUser")
    public String updateUser(Model model,@ModelAttribute User user, BindingResult bindingResult, Authentication authentication){
        boolean success = true;

        if (bindingResult.hasErrors()){
            success = false;
        }

        if (!user.getUsername().equals(authentication.getName())){
            success = false;
            model.addAttribute("userchanged", "The user has been changed");
        }

        //todo verify if wquals  username form user and username from authentification
        //todo create a service to update the user

        if (!user.getPassword().equals(user.getPasswConfirm())) {
            model.addAttribute("pswnotequal", "Passowrds do not match");
            success = false;
        }
        User userauth = userService.getUserByName(authentication.getName()).get();
        boolean emailChanged=!userauth.getEmail().equals(user.getEmail());
        if (emailChanged && userService.getUserByEmail(user.getEmail()).isPresent()){
            model.addAttribute("emailpresent", "Such email is already registered in DB");
            success = false;
        }

        if (success){
            userService.update(user);
            return "redirect:/allusers";
        }

        return "updateUser";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") User user
            , BindingResult bindingResult
            , Model model) {

        boolean test = true;

        if (bindingResult.hasErrors()) {
            test = false;
        }

        if (!user.getPassword().equals(user.getPasswConfirm())) {
            model.addAttribute("pswnotequal", "Passowrds do not match");
            test = false;
        }

        if (userService.getUserByName(user.getUsername()).isPresent()){
            model.addAttribute("userpresent", "User with such an username already exists");
            test = false;
        }

        if (userService.getUserByEmail(user.getEmail()).isPresent()){
               model.addAttribute("emailpresent", "Such email is already registered in DB");
            test = false;
        }

        if (!test) {
            return "registration";
        }

        boolean saved = userService.register(user);
        if (saved) {
            return "redirect:/allusers";
        }
        else{
        } return "registration";
    }

    @RequestMapping(value = "/error")
    public String returnError(){
        return "error";
    }

    @RequestMapping(value = "/showAllUsers", method = RequestMethod.GET)
    public String returnAllInEditForm(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "showall";
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