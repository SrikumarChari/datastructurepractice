package com.sri.moviedatastructures;

import com.sri.main.DataStructureTest;
import com.sri.graphdatastructures.VertexIntf;
import com.sri.graphdatastructures.VertexType;

public class Genre implements Comparable<Genre>, VertexIntf {

    private String genre;
    private String title;
    private String relYear;
    private boolean episode;
    private String episodeName;
    private String episodeNumber;
    private String showType;
    private String credits;

    public Genre(String genre, String title2, String relYear2,
            String episodeName2, String episodeNumber2,
            String showType2, String credits2) {
        setGenre(genre);
        setTitle(title);
        if (episodeName2.length() > 0) {
            episode = true;
            setEpisodeName(episodeName2);
            setEpisodeNumber(episodeNumber2);
        }
        setShowType(showType2);
        setCredits(credits);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genreName) {
        genre = genreName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelYear() {
        return relYear;
    }

    public void setRelYear(String relYear) {
        this.relYear = relYear;
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

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    @Override
    public int compareTo(Genre o) {
        //return genre.compareTo(o.genre);
        return DataStructureTest.stringCompare(getGenre(), o.getGenre());
    }

    @Override
    public String getVertexLabel() {
        return getGenre();
    }

    @Override
    public VertexType getVertexType() {
        // TODO Auto-generated method stub
        return VertexType.GENRE;
    }

}
