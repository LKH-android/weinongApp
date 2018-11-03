package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;

public class Crop extends BmobObject {
    private Integer id;
    private Land land_id;  /*土地id*/
    private MyUser user_id;   /*用户id*/
    private String  name;      /*作物名称*/

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


    public Land getLand_id() {
        return land_id;
    }

    public void setLand_id(Land land_id) {
        this.land_id = land_id;
    }

    public MyUser getUser_id() {
        return user_id;
    }

    public void setUser_id(MyUser user_id) {
        this.user_id = user_id;
    }
}
