package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Type extends BmobObject {
    private Integer id;
    private String  fertilize;       /*施肥*/
    private String  medicine;     /*打药*/
    private String  water;         /*灌溉*/
    private String  farmland;      /*耕地*/
    private String  sow;           /*播种*/
    private String  transplant;    /*移栽*/
    private String  recovery;      /*采收*/
    private String  sell;          /*销售*/
    private String  Other;         /*其它*/


    public String getFertilize() {
        return fertilize;
    }

    public void setFertilize(String fertilize) {
        this.fertilize = fertilize;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getFarmland() {
        return farmland;
    }

    public void setFarmland(String farmland) {
        this.farmland = farmland;
    }

    public String getSow() {
        return sow;
    }

    public void setSow(String sow) {
        this.sow = sow;
    }

    public String getTransplant() {
        return transplant;
    }

    public void setTransplant(String transplant) {
        this.transplant = transplant;
    }

    public String getRecovery() {
        return recovery;
    }

    public void setRecovery(String recovery) {
        this.recovery = recovery;
    }

    public String getOther() {
        return Other;
    }

    public void setOther(String other) {
        Other = other;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }
}
