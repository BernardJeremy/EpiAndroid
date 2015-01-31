package com.intradroid.dt.intradroid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by bernar_w on 24/01/2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class UserJson {
    private String title;

    public UserJson() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
