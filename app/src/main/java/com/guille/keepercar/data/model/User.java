package com.guille.keepercar.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Guille on 09/06/2015.
 */
public class User implements Cloneable,Serializable {

    @SerializedName("userId")
    private String id;
    private String name;
    private String urlPhoto;
    private String email;

    private List<Vehicle> vehicleCollection;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<Vehicle> getVehicleCollection() {
        return vehicleCollection;
    }

    public void setVehicleCollection(List<Vehicle> vehicleCollection) {
        this.vehicleCollection = vehicleCollection;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", urlPhoto='" + urlPhoto + '\'' +
                ", email='" + email + '\'' +
                ", vehicleCollection=" + vehicleCollection +
                '}';
    }

    public User clone() {

        User u = new User();
        u.setId(getId());
        u.setName(getName());
        u.setUrlPhoto(getUrlPhoto());
        u.setEmail(getEmail());
        u.setVehicleCollection(getVehicleCollection());

        return u;
    }

}



