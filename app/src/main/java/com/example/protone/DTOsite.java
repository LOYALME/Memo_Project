package com.example.protone;

public class DTOsite {

    String sitename;
    String id;
    String pw;

    public DTOsite() { }

    public DTOsite(String sitename, String id, String pw) {
        this.sitename = sitename;
        this.id = id;
        this.pw = pw;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
