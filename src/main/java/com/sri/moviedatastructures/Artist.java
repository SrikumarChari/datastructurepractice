package com.sri.moviedatastructures;

import com.sri.main.DataStructureTest;
import com.sri.graphdatastructures.VertexIntf;
import com.sri.graphdatastructures.VertexType;

import java.util.Iterator;

import com.sri.datastructures.List;

public class Artist extends Object implements Comparable<Artist>, VertexIntf, Iterable<Movie> {

    private String lastName;
    private String firstName;
    private ArtistType type;
    private List<Movie> roles;

    public Artist() {
        roles = new List<Movie>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newName) {
        firstName = newName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArtistType getType() {
        return type;
    }

    public String getTypeString() {
        String t = "";
        if (type == ArtistType.ACTOR) {
            t = "actor";
        } else if (type == ArtistType.ACTRESS) {
            t = "actress";
        } else if (type == ArtistType.DIRECTOR) {
            t = "director";
        } else if (type == ArtistType.PRODUCER) {
            t = "producer";
        }
        return t;
    }

    public void setType(ArtistType type) {
        this.type = type;
    }

    public void setTypeString(String asString) {
        if (asString.compareTo("actor") == 0) {
            type = ArtistType.ACTOR;
        } else if (asString.compareTo("actress") == 0) {
            type = ArtistType.ACTRESS;
        } else if (asString.compareTo("director") == 0) {
            type = ArtistType.DIRECTOR;
        } else if (asString.compareTo("producer") == 0) {
            type = ArtistType.PRODUCER;
        }
    }

    public void addRole(Movie m) {
        roles.add(m);
    }

    public String toString() {
        String s = "";
        s = "Name: " + getFirstName() + " " + getLastName() + " Type: " + getTypeString() + "\r";

        Iterator<Movie> it = roles.iterator();
        while (it.hasNext()) {
            Movie ar = it.next();
            s += ar.toString();
            s += "\r";
        }
        s += "\r";
        return s;
    }

    @Override
    public int compareTo(Artist o) {
        return DataStructureTest.stringCompare(getFirstName(), o.getFirstName());
    }

    public boolean equals(Object anotherArtist) {
        boolean retVal = false;
        int v1 = DataStructureTest.stringCompare(getFirstName(), ((Artist) anotherArtist).getFirstName());
        int v2 = DataStructureTest.stringCompare(getLastName(), ((Artist) anotherArtist).getLastName());
        if (v1 == 0 && v2 == 0) {
            retVal = true;
        }
        return retVal;
    }

    @Override
    public String getVertexLabel() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public VertexType getVertexType() {
        return VertexType.ARTIST;
    }

    @Override
    public Iterator<Movie> iterator() {
        return roles.iterator();
    }
}
