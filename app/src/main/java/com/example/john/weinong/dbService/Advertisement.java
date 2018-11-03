package com.example.john.weinong.dbService;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by john on 2018/10/28.
 */

public class Advertisement extends BmobObject {
    private BmobFile image;
    private String url;
    private String queren;

    public String getQueren() {
        return queren;
    }

    public void setQueren(String queren) {
        this.queren = queren;
    }


    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
