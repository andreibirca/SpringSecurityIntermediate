package com.springapp.mvc.model;

import javax.persistence.*;


@Entity
public class Role {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public int id;


    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    public RoleEnum name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleEnum getName() {
        return name;
    }

    public void setName(RoleEnum name) {
        this.name = name;
    }
}
