package com.vk.oms.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Исполнитель
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Performer extends User {

    @OneToMany(mappedBy = "performer")
    private Set<Order> orders;

    public Performer() {
    }

    public Performer(String username, String password) {
        super(username, password);
    }
}
