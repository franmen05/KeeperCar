package com.guille.keepercar.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Guille on 09/06/2015.
 */
public class Vehicle implements Serializable{

//    {
//        "vehicleId": 1
//        "year": 3431
//        "distanceDaily": 3
//        "distanceActual": 2
//        "brandId": {
//            "brandId": 1
//            "name": "nombre"
//            "urlPhoto": "dirrecion de foto"
//            "type": 1
//        }-
//    }

    @SerializedName("vehicleId")
    private  int id;

    @SerializedName("brandId")
    private Brand  brand;

    @SerializedName("year")
    private int  year;

    @SerializedName("distanceDaily")
    private int distanceDaiLy;

    /**
     * en KM
     */
    @SerializedName("distanceActual")
    private long  distanceActual; //TODO: evaluar

    @SerializedName("maintenancetypeCollection")
    private List<MaintenanceType> maintenanceType;


    @SerializedName("dateUpdate")
    private String  dateUpdate;

    @SerializedName("carModel")
    private String  carModel;

    @SerializedName("status")
    private String  status;


    public Vehicle(int id) {
        this.id=id;
    }

    public Vehicle() { }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDistanceDaiLy() {
        return distanceDaiLy;
    }

    public void setDistanceDaiLy(int distanceDaiLy) {
        this.distanceDaiLy = distanceDaiLy;
    }


    public List<MaintenanceType> getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(List<MaintenanceType> maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public String getDateUpdate()  {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }


    public long getDistanceActual() {
        return distanceActual;
    }

    public void setDistanceActual(long distanceActual) {
        this.distanceActual = distanceActual;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", brand=" + brand +
                ", year=" + year +
                ", distanceDaiLy=" + distanceDaiLy +
                ", distanceActual=" + distanceActual +
                ", maintenanceType=" + maintenanceType +
                ", dateUpdate='" + dateUpdate + '\'' +
                ", carModel='" + carModel + '\'' +
                '}';
    }
}
