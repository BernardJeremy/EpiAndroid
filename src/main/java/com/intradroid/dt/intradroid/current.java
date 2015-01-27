package com.intradroid.dt.intradroid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by bernar_w on 24/01/2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class current {
    public current(){

    }

    private String active_log;

    private String nslog_min;

    private String achieved;

    public String getAchieved() {
        return achieved;
    }

    public void setAchieved(String achieved) {
        this.achieved = achieved;
    }

    public String getActive_log() {
        return active_log;
    }

    public void setActive_log(String active_log) {
        this.active_log = active_log;
    }

    public String getNslog_min() {
        return nslog_min;
    }

    public void setNslog_min(String nslog_min) {
        this.nslog_min = nslog_min;
    }
}
