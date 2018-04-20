package com.springapp.mvc.controller;

import com.springapp.mvc.model.Gender;
import com.springapp.mvc.model.RoleEnum;
import com.springapp.mvc.model.User;
import com.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/rest/allusers", method = RequestMethod.GET)
    public List<User> getAllUsers(Model model) {
       return userService.getAllUsers();
    }

    @GetMapping("rest/getUserById/{id}")
    public User getUserById(@PathVariable int id){
        return userService.getUserById(id).get();
        }

    @GetMapping("rest/getUserByUsername/{username}")
    public User getUserByUserName(@PathVariable String username){
        return userService.getUserByName(username).get();
    }

    @GetMapping("rest/getUserByGender/{gender}")
    public List<User> getUserByGender(@PathVariable Gender gender){
        return userService.getAllByGender(gender);
    }

    @GetMapping("rest/getUserByRole/{role}")
    public List<User> getUserByGender(@PathVariable String role){
        return userService.getUserByRole(role);
    }

    @GetMapping("rest/getUserByAge/{age}")
    public List<User> getUserByAge(@PathVariable String age){
        return userService.getUserByAge(age);
    }

    @PostMapping("rest/createUser")
    public boolean registerUser(@Valid @RequestBody User user
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
            return false;
        }

        boolean saved = userService.register(user);
        if (saved) {
            return true;
        }
        else{
        } return false;
    }

    @PostMapping("rest/createRole/{roleName}")
    public boolean createRole(@PathVariable RoleEnum roleEnum){
        return userService.saveRole(roleEnum);
    }

    @GetMapping(value = "/rest/deleteUser/{username}")
    public boolean deleteUserByUsername(@PathVariable String username){
       if(userService.deleteUser(username)){
           return true;
       }
        return false;
    }

    @GetMapping(value = "/rest/deleteRole/{id}")
    public boolean deleteRoleById(@PathVariable int id){
        if(userService.deleteRole(id)){
            return true;
        }
        return false;
    }

}
