package com.springapp.mvc.controller;

import com.springapp.mvc.model.User;
import com.springapp.mvc.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
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

    @PostMapping("/updateSimpleUser")
    public String registerUpdatedUser(@Valid @ModelAttribute("username") String username
                                        ,BindingResult bindingResult
                                        , Model model){
        User user = null;
        Optional<User> user1 = userService.getUserByName(username);
        if (user1.isPresent()){
            model.addAttribute("activeUser", user1.get());
            user = user1.get();
        }

//        boolean test = true;
//
//        if (bindingResult.hasErrors()) {
//            test = false;
//        }
//
//        if (!user.getPassword().equals(user.getPasswConfirm())) {
//            model.addAttribute("pswnotequal", "Passowrds do not match");
//            test = false;
//        }
//        if (userService.getUserByEmail(user.getEmail()).isPresent()){
//            model.addAttribute("emailpresent", "Such email is already registered in DB");
//            test = false;
//        }
//        if (!test) {
//            return "register";
//        }

        boolean saved = userService.(user);
        if (saved) {
            return "redirect:/allusers";
        }
        else{
        } return "register";
    }

    @PostMapping("/registration")
    public String registerUser(
            @Valid @ModelAttribute("user") User user
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

//        if (userService.existsInDb(user)) {
//            model.addAttribute("msgExist", "An user with such name already exists !");
//            test = false;
//        }

        if (!test) {
            return "register";
        }

        boolean saved = userService.register(user);
        if (saved) {
            return "redirect:/allusers";
        }
        else{
        } return "register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String printWelcome(Model model) {
        model.addAttribute("message", "Hi there! Log in, please");
        return "index";
    }

    @RequestMapping(value = "/error")
    public String returnError(){
        return "error";
    }

    @RequestMapping(value = "/failedAccess")
    public String returnFailureAccess(){
        return "failAccess";
    }

    @RequestMapping(value = "/showAllUsers", method = RequestMethod.GET)
    public String returnAllInEditForm(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "showall";
    }

    @RequestMapping(value = "/allusers", method = RequestMethod.GET)
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("activeUser", SecurityContextHolder.getContext().getAuthentication().getName());
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

    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public String updateUser(@RequestParam("username") String username, Model model){
        Optional<User> user = userService.getUserByName(username);
        if (user.isPresent()){
            model.addAttribute("user", user.get());
        }
        return "editPage";
    }

    @GetMapping(value = "/deleteUser")
    public String deleteUserByUsername(@RequestParam("username")String  username){
        userService.deleteUser(username);
        return "redirect:/showAllUsers";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String  showUsersByRoles(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "adminPanel";
    }


}