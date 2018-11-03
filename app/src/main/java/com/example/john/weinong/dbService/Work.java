package com.example.john.weinong.dbService;

import java.lang.reflect.Array;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Work extends BmobObject {
    private MyUser user_id;      /* 用户id*/
    private String  work;         /* 农事活动*/
    private Land landid;
    private String detailedwork;
    private String operationtime;


    public List<BmobFile> getPhoto() {
        return photo;
    }

    public void setPhoto(List<BmobFile> photo) {
        this.photo = photo;
    }

    private List<BmobFile> photo;


    public String getOperationtime() {
        return operationtime;
    }

    public void setOperationtime(String operationtime) {
        this.operationtime = operationtime;
    }


    public String getDetailedwork() {
        return detailedwork;
    }

    public void setDetailedwork(String detailedwork) {
        this.detailedwork = detailedwork;
    }


    public Land getLandid() {
        return landid;
    }

    public void setLandid(Land landid) {
        this.landid = landid;
    }

    public MyUser getUser_id() {
        return user_id;
    }

    public void setUser_id(MyUser user_id) {
        this.user_id = user_id;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}
