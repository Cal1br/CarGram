package me.cal1br.cargram.models;

import java.sql.Date;
import java.sql.Timestamp;

public class UserDto {
    private final long userId;
    private final String username;
    private final String biography;

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getBiography() {
        return biography;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public Timestamp getLastOnline() {
        return lastOnline;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public Date getAccountCreation() {
        return accountCreation;
    }

    private final boolean isOnline;
    private final Timestamp lastOnline;
    private final String profilePic;
    private final Date accountCreation;

    public UserDto(final long userId, final String username,
                   final String biography, final boolean isOnline, final Timestamp lastOnline, final String profilePic, final Date accountCreation) {
        this.userId = userId;
        this.username = username;
        this.biography = biography;
        this.isOnline = isOnline;
        this.lastOnline = lastOnline;
        this.profilePic = profilePic;
        this.accountCreation = accountCreation;
    }

}
