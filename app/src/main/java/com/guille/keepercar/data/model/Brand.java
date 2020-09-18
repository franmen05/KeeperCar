package com.guille.keepercar.data.model;

import com.google.gson.annotations.SerializedName;
import com.guille.keepercar.data.ws.JsonKey;

import java.io.Serializable;

/**
 * Created by Guille on 09/06/2015.
 */
public class Brand implements Serializable{

    public static final int CAR_TYPE=1;

    @SerializedName(JsonKey.BRAND_ID)
    private int  id;

    @SerializedName(JsonKey.BRAND_NAME)
    private String  name;

    @SerializedName(JsonKey.BRAND_PHOTO)
    private String urlPhoto;

    @SerializedName(JsonKey.BRAND_TYPE)
    private int  type;

    public Brand(int id) {
        this.setId(id);
    }

    public Brand() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", urlPhoto='" + urlPhoto + '\'' +
                ", type=" + type +
                '}';
    }
}

