package com.springapp.mvc.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//@Entity
//@NoArgsConstructor
//@Data
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private int id;
//    @NaturalId
//    private String name;
//}

public enum RoleEnum{
    ROLE_USER,
    ROLE_ADMIN
}