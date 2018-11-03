package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Cucurbit extends BmobObject {
    private Integer id;
    private String  wateermelon;
    private String  muskmelon;
    private String  papaya;
    private String  melon;
    private String  luffa;
    private String  other;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWateermelon() {
        return wateermelon;
    }

    public void setWateermelon(String wateermelon) {
        this.wateermelon = wateermelon;
    }

    public String getMuskmelon() {
        return muskmelon;
    }

    public void setMuskmelon(String muskmelon) {
        this.muskmelon = muskmelon;
    }

    public String getPapaya() {
        return papaya;
    }

    public void setPapaya(String papaya) {
        this.papaya = papaya;
    }

    public String getMelon() {
        return melon;
    }

    public void setMelon(String melon) {
        this.melon = melon;
    }

    public String getLuffa() {
        return luffa;
    }

    public void setLuffa(String luffa) {
        this.luffa = luffa;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
