package com.guille.keepercar.data.model;

import com.google.gson.annotations.SerializedName;
import com.guille.keepercar.data.model.json.MaintenanceSimple;

import java.io.Serializable;

/**
 * Created by Guille on 09/02/2015.
 */
public class Maintenance implements Serializable {

//
//    { "maintenanceId":1,
//      "dmanvehmysqlesc":"el acieite  esta caro ",
//      "date":null,
//      "cost":22134,
//    brandId,
//    vehicleId,
//    maintenanceTypeId
//    }

    @SerializedName("maintenanceId")
    private int  id;

    @SerializedName("maintenanceTypeId")
    private MaintenanceType type;

    @SerializedName("date")
    private String date;

    @SerializedName("dmanvehmysqlesc")
    private String desc;

    @SerializedName("vehicleId")
    private Vehicle vehicle;

    @SerializedName("cost")
    private double cost;

    @SerializedName("recommended")
    private String recommended;

    @SerializedName("used")
    private String used;

    @SerializedName("distanceDo")
    private Long distanceDo;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MaintenanceType getType() {
        return type;
    }

    public void setType(MaintenanceType type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public Long getDistanceDo() {
        return distanceDo;
    }

    public void setDistanceDo(Long distanceDo) {
        this.distanceDo = distanceDo;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "id=" + id +
                " distance to do= '"+distanceDo +
                ", date=" + date +
                ", desc='" + desc + '\'' +
                ", vehicle=" + vehicle +
                ", cost=" + cost +
                ", type=" + type +
                '}';
    }

    public MaintenanceSimple toMaintenanceSimple(){

        MaintenanceSimple m = new MaintenanceSimple();
        m.setId(getId());
        m.setCost(getCost());
        m.setDate(getDate());
        m.setDesc(getDesc());
        m.setType(getType().getId());
        m.setVehicle(getVehicle().getId());
        m.setRecommended(getRecommended());
        m.setUsed(getUsed());
        m.setDistanceDo(getDistanceDo());

        return m;
    }

}