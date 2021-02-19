package com.app.jwtauthentication.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;

@Entity
public class Role extends Identity {

    @Enumerated(STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;

    private Role() {

    }

    public RoleName getName() {
        return name;
    }
}