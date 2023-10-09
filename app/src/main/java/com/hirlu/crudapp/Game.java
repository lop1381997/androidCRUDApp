package com.hirlu.crudapp;

public class Game {
    int id;
    String name;
    int year;
    String description;
    int pegiAge;
    int image;


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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Game(String name, int year, String description, int pegiAge, int image) {
        this.name = name;
        this.year = year;
        this.description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        this.pegiAge = pegiAge;
        this.image = image;
    }
}