package com.intradroid.dt.intradroid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bernar_w on 31/01/2015.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class RapportJson
{
    private String uid;

    private String shell;

    private String old_id_location;

    private String location;

    private String promo;

    private String credits;

    private String lastname;

    private String close;

    private String id_history;

    private String title;

    private String internal_email;

    private String login;

    private String school_title;

    private String documents;

    private Gpa[] gpa;

    private String mtime;

    private String picture_fun;

    private String firstname;

    private String referent_used;

    private String ctime;

    private String id_promo;

    private String editable;

    private String picture;

    private String userdocs;

    private String course_code;

    private String admin;

    private String old_id_promo;

    private String gid;

    private String studentyear;

    private String school_code;

    private String invited;

    private String semester;

    public String getUid ()
    {
        return uid;
    }

    public void setUid (String uid)
    {
        this.uid = uid;
    }

    public String getShell ()
    {
        return shell;
    }

    public void setShell (String shell)
    {
        this.shell = shell;
    }

    public String getOld_id_location ()
    {
        return old_id_location;
    }

    public void setOld_id_location (String old_id_location)
    {
        this.old_id_location = old_id_location;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getPromo ()
    {
        return promo;
    }

    public void setPromo (String promo)
    {
        this.promo = promo;
    }

    public String getCredits ()
    {
        return credits;
    }

    public void setCredits (String credits)
    {
        this.credits = credits;
    }

    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }

    public String getClose ()
    {
        return close;
    }

    public void setClose (String close)
    {
        this.close = close;
    }

    public String getId_history ()
    {
        return id_history;
    }

    public void setId_history (String id_history)
    {
        this.id_history = id_history;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getInternal_email ()
    {
        return internal_email;
    }

    public void setInternal_email (String internal_email)
    {
        this.internal_email = internal_email;
    }

    public String getLogin ()
    {
        return login;
    }

    public void setLogin (String login)
    {
        this.login = login;
    }

    public String getSchool_title ()
    {
        return school_title;
    }

    public void setSchool_title (String school_title)
    {
        this.school_title = school_title;
    }

    public String getDocuments ()
    {
        return documents;
    }

    public void setDocuments (String documents)
    {
        this.documents = documents;
    }

    public Gpa[] getGpa ()
    {
        return gpa;
    }

    public void setGpa (Gpa[] gpa)
    {
        this.gpa = gpa;
    }

    public String getMtime ()
    {
        return mtime;
    }

    public void setMtime (String mtime)
    {
        this.mtime = mtime;
    }

    public String getPicture_fun ()
{
    return picture_fun;
}

    public void setPicture_fun (String picture_fun)
    {
        this.picture_fun = picture_fun;
    }

    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }

    public String getReferent_used ()
    {
        return referent_used;
    }

    public void setReferent_used (String referent_used)
    {
        this.referent_used = referent_used;
    }

    public String getCtime ()
    {
        return ctime;
    }

    public void setCtime (String ctime)
    {
        this.ctime = ctime;
    }

    public String getId_promo ()
    {
        return id_promo;
    }

    public void setId_promo (String id_promo)
    {
        this.id_promo = id_promo;
    }

    public String getEditable ()
    {
        return editable;
    }

    public void setEditable (String editable)
    {
        this.editable = editable;
    }

    public String getPicture ()
    {
        return picture;
    }

    public void setPicture (String picture)
    {
        this.picture = picture;
    }

    public String getUserdocs ()
    {
        return userdocs;
    }

    public void setUserdocs (String userdocs)
    {
        this.userdocs = userdocs;
    }

    public String getCourse_code ()
    {
        return course_code;
    }

    public void setCourse_code (String course_code)
    {
        this.course_code = course_code;
    }

    public String getAdmin ()
    {
        return admin;
    }

    public void setAdmin (String admin)
    {
        this.admin = admin;
    }

    public String getOld_id_promo ()
    {
        return old_id_promo;
    }

    public void setOld_id_promo (String old_id_promo)
    {
        this.old_id_promo = old_id_promo;
    }

    public String getGid ()
    {
        return gid;
    }

    public void setGid (String gid)
    {
        this.gid = gid;
    }

    public String getStudentyear ()
    {
        return studentyear;
    }

    public void setStudentyear (String studentyear)
    {
        this.studentyear = studentyear;
    }

    public String getSchool_code ()
    {
        return school_code;
    }

    public void setSchool_code (String school_code)
    {
        this.school_code = school_code;
    }

    public String getInvited ()
    {
        return invited;
    }

    public void setInvited (String invited)
    {
        this.invited = invited;
    }

    public String getSemester ()
    {
        return semester;
    }

    public void setSemester (String semester)
    {
        this.semester = semester;
    }
}