package com.app.jwtauthentication.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static java.util.UUID.randomUUID;

@MappedSuperclass

public abstract class Identity {

    @Id
    @Column(updatable = false, nullable = false)
    String id = randomUUID().toString();

    public String getId() {
        return id;
    }
}
