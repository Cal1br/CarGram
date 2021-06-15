package me.cal1br.cargram.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(unique = true, nullable = false,updatable = false,length = 25)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = true,updatable = true)
    private String biography;
    @Column(nullable = false)
    private boolean isOnline;
    @Column(nullable = false)
    private Timestamp lastOnline;
    @Column(nullable = false,updatable = false)
    private Date accountCreation;

    public User(){

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", biography='" + biography + '\'' +
                ", isOnline=" + isOnline +
                ", lastOnline=" + lastOnline +
                ", accountCreation=" + accountCreation +
                '}';
    }
}
