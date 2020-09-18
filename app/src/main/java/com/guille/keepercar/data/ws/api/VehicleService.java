package com.guille.keepercar.data.ws.api;

import com.guille.keepercar.data.model.Vehicle;
import com.guille.keepercar.data.model.json.VehicleMaintenanceTypeSimple;
import com.guille.keepercar.data.ws.ApiConstants;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Guille on 11/24/2015.
 */
public interface VehicleService {


    @GET(ApiConstants.URL_VEHICLE)
    void findAll(Callback<List<Vehicle>> response) ;

    @GET(ApiConstants.URL_VEHICLE_ID)
    void findById(@Path(ApiConstants.PARAM_ID) int id, Callback<Vehicle> response) ;

    @POST(ApiConstants.URL_VEHICLE)
    void add(@Body Vehicle vehicle, Callback<String> response) ;

    @DELETE(ApiConstants.URL_VEHICLE_DELETE)
    void delete(@Path(ApiConstants.PARAM_ID) int id, Callback<String> response) ;

    @POST(ApiConstants.URL_VEH_MAINTENANCETYPE)
    void addMaintByVeh(@Body VehicleMaintenanceTypeSimple vehMaintenanceType, Callback<String> response) ;

    @POST(ApiConstants.URL_VEH_MAINTENANCETYPE_UPDATE)
    void updateMaintByVeh(@Body VehicleMaintenanceTypeSimple vehMaintenanceType, Callback<String> response) ;

    @POST(ApiConstants.URL_VEH_MAINTENANCETYPE_DELETE)
    void deleteMaintByVeh(@Body VehicleMaintenanceTypeSimple vehMaintenanceType, Callback<String> response) ;

    /// agregar  metodo para borrar ti

}