package com.intradroid.dt.intradroid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class InfoJSON {


    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class infos {
        private String promo;

        private String title;

        private String login;

        private String school_title;

        private String course_code;

        private String email;

        private String semester;

        public infos() {
        }

        public String getSemester() {
            return semester;
        }

        public void setSemester(String semester) {
            this.semester = semester;
        }

        public String getPromo() {
            return promo;
        }

        public void setPromo(String promo) {
            this.promo = promo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getSchool_title() {
            return school_title;
        }

        public void setSchool_title(String school_title) {
            this.school_title = school_title;
        }

        public String getCourse_code() {
            return course_code;
        }

        public void setCourse_code(String course_code) {
            this.course_code = course_code;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public InfoJSON() {
    }

    private String ip;

    private infos infos;

    private current current;

    private history history[];

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public InfoJSON.infos getInfos() {
        return infos;
    }

    public void setInfos(InfoJSON.infos infos) {
        this.infos = infos;
    }

    public current getCurrent() {
        return current;
    }

    public void setCurrent(current current) {
        this.current = current;
    }

    public history[] getHistory() {
        return history;
    }

    public void setHistory(history[] history) {
        this.history = history;
    }
}