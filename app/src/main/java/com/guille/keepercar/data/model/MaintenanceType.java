package com.guille.keepercar.data.model;

import com.google.gson.annotations.SerializedName;
import com.guille.keepercar.data.model.json.MaintenanceTypeSimple;

import java.io.Serializable;
import java.util.List;

/**
 * un ejemplo  de lo que es un tipo de mantenimiento seria el cambio de  aceite.
 * Created by Guille on 09/02/2015.
 */
public class MaintenanceType implements Serializable{

//    {
//        "maintenanceTypeId":1,
//            "name":"aceite",
//            "distance":10000
//    }

    @SerializedName("maintenanceTypeId")
    private Integer id;

    @SerializedName("name")
    private String name;
    /**
     * Distance in  KMs
     */
    @SerializedName("distance")
    private Long distanceToApply;

    @SerializedName("forToDo")
    private Boolean forToDo;

    @SerializedName("lastCost")
    private Double lastCost;

    @SerializedName("lastDate")
    private Double lastDate;


    public MaintenanceType(int id) {
        this.setId(id);
    }


    /**
     *
     */
    private List<Maintenance> maintenances;

    public MaintenanceType() {

    }

    public Integer getId() {
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

    public Long getDistanceToApply() {
        return distanceToApply;
    }

    public void setDistanceToApply(long distanceToApply) {
        this.distanceToApply = distanceToApply;
    }

    public List<Maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }

    public Boolean getForToDo() {
        return forToDo;
    }

    public Double getLastCost() {
        return lastCost;
    }

    public Double getLastDate() {
        return lastDate;
    }

    public void setForToDo(Boolean forToDo) {
        this.forToDo = forToDo;
    }

    public void setLastCost(Double lastCost) {
        this.lastCost = lastCost;
    }

    public void setLastDate(Double lastDate) {
        this.lastDate = lastDate;
    }

    public MaintenanceTypeSimple toMaintenanceTypeSimple(){

        MaintenanceTypeSimple m= new  MaintenanceTypeSimple();
        m.setName(getName());
        m.setDistance(getDistanceToApply());
        m.setMaintenanceTypeId(getId());

        return m;
    }

    @Override
    public String toString() {
        return "MaintenanceType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", distanceToApply=" + distanceToApply +
                ", maintenances=" + maintenances +
                '}';
    }
}
