package com.guille.keepercar.data.model;

/**
 * Created by Guille on 09/25/2015.
 */
public class VehicleMaintenceType {

    private Vehicle vehicle;
    private MaintenanceType maintenanceType;

    public VehicleMaintenceType(Vehicle vehicle, MaintenanceType maintenanceType) {
        this.vehicle = vehicle;
        this.maintenanceType = maintenanceType;
    }

    public VehicleMaintenceType() {

    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }
}
