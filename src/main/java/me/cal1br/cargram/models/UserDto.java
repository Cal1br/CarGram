package me.cal1br.cargram.models;

import java.sql.Date;

public class UserDto {
    private final long userId;
    private final String username;
    private final String biography;
    private final String profilePic;
    private final Date accountCreation;

    public UserDto(final long userId, final String username,
                   final String biography, final String profilePic, final Date accountCreation) {
        this.userId = userId;
        this.username = username;
        this.biography = biography;
        this.profilePic = profilePic;
        this.accountCreation = accountCreation;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }


    public String getBiography() {
        return biography;
    }


    public Date getAccountCreation() {
        return accountCreation;
    }

    public String getProfilePic() {
        return profilePic;
    }
}
