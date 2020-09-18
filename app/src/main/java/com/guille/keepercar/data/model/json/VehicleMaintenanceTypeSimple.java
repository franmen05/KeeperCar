/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guille.keepercar.data.model.json;

import com.guille.keepercar.data.model.MaintenanceType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Guille
 */
public class VehicleMaintenanceTypeSimple {

    /**
     * indida  que el mantemiento debe ser realizado
     */
    public static final int TODO =1;

    private int vehicleId;
    private List<MaintenanceTypeSimple> types;
    private int todo;

    public VehicleMaintenanceTypeSimple(int vehicleId, List<MaintenanceTypeSimple> types) {
        this.vehicleId = vehicleId;
        this.types = types;
    }

    public VehicleMaintenanceTypeSimple(int vehicleId, int todo) {
        this.vehicleId = vehicleId;
        this.todo = todo;
    }

    public VehicleMaintenanceTypeSimple(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public List<MaintenanceTypeSimple> getTypes() {
        return types;
    }

    public void setTypes(List<MaintenanceTypeSimple> types) {
        this.types = types;
    }

    public void setTodo(int todo) {
        this.todo = todo;
    }

    public int getTodo() {
        return todo;
    }

    public List<MaintenanceType> toMaintenanceTypes() {

        List<MaintenanceType> r=new  ArrayList<>();
        for(MaintenanceTypeSimple mts:types){

          MaintenanceType mt= new MaintenanceType(mts.getMaintenanceTypeId());
          mt.setDistanceToApply(mts.getDistance());
          mt.setName(mts.getName());

          r.add(mt);
        }
        return r ;
    }

}
