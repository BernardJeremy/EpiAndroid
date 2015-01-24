package com.intradroid.dt.intradroid;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by bernar_w on 24/01/2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class history {

    private String title;

    private UserJson user;

    public history() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserJson getUser() {
        return user;
    }

    public void setUser(UserJson user) {
        this.user = user;
    }
}
