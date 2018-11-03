package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Person extends BmobObject {

    private String name;
    private String address;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{"+
                "name='"+ name +'\''+
                ",address'" + address + '\'' +
                '}';
    }
}
