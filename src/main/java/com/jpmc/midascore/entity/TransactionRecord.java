package com.jpmc.midascore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue()
    private long id;

    private double amount;

    private double incetive;

    @ManyToOne
    private UserRecord user;

    @ManyToOne
    private UserRecord user2;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UserRecord getUser() {
        return user;
    }

    public void setUser(UserRecord user) {
        this.user = user;
    }

    public UserRecord getUser2() {
        return user2;
    }

    public void setUser2(UserRecord user2) {
        this.user2 = user2;
    }

    public double getIncetive() {
        return incetive;
    }

    public void setIncetive(double incetive) {
        this.incetive = incetive;
    }
}
