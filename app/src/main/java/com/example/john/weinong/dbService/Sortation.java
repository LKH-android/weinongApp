package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Sortation extends BmobObject {
    private Integer id;
    private String  field;  /*大田作物*/
    private String  cucurbit; /*瓜类作物*/
    private String  eggplant; /*茄果类作物*/
    private String  vegetables; /*叶菜类蔬菜*/


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCucurbit() {
        return cucurbit;
    }

    public void setCucurbit(String cucurbit) {
        this.cucurbit = cucurbit;
    }

    public String getEggplant() {
        return eggplant;
    }

    public void setEggplant(String eggplant) {
        this.eggplant = eggplant;
    }

    public String getVegetables() {
        return vegetables;
    }

    public void setVegetables(String vegetables) {
        this.vegetables = vegetables;
    }
}
