package com.intradroid.dt.intradroid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventPlanningJSON
{
    public EventPlanningJSON() {
    }

    private String event_registered;
    private String titlemodule;
    private String type_title;
    private String acti_title;
    private RoomPlanningJSON room;
    private boolean module_registered;
    private boolean past;
    private String start;
    private String end;
    private int semester;
    private boolean allow_token;
    private int scolaryear;
    private String codemodule;
    private String codeinstance;
    private String codeacti;
    private String codeevent;

    public String getTitlemodule() {
        return titlemodule;
    }

    public void setTitlemodule(String titlemodule) {
        this.titlemodule = titlemodule;
    }

    public String getType_title() {
        return type_title;
    }

    public void setType_title(String type_title) {
        this.type_title = type_title;
    }

    public String getActi_title() {
        return acti_title;
    }

    public void setActi_title(String acti_title) {
        this.acti_title = acti_title;
    }

    public boolean isModule_registered() {
        return module_registered;
    }

    public void setModule_registered(boolean module_registered) {
        this.module_registered = module_registered;
    }

    public boolean isPast() {
        return past;
    }

    public void setPast(boolean past) {
        this.past = past;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public RoomPlanningJSON getRoom() {
        return room;
    }

    public void setRoom(RoomPlanningJSON room) {
        this.room = room;
    }

    public boolean isAllow_token() {
        return allow_token;
    }

    public void setAllow_token(boolean allow_token) {
        this.allow_token = allow_token;
    }

    public String getEvent_registered() {
        return event_registered;
    }

    public void setEvent_registered(String event_registered) {
        this.event_registered = event_registered;
    }


    public int getScolaryear() {
        return scolaryear;
    }

    public void setScolaryear(int scolaryear) {
        this.scolaryear = scolaryear;
    }

    public String getCodemodule() {
        return codemodule;
    }

    public void setCodemodule(String codemodule) {
        this.codemodule = codemodule;
    }

    public String getCodeinstance() {
        return codeinstance;
    }

    public void setCodeinstance(String codeinstance) {
        this.codeinstance = codeinstance;
    }

    public String getCodeacti() {
        return codeacti;
    }

    public void setCodeacti(String codeacti) {
        this.codeacti = codeacti;
    }

    public String getCodeevent() {
        return codeevent;
    }

    public void setCodeevent(String codeevent) {
        this.codeevent = codeevent;
    }

}
