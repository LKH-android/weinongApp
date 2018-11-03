package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Filed extends BmobObject {
    private Integer id;
    private String  rice;  /*水稻*/
    private String  wheat; /*小麦*/
    private String  corn;  /*玉米*/
    private String  peanut; /*花生*/
    private String  rape;  /*油菜*/
    private String  soybean; /*大豆*/
    private String  cotton; /* 棉花*/
    private String  potato;
    private String  millet;
    private String  other;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRice() {
        return rice;
    }

    public void setRice(String rice) {
        this.rice = rice;
    }

    public String getWheat() {
        return wheat;
    }

    public void setWheat(String wheat) {
        this.wheat = wheat;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public String getPeanut() {
        return peanut;
    }

    public void setPeanut(String peanut) {
        this.peanut = peanut;
    }

    public String getRape() {
        return rape;
    }

    public void setRape(String rape) {
        this.rape = rape;
    }

    public String getSoybean() {
        return soybean;
    }

    public void setSoybean(String soybean) {
        this.soybean = soybean;
    }

    public String getCotton() {
        return cotton;
    }

    public void setCotton(String cotton) {
        this.cotton = cotton;
    }

    public String getPotato() {
        return potato;
    }

    public void setPotato(String potato) {
        this.potato = potato;
    }

    public String getMillet() {
        return millet;
    }

    public void setMillet(String millet) {
        this.millet = millet;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
