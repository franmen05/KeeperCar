package com.guille.keepercar.data.dal;

/**
 * Created by Guille on 09/17/2015.
 */
public class DBConstants {


    public static final String DB_NAME = "DB_ManVeh";
    ///Tablet  name
    static final String TABLE_MAINTENANCE="Maintenance";
    static final String TABLE_MAINTENANCE_TYPE="MaintenanceType";
    static final String TABLE_VEHICLE="Vehicles";
    static final String TABLE_BRAND="Brand";
    static final String TABLE_USER="User";
    static final String TABLE_VEHICLE_MAINTENANCETYPE="Vehicle_MaintenanceType";

    static final String sqlCreateUser = "CREATE TABLE "+TABLE_USER+" (userId INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,urlPhoto TEXT,email TEXT);";
    static final String sqlCreateVehicle = " CREATE TABLE "+TABLE_VEHICLE+" (vehicleId INTEGER PRIMARY KEY AUTOINCREMENT, brandId INTEGER," +
            "year INTEGER,distanceDaily INTEGER,distanceActual TEXT);";
    static final String sqlCreateBrand ="CREATE TABLE "+TABLE_BRAND+" (brandId INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,urlPhoto TEXT,type INTEGER );";

    static final String sqlCreateUser_Vehicle = " CREATE TABLE User_Vehicle ( userId INTEGER , vehicleId INTEGER  );";

    static final String sqlCreateMaintenanceType ="CREATE TABLE "+TABLE_MAINTENANCE_TYPE+" (maintenanceTypeId INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,distance NUMERIC);";
    static final String sqlCreateMaintenance ="CREATE TABLE "+TABLE_MAINTENANCE+" (maintenanceId INTEGER PRIMARY KEY AUTOINCREMENT,maintenanceTypeId INTEGER," +
            "vehicleId INTEGER, desc TEXT,date TEXT,cost NUMERIC,FOREIGN KEY(maintenanceTypeId) REFERENCES MaintenanceType(maintenanceTypeId) );";
    static final String sqlCreateVehicleMaintenanceType ="CREATE TABLE "+TABLE_VEHICLE_MAINTENANCETYPE+" (vehicleId INTEGER ,maintenanceTypeId INTEGER);";


}
