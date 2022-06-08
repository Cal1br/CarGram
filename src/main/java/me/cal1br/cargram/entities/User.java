package me.cal1br.cargram.entities;

import me.cal1br.cargram.models.UserDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
    @Column(unique = true, nullable = false, updatable = false, length = 25, name = "username")
    private String username;
    @Column(nullable = false, name = "password")
    private String password;
    @Column(nullable = true, updatable = true, length = 2000, name = "biography")
    private String biography;
    @Column(nullable = false, name = "is_online")
    private boolean isOnline;
    @Column(nullable = false, name = "last_online")
    private Timestamp lastOnline;
    @Column(nullable = false, updatable = false, name = "account_creation")
    private Date accountCreation;
    @Column(name = "profile_pic")
    private String profilePic;

    public User() {

    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(final String profilePic) {
        this.profilePic = profilePic;
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

    public UserDto toDto() {
        return new UserDto(userId, username, biography, isOnline, lastOnline, profilePic, accountCreation);
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

    public void updateLastOnline() {

    }
}
