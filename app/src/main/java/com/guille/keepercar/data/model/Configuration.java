package com.guille.keepercar.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Guille on 03/10/2015.
 */
public class Configuration implements Serializable{

    public static final Integer UNIT_KEY=2;

    @SerializedName("id")
    private int  id;

    @SerializedName("value")
    private String  value;

    public Configuration() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}

