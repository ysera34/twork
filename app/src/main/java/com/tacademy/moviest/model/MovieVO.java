package com.tacademy.moviest.model;

/**
 * Created by yoon on 2016. 9. 7..
 */
public class MovieVO {

    private int id;
    private String title;
    private String director;
    private int year;
    private String synopsis;

    public MovieVO() {
    }

    public MovieVO(int id, String title, String director, int year, String synopsis) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
        this.synopsis = synopsis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

}
