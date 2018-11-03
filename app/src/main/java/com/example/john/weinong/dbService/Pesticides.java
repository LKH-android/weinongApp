package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Pesticides extends BmobObject {
    private Integer id;
    private MyUser user_id;      /* user表使用农药的用户id*/
    private Land land_id;      /* land表农药的土地id*/
    private String  name;         /* 农药通用名称*/
    private String  number;       /* 登记证号*/
    private Integer consumption;  /* 用量（克/亩）*/
    private Integer area;         /*  使用面积*/
    private String  object;   /* 防治对象*/
    private String  remarks;      /* 备注*/


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
