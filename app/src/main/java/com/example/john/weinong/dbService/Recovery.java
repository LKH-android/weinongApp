package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Recovery extends BmobObject {

    private Crop  crop_name;
    private Land land_id;
    private String  createtime;
    private Integer landarea;
    private String  date;
    private Integer  harvest;
    private String  remarks;

    public Crop getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(Crop crop_name) {
        this.crop_name = crop_name;
    }

    public Land getLand_id() {
        return land_id;
    }

    public void setLand_id(Land land_id) {
        this.land_id = land_id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getLandarea() {
        return landarea;
    }

    public void setLandarea(Integer landarea) {
        this.landarea = landarea;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getHarvest() {
        return harvest;
    }

    public void setHarvest(Integer harvest) {
        this.harvest = harvest;
    }
}
