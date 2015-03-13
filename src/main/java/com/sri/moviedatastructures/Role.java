package com.sri.moviedatastructures;

public class Role extends Movie {

    private String roleName; //"himself" (or "herself") or the character played by the artist
    private String showType;
    private String credits;

    Role() {
        super();
    }

    Role(String showName, String showRelYear, String epiName, String epiNum, String c, String st, String r) {
        super(showName, showRelYear, epiName, epiNum);
        setRoleName(r);
        setShowType(st);
        setCredits(c);
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String rn) {
        this.roleName = rn;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String st) {
        this.showType = st;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String c) {
        this.credits = c;
    }

    public String toString() {
        return super.toString() + " Role: " + roleName + " Show Type: " + showType + " Credits: " + credits;
    }
}
