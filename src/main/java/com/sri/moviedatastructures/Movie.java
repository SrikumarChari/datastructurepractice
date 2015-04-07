package com.sri.moviedatastructures;

import com.sri.main.DataStructureTest;
import com.sri.graphdatastructures.VertexIntf;
import com.sri.graphdatastructures.VertexType;
import static com.sri.utility.StringUtilities.stringCompare;

/*
 * Movie
 * 
 * name - name of the movie or show
 * relYear - year the movie or show was first aired
 * episode - true if this movie or show is an episode
 * episodeNumber - the episode number, blank if this Mpvie is not an episode
 * 
 */
public class Movie extends Object implements Comparable<Movie>, VertexIntf {

    private String name;
    private String relYear;
    private boolean episode;
    private String episodeName;
    private String episodeNumber;

    Movie() {
        name = relYear = episodeName = episodeNumber = "";
        episode = false;
    }

    public Movie(String n, String rYear) {
        name = n;
        relYear = rYear;
        episode = false;
    }

    Movie(String n, String rYear, String epiName, String epiNum) {
        name = n;
        relYear = rYear;
        episodeName = epiName;
        episodeNumber = epiNum;
        if (episodeName.length() > 0 || episodeNumber.length() > 0) {
            episode = true;
        } else {
            episode = false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getRelYear() {
        return relYear;
    }

    public void setRelYear(String year) {
        this.relYear = year;
    }

    public boolean isEpisode() {
        return episode;
    }

    public void setEpisode(boolean episode) {
        this.episode = episode;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String toString() {
        String s;
        if (!episode) {
            s = "Movie Name: " + name + " Release Year: " + relYear + "\r";
        } else {
            s = "Show: " + name + " Release Year: " + relYear + " Episode Name: " + episodeName + " Episode Number: " + episodeNumber + "\r";
        }
        return s;
    }

    public boolean equals(Object anotherMovie) {
        if (stringCompare(name, ((Movie) anotherMovie).name) == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Movie o) {
        return stringCompare(getName(), o.getName());
    }

    @Override
    public String getVertexLabel() {
        return getName();
    }

    @Override
    public VertexType getVertexType() {
        return VertexType.MOVIE;
    }
}
