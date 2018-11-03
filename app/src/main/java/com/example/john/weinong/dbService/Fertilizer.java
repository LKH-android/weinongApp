package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Fertilizer extends BmobObject {
    private Integer id;
    private MyUser user_id;      /* 使用化肥的用户id*/
    private Land land_id;      /* 使用该化肥的土地id*/
    private String  name;            /*肥料名称*/
    private Integer consumption;    /*用量*/
    private Integer area;          /*使用面积*/
    private String  remarks;      /* 备注*/


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public MyUser getUser_id() {
        return user_id;
    }

    public void setUser_id(MyUser user_id) {
        this.user_id = user_id;
    }

    public Land getLand_id() {
        return land_id;
    }

    public void setLand_id(Land land_id) {
        this.land_id = land_id;
    }
}
