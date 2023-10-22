package com.hirlu.crudapp;

public class Game {
    private int id;
    private String name;
    private int year;
    private String description;
    private int pegiAge;
    private String image;
    public Game(String name, int year, String description, int pegiAge, String image) {
        this.name = name;
        this.year = year;
        this.description = description;
        this.pegiAge = pegiAge;
        this.image = image;
    }

    public Game(int id, String name, int year, String description, int pegiAge, String image) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.description = description;
        this.pegiAge = pegiAge;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPegiAge() {
        return pegiAge;
    }

    public void setPegiAge(int pegiAge) {
        this.pegiAge = pegiAge;
    }

    public String getImage() {
        return image;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}