package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Eggplant extends BmobObject {
    private Integer id;
    private String  tomatoes;
    private String  pepper;
    private String  cucumber;
    private String  strawberry;
    private String  squash;
    private String  eggplant;
    private String  green;
    private String  other;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTomatoes() {
        return tomatoes;
    }

    public void setTomatoes(String tomatoes) {
        this.tomatoes = tomatoes;
    }

    public String getPepper() {
        return pepper;
    }

    public void setPepper(String pepper) {
        this.pepper = pepper;
    }

    public String getCucumber() {
        return cucumber;
    }

    public void setCucumber(String cucumber) {
        this.cucumber = cucumber;
    }

    public String getStrawberry() {
        return strawberry;
    }

    public void setStrawberry(String strawberry) {
        this.strawberry = strawberry;
    }

    public String getSquash() {
        return squash;
    }

    public void setSquash(String squash) {
        this.squash = squash;
    }

    public String getEggplant() {
        return eggplant;
    }

    public void setEggplant(String eggplant) {
        this.eggplant = eggplant;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
