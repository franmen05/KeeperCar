package com.guille.keepercar.data.model.json;

import com.guille.keepercar.data.model.Maintenance;
import com.guille.keepercar.data.model.MaintenanceType;
import com.guille.keepercar.data.model.Vehicle;

/**
 * Created by Guille on 12/08/2015.
 */
public class MaintenanceSimple {

//
//    { "maintenanceId":1,
//      "dmanvehmysqlesc":"el acieite  esta caro ",
//      "date":null,
//      "cost":22134,
//    brandId,
//    vehicleId,
//    maintenanceTypeId
//    }

    private int id;

    private int type;


    private String date;


    private String desc;


    private int vehicle;


    private double cost;

    private String recommended;

    private String used;

    private Long distanceDo;

    public Long getDistanceDo() {
        return distanceDo;
    }

    public void setDistanceDo(Long distanceDo) {
        this.distanceDo = distanceDo;
    }

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public Double getCost() {
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

    @Override
    public String toString() {
        return "MaintenanceSimple{" +
                "id=" + id +
                ", date=" + date +
                ", desc='" + desc + '\'' +
                ", vehicle=" + vehicle +
                ", cost=" + cost +
                ", type=" + type +
                '}';
    }

    public Maintenance toMaintenance(){
        Maintenance m = new Maintenance();
        m.setId(getId());
        m.setCost(getCost());
        m.setDate(getDate().toString());
        m.setDesc(getDesc());
        m.setType(new MaintenanceType(getType()));
        m.setVehicle(new Vehicle(getVehicle()));
        m.setRecommended(getRecommended());
        m.setUsed(getUsed());
        m.setDistanceDo(getDistanceDo());

        return m;
    }

}
