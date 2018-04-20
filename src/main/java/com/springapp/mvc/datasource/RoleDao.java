package com.springapp.mvc.datasource;

import com.springapp.mvc.model.Role;
import com.springapp.mvc.model.RoleEnum;
import com.springapp.mvc.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RoleDao {


    @Autowired
    private SessionFactory sessionFactory;

    public List<Role> getAllRoles(){
        return sessionFactory.getCurrentSession().createQuery("from Roles").list();
    }

//    public boolean existRole(String rolename){
//        for (Role r : getAllRoles()
//             ) {
//            if (r.getName().equals(rolename)){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void dheckIfHasRole(User user){
//        if(user.getRoles().isEmpty()){
//            user.setRoles();
//            Set<Role> setRole = new HashSet<>();
//            setRole.add()
//        }
//
//
//
//
//        String rolename = user.getUsername();
//        Long count = (Long) sessionFactory
//                .getCurrentSession()
//                .createQuery("select count(u.id)from User u where username=:username")
//                .setParameter("username", username)
//                .uniqueResult();
//        if (count == 0) {
//            return false;
//        }

        public Set<Role> getDefaultRoles(){
            Set<Role> roles = new HashSet<>();

            Query query = sessionFactory.getCurrentSession()
                    .createQuery(" from  Role r where r.name = 'ROLE_USER'");
                roles.add((Role) query.getSingleResult());

            return roles;

//            TypedQuery<Role> query = sessionFactory.getCurrentSession()
//                    .createQuery("select r from Role r where role = :role", Role.class);
//            query.setParameter("role", roles);
//            query.getResultList().stream().findFirst();
        }
//        sessionFactory.getCurrentSession().createQuery()
}


