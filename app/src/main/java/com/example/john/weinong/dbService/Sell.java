package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Sell extends BmobObject {
    private Integer id;
    private MyUser user_id;    /*用户id*/
    private String  name;       /*产品名称*/
    private Integer  number;      /*数量*/
    private Integer  batch;       /*生产批次*/
    private String  sales;       /*销售去向*/
    private String  time;        /*销售日期*/


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





    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public MyUser getUser_id() {
        return user_id;
    }

    public void setUser_id(MyUser user_id) {
        this.user_id = user_id;
    }
}
