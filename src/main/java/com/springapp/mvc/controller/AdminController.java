package com.springapp.mvc.controller;

import com.springapp.mvc.model.User;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/panel", method = RequestMethod.GET)
    public String  showUsersByRoles(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "adminPanel";
    }

    @RequestMapping(value = "/admin/updateUser", method = RequestMethod.GET)
    public String updateUser(@RequestParam("username") String username, Model model){
        Optional<User> user = userService.getUserByName(username);
        if (user.isPresent()){
            model.addAttribute("user", user.get());
        }
        return "updateAdminUser";
    }
    @PostMapping(value = "/admin/updateUser")
    public String updtaeAdminUser(@ModelAttribute("user") User user
            , BindingResult bindingResult, Model model){
        boolean success = true;

        if (bindingResult.hasErrors()){
            success = false;
        }

        if (!user.getUsername().equals(user.getName())){
            success = false;
            model.addAttribute("userchanged", "The username has been changed");
        }

        //todo verify if wquals  username form user and username from authentification
        //todo create a service to update the user

        if (!user.getPassword().equals(user.getPasswConfirm())) {
            model.addAttribute("pswnotequal", "Passowrds do not match");
            success = false;
        }
        User userauth = userService.getUserByName(user.getUsername()).get();
        boolean emailChanged = !userauth.getEmail().equals(user.getEmail());
        if (emailChanged && userService.getUserByEmail(user.getEmail()).isPresent()){
            model.addAttribute("emailpresent", "Such email is already registered in DB");
            success = false;
        }

        if (success){
            userService.update(user);
            return "redirect:/admin/panel";
        }

        return "updateAdminUser";

    }

    @GetMapping(value = "/admin/deleteUser")
    public String deleteUserByUsername(@RequestParam("username")String username){
        userService.deleteUser(username);
        return "redirect:/admin/panel";
    }



}
