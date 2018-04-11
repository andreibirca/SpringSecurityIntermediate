package com.springapp.mvc.datasource;

import com.springapp.mvc.model.Gender;
import com.springapp.mvc.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.springapp.mvc.model.Gender.*;

@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<User> getUserByName(String username) {
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery("select u from User u where username=:name",
                        User.class);
        query.setParameter("name", username);

        return query.getResultList().stream().findFirst();
    }

    public List<User> getListOfUsers() {
        return sessionFactory.getCurrentSession()
                .createQuery("from User")
                .list();
    }

    public List<User> getAllByGender(Gender gender) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from User where gender=:gender");
        query.setParameter("gender", gender);

        return query.getResultList();
    }

    public boolean saveUser(User user) {

        if (checkUserIfExistInDb(user.getUsername())) {
            return false;
        }

        try {
            sessionFactory.getCurrentSession()
                    .persist(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkUserIfExistInDb(String username) {

        long count = (Long) sessionFactory
                .getCurrentSession()
                .createQuery("select count(u.id)from User u where username=:username")
                .setParameter("username", username)
                .uniqueResult();
        if (count == 0) {
            return false;
        }
        return true;
    }
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