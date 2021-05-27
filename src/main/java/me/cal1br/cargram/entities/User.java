package me.cal1br.cargram.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID userId; //todo maybe replace with long?
    @Column(unique = true, nullable = false,updatable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = true,updatable = true)
    private String biography;
    @Column(nullable = false)
    private boolean isOnline;
    @Column(nullable = false)
    private Timestamp lastOnline;
    @Column(nullable = false,updatable = false)
    private Date accountCreation;

    public User(){

    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(final String biography) {
        this.biography = biography;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(final boolean online) {
        isOnline = online;
    }

    public Timestamp getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(final Timestamp lastOnline) {
        this.lastOnline = lastOnline;
    }

    public Date getAccountCreation() {
        return accountCreation;
    }

    public void setAccountCreation(final Date accountCreation) {
        this.accountCreation = accountCreation;
    }
}
