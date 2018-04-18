package com.springapp.mvc.service;

import com.springapp.mvc.datasource.UserDao;
import com.springapp.mvc.model.Gender;
import com.springapp.mvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional //
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.getListOfUsers();
    }

    public List<User> getAllByGender(Gender gender) {
        return userDao.getAllByGender(gender);
    }

    public Optional<User> getUserByName(String username) {
        return userDao.getUserByName(username);
    }

    public Optional<User> getUserByEmail(String emailname) {
        return userDao.getUserByEmail(emailname);
    }

    public boolean register(User user){
        return userDao.saveUser(user);
    }

    public boolean update(User user) {
        return  userDao.updateUser(user);
    }

//    public boolean existsInDb(User user){
//        return userDao.checkUserIfExistInDb(user);
//    }

    public List<User> getAllUsersWithRoleUser() { return userDao.getListOfUsersWithRoleUser();}



    public void deleteUser(String username){
        userDao.deleteUser(username);
    }
}
