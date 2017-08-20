package com.vk.oms.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Заказчик
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders;

    public Customer() {
    }

    public Customer(String username, String password) {
        super(username, password);
    }

    public Customer(String username, String password, BigDecimal money) {
        super(username, password, money);
    }
}
