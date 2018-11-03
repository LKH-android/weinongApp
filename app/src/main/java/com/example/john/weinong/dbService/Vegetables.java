package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Vegetables extends BmobObject {
    private Integer id;
    private String  cauliflower;
    private String  greens;
    private String  celery;
    private String  spinach;
    private String  amaranth;
    private String  waterspinach;
    private String  orianderc;
    private String  other;

    public Vegetables() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCauliflower() {
        return cauliflower;
    }

    public void setCauliflower(String cauliflower) {
        this.cauliflower = cauliflower;
    }

    public String getGreens() {
        return greens;
    }

    public void setGreens(String greens) {
        this.greens = greens;
    }

    public String getCelery() {
        return celery;
    }

    public void setCelery(String celery) {
        this.celery = celery;
    }

    public String getSpinach() {
        return spinach;
    }

    public void setSpinach(String spinach) {
        this.spinach = spinach;
    }

    public String getAmaranth() {
        return amaranth;
    }

    public void setAmaranth(String amaranth) {
        this.amaranth = amaranth;
    }

    public String getOrianderc() {
        return orianderc;
    }

    public void setOrianderc(String orianderc) {
        this.orianderc = orianderc;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getWaterspinach() {
        return waterspinach;
    }

    public void setWaterspinach(String waterspinach) {
        this.waterspinach = waterspinach;
    }
}
