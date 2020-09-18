package com.guille.keepercar.data.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.guille.keepercar.data.model.Brand;
import com.guille.keepercar.data.model.Maintenance;
import com.guille.keepercar.data.model.MaintenanceType;
import com.guille.keepercar.data.model.User;
import com.guille.keepercar.data.model.Vehicle;
import com.guille.keepercar.data.model.VehicleMaintenceType;
import com.guille.keepercar.logic.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Guille on 09/06/2015.
 */
public class SQLHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 7;
    private String TAG="SQLHelper";

    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDB(db);
//        insertTest(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        dropDB(db);
        createDB(db);
        insertTest(db);
    }


    public void createDB(SQLiteDatabase db) {
        db.execSQL(DBConstants.sqlCreateUser);
        db.execSQL(DBConstants.sqlCreateVehicle);
        db.execSQL(DBConstants.sqlCreateBrand);
        db.execSQL(DBConstants.sqlCreateUser_Vehicle);
        db.execSQL(DBConstants.sqlCreateMaintenance);
        db.execSQL(DBConstants.sqlCreateMaintenanceType);
        db.execSQL(DBConstants.sqlCreateVehicleMaintenanceType);
    }


    public void dropDB(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Vehicles");
        db.execSQL("DROP TABLE IF EXISTS Brand");
        db.execSQL("DROP TABLE IF EXISTS User_Vehicle");
        db.execSQL("DROP TABLE IF EXISTS MaintenanceType");
        db.execSQL("DROP TABLE IF EXISTS Maintenance");
        db.execSQL("DROP TABLE IF EXISTS Vehicle_MaintenanceType");

    }
    private void  insertTest(SQLiteDatabase db){

        //maintenanceType
        MaintenanceType mt= new MaintenanceType();
        mt.setName("gomas");
        mt.setDistanceToApply(10000);
        saveMaintenanceType(mt, db);


        ///Brand
        Brand b=  new Brand();
        b.setType(1);
        b.setName("Mazda");
        b.setUrlPhoto("");

        Brand b2=  new Brand();
        b2.setType(1);
        b2.setName("Toyota");
        b2.setUrlPhoto("");

        saveBrand(b, db);
        saveBrand(b2, db);

        //vehicle
        Vehicle v = new Vehicle();
//        v.setId(1);
        v.setYear(2006);
        v.setDistanceActual(100000);
        v.setDistanceDaiLy(22);
        v.setBrand(b);
        saveVehicle(v, db);


        //user
        User u = new User();
//        u.setId(1);
        u.setName("Guilermo");
        u.setEmail("franmen05@hotmail.com");
        u.setUrlPhoto("");
        saveUser(u, db);


        //maintenance
        Maintenance m =  new Maintenance();
//        m.setId(1);
        m.setDesc("teste");
        m.setType(mt);
//        m.setDate(new Date());
        m.setVehicle(v);
        saveMaintenance(m, db);

        Maintenance m2 =  new Maintenance();
//        m.setId(1);
        m2.setDesc("teste 2");
        m2.setType(mt);
//        m2.setDate(new Date());
        m2.setVehicle(v);
        saveMaintenance(m2, db);
    }

    //Mantemientos
    public List<Maintenance> getMaintenance(SQLiteDatabase db) {

        Cursor c = db.rawQuery(" SELECT maintenanceId ,maintenanceTypeId ,desc ,date,vehicleId FROM "+DBConstants.TABLE_MAINTENANCE,
                (String[])null);

        return getDataMaintenance(c);
    }
    public List<Maintenance> getMaintenanceByType(MaintenanceType type,SQLiteDatabase db) {

        Cursor c = db.rawQuery(" SELECT maintenanceId ,maintenanceTypeId ,desc ,date,vehicleId,cost FROM "+DBConstants.TABLE_MAINTENANCE +
                " WHERE maintenanceTypeId="+type.getId(), (String[])null);

        return getDataMaintenance(c);
    }
    public int getCountMaintenanceByType(MaintenanceType type,SQLiteDatabase db) {

        Cursor c = db.rawQuery(" SELECT maintenanceId ,maintenanceTypeId ,desc ,date,vehicleId FROM "+DBConstants.TABLE_MAINTENANCE +
                " WHERE maintenanceTypeId="+type.getId(), (String[])null);

        return c.getCount();
    }


    public List<Maintenance> getMaintenanceByType(MaintenanceType type, Vehicle v, SQLiteDatabase db) {

        Cursor c = db.rawQuery(" SELECT maintenanceId ,maintenanceTypeId ,desc ,date,vehicleId FROM "+DBConstants.TABLE_MAINTENANCE +
                " WHERE maintenanceTypeId="+type.getId()+" AND vehicleId="+v.getId(), (String[])null);

        return getDataMaintenance(c);
    }

    private List<Maintenance> getDataMaintenance(Cursor c) {

        List<Maintenance> r = new ArrayList<>();

        if(c.moveToFirst()) {
            do {
                Maintenance m= new Maintenance();
                m.setId(c.getInt(0));
                m.setType(new MaintenanceType(c.getInt(1)));
                m.setDesc(c.getString(2));

                m.setDate(c.getString(3));
                m.setVehicle(new Vehicle(c.getInt(4)));
                m.setCost(c.getDouble(5));
                r.add(m);
            } while(c.moveToNext());
        }

        return r;
    }

    public void saveMaintenance(Maintenance m, SQLiteDatabase db) {
        if (db != null) {
            db.execSQL("INSERT INTO " + DBConstants.TABLE_MAINTENANCE + " ( maintenanceTypeId,vehicleId,desc,date,cost ) " +
                    " VALUES (" + m.getType().getId() +
                    "," + m.getVehicle().getId() + ",\""
                    + m.getDesc() +
                    "\",\"" + m.getDate() + "\"," +
                    m.getCost() + ")");
        }
    }

    public void deleteMaintenance(Maintenance m, SQLiteDatabase db) {
        if (db != null) {
            db.delete(DBConstants.TABLE_MAINTENANCE, "maintenanceId=" + m.getId(), null);
        }
    }

    public void updateMaintenance(Maintenance m, SQLiteDatabase db) {
        if (db != null) {
            deleteMaintenance(m, db);
            saveMaintenance(m, db);
        }
    }

    //MaintenanceType

    public MaintenanceType getMaintenanceTypeByID(int id,SQLiteDatabase db) {


        MaintenanceType type= null;
        Cursor c = db.rawQuery(" SELECT maintenanceTypeId,name,distance FROM  "+DBConstants.TABLE_MAINTENANCE_TYPE
        +"  WHERE maintenanceTypeId ="+id, (String[])null);

        if(c.moveToFirst()) {
            do {
                type= buildMaintenanceType(c);

            } while(c.moveToNext());
        }
        return type;
    }

    private Cursor excuteQueryGetMaintenanceType(SQLiteDatabase db){
        return db.rawQuery(" SELECT maintenanceTypeId,name,distance FROM  "+
                DBConstants.TABLE_MAINTENANCE_TYPE, (String[])null);
    }

    public List<MaintenanceType> getMaintenanceType(SQLiteDatabase db) {

        List<MaintenanceType> r = new ArrayList<>();

        Cursor c = excuteQueryGetMaintenanceType(db);
        if(c.moveToFirst()) {
            do {

                r.add(buildMaintenanceType(c));
            } while(c.moveToNext());
        }
        return r;
    }

    public TreeMap<String,MaintenanceType> getMaintenanceTypeMap(SQLiteDatabase db) {

        TreeMap<String,MaintenanceType> r = new TreeMap<>();
        Cursor c = excuteQueryGetMaintenanceType(db);

        if(c.moveToFirst()) {
            do {
                MaintenanceType mt=buildMaintenanceType(c);
                r.put(mt.getName(), mt);
            } while(c.moveToNext());
        }
        return r;
    }

    private MaintenanceType buildMaintenanceType(Cursor c){
        MaintenanceType type = new MaintenanceType(c.getInt(0));
        type.setName(c.getString(1));
        type.setDistanceToApply(c.getLong(2));
        return type;
    }

    public List<MaintenanceType> getMaintenanceTypeByVehicle(Vehicle v,SQLiteDatabase db) {

        List<MaintenanceType> r = new ArrayList<>();

        Cursor c = db.rawQuery(" SELECT maintenanceTypeId FROM "+DBConstants.TABLE_VEHICLE_MAINTENANCETYPE
                +" WHERE vehicleId="+v.getId(), (String[])null);

        if(c.moveToFirst()) {
            do {
                r.add(getMaintenanceTypeByID(c.getInt(0),db));
            } while(c.moveToNext());
        }

        Log.d(TAG+"-getMaintenanceTypeByVe", "SIZE : "+ r.size());
        return r;
    }

    public void saveMaintenanceType(MaintenanceType mt, SQLiteDatabase db){
        if (db != null)
            db.execSQL("INSERT INTO " + DBConstants.TABLE_MAINTENANCE_TYPE + " ( name,distance )  VALUES ("
                     + "\"" + mt.getName()
                    + "\"," + mt.getDistanceToApply()+")");
    }

    public void saveAllMaintenanceType(List<MaintenanceType> maintenanceTypes, SQLiteDatabase db){

        for (MaintenanceType type : maintenanceTypes)
            saveMaintenanceType(type,db);
    }

    public void saveMaintenanceTypeByVehicle(VehicleMaintenceType m, SQLiteDatabase db){
        if (db != null)
            db.execSQL("INSERT INTO " + DBConstants.TABLE_VEHICLE_MAINTENANCETYPE + " ( vehicleId,maintenanceTypeId )  VALUES ( "
                    + m.getVehicle().getId() + "," + m.getMaintenanceType().getId() + ")");
    }

    public void deleteMaintenanceTypeByVehicle(Vehicle v, SQLiteDatabase db){
        if (db != null)
            db.delete(DBConstants.TABLE_VEHICLE_MAINTENANCETYPE, "vehicleId=" + v.getId(), null);

    }

    public void deleteMaintenanceType(MaintenanceType mt, SQLiteDatabase db) {

        if (db != null)
            db.delete(DBConstants.TABLE_MAINTENANCE_TYPE, "maintenanceTypeId=" + mt.getId(), null);
    }

    public void updateMaintenanceType(MaintenanceType mt, SQLiteDatabase db) {
        if (db != null) {
            deleteMaintenanceType(mt, db);
            saveMaintenanceType(mt, db);
        }
    }

    ///vehiculo

    public List<Vehicle> getVehicle(SQLiteDatabase db) {

        Cursor c = db.rawQuery(" SELECT vehicleId , brandId ,year ,distanceDaily ,distanceActual  FROM "+
                DBConstants.TABLE_VEHICLE , (String[])null);

        List<Vehicle> r = new ArrayList<>();

        if(c.moveToFirst()) {
            do {
                Vehicle o= new Vehicle();
                o.setId(c.getInt(0));
                o.setBrand(new Brand(c.getInt(1)));
                o.setYear(c.getInt(2));
                o.setDistanceDaiLy(c.getInt(3));
                o.setDistanceActual(c.getLong(4));
                r.add(o);

            } while(c.moveToNext());
        }

        return r;
    }

    public void saveVehicle(Vehicle v, SQLiteDatabase db){

        if(v.getBrand()==null) {
            Util.LOG_D(this, "la marca es nula");
            throw  new NullPointerException("La marca  es nula");
        }


        if (db != null)
            db.execSQL("INSERT INTO " + DBConstants.TABLE_VEHICLE + " ( brandId ,year ,distanceDaily ,distanceActual)" +
                    "  VALUES (" + v.getBrand().getId() + "," +
                    v.getYear() + "," +
                    v.getDistanceDaiLy() +
                    ",\"" + v.getDistanceActual() + "\")");
    }

    public void deleteVehicle(Vehicle v, SQLiteDatabase db) {

        if (db != null)
            db.delete(DBConstants.TABLE_VEHICLE, "vehicleId=" + v.getId(), null);
    }

    public void updateVehicle(Vehicle v, SQLiteDatabase db) {

        if (db != null) {
            deleteVehicle(v, db);
            saveVehicle(v, db);
        }
    }

    ///Marca

    public Brand getBrandByName( String b,SQLiteDatabase db) {

        Cursor c = db.rawQuery(" SELECT brandId ,name ,urlPhoto ,type  FROM "+DBConstants.TABLE_BRAND+
                " WHERE name=\'"+b+"\' ", (String[])null);

        if(c.moveToFirst()) {
            do {

                return buildBrand(c);

            } while(c.moveToNext());
        }

        return null;
    }


    public List<Brand> getBrands(SQLiteDatabase db) {

        Cursor c = db.rawQuery(" SELECT brandId ,name ,urlPhoto ,type  FROM "+DBConstants.TABLE_BRAND , (String[])null);

        List<Brand> r = new ArrayList<>();

        if(c.moveToFirst()) {
            do {

                r.add(buildBrand(c));

            } while(c.moveToNext());
        }

        return r;
    }

    private Brand buildBrand(Cursor c){
        Brand brand= new Brand();
        brand.setId(c.getInt(0));
        brand.setName(c.getString(1));
        brand.setUrlPhoto(c.getString(2));
        brand.setType(c.getInt(3));
        return brand;
    }

    public void saveBrand(Brand b, SQLiteDatabase db){

        if (db != null)
            db.execSQL("INSERT INTO " + DBConstants.TABLE_BRAND + " (name ,urlPhoto ,type )  VALUES ( \""
                    + b.getName() +
                    "\",\"" +
                    b.getUrlPhoto()+"\","
                    +b.getType()+")");

    }

    public void deleteBrand(Brand b, SQLiteDatabase db){

        if (db != null)
            db.delete(DBConstants.TABLE_VEHICLE, "brandId=" + b.getId(), null);
    }

    public void deleteAllBrands( SQLiteDatabase db){

        if (db != null)
            db.delete(DBConstants.TABLE_VEHICLE,null,null);
    }

    public void updateBrand(Brand b, SQLiteDatabase db){

        if (db != null) {
            deleteBrand(b, db);
            saveBrand(b, db);
        }
    }

    ///User
    public void saveUser(User u, SQLiteDatabase db){

        if (db != null)
            db.execSQL("INSERT INTO " + DBConstants.TABLE_USER + " ( name, urlPhoto, email) VALUES ( "
                     + "\"" + u.getName() +
                    "\",\"" + u.getUrlPhoto()
                    +"\",\"" +u.getEmail()+"\")");
    }

    public void deleteUser(User u, SQLiteDatabase db){

        if (db != null)
            db.delete(DBConstants.TABLE_USER, "userId=" + u.getId(), null);
    }

    public void updateUser(User u, SQLiteDatabase db){

        if (db != null) {
            deleteUser(u, db);
            saveUser(u, db);
        }
    }

}
