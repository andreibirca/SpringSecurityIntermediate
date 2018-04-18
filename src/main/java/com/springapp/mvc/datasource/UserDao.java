package com.springapp.mvc.datasource;

import com.springapp.mvc.model.Gender;
import com.springapp.mvc.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.springapp.mvc.model.Gender.*;
import static com.springapp.mvc.model.RoleEnum.ROLE_USER;

@Repository
public class UserDao {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private PasswordEncoder encoder;
    //TODO create RolesDao and RolesService it should give default roles;
    //TODO add roles into table
    public Optional<User> getUserByName(String username) {
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery("select u from User u where username=:name",
                        User.class);
        query.setParameter("name", username);

        return query.getResultList().stream().findFirst();
    }

    public List<User> getListOfUsers() {
        return sessionFactory.getCurrentSession()
                .createQuery("Select distinct u from User u left join fetch u.roles r")
                .list();
    }

    public Optional<User> getUserByEmail(String email){
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery("from User where email = :email", User.class)
                .setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

    public List<User> getAllByGender(Gender gender) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from User where gender=:gender");
        query.setParameter("gender", gender);

        return query.getResultList();
    }

    public boolean saveUser(User user) {

        try {
            user.setPassword(encoder.encode(user.getPassword()));
            //TODO CHECK IF user.roles is empty give him default roles

            if (user.getRoles() == null || user.getRoles().isEmpty() ){
                user.setRoles(roleDao.getDefaultRoles());
            }

            sessionFactory.getCurrentSession()
                    .persist(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<User> getListOfUsersWithRoleUser() {

        return sessionFactory.getCurrentSession()
                .createQuery("select distinct u from User u LEFT JOIN FETCH u.roles r where r.name = :role")
                .setParameter("role", ROLE_USER)
                .list();

    }

    public void deleteUser(String username){
        sessionFactory.getCurrentSession()
                .delete(getUserByName(username).get());
    }

    public boolean updateUser(User user) {
        try {

            user.setPassword(encoder.encode(user.getPassword()));
            //TODO CHECK IF user.roles is empty give him default roles

            User userDB = getUserByName(user.getUsername()).get();
            user.setId(userDB.getId());
            user.setRoles(userDB.getRoles());
//            if (user.getRoles() == null || user.getRoles().isEmpty() ){
//                user.setRoles(roleDao.getDefaultRoles());
//            }

            sessionFactory.getCurrentSession()
                    .merge(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public void setDefaultUser(User user){
//        if (user.getRoles().isEmpty()){
//            roleDao.getDefaultRoles();
//        }
//    }


}

//    public boolean checkUserIfExistInDb(User user) {
//        List<User> list = getListOfUsers();
//        for (User u : list
//                ) {
//            if (u.getUsername().equals(user.getUsername())) {
//                return true;
//            }
//        }
//        return false;
//    }
//}