package com.restapp;

import android.app.Application;

public class GlobalClass extends Application {

    private String someVariable;
    private String someVariable2;
    private String Email;

    private boolean photoflag;
    private boolean photoflag2;

    public String getSomeVariable() {
        return someVariable;
    }
    public String getSomeVariable2() {
        return someVariable2;
    }
    public String getEmail() {
        return Email;
    }
    public boolean getphotoflag() {
        return photoflag;
    }
    public boolean getphotoflag2() {
        return photoflag2;
    }


    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }
    public void setSomeVariable2(String someVariable2) {
        this.someVariable2 = someVariable2;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    public void setPhotoflag(boolean photoflag) {
        this.photoflag = photoflag;
    }
    public void setPhotoflag2(boolean photoflag2) {
        this.photoflag2 = photoflag2;
    }
}