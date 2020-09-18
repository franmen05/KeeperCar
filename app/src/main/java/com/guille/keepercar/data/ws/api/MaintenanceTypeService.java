package com.guille.keepercar.data.ws.api;

import com.guille.keepercar.data.model.MaintenanceType;
import com.guille.keepercar.data.ws.ApiConstants;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Guille on 11/24/2015.
 */
    public interface MaintenanceTypeService {

    @GET(ApiConstants.URL_MAINTENANCE_TYPE)
    void findAll(Callback<List<MaintenanceType>> response) ;

    @POST(ApiConstants.URL_MAINTENANCE_TYPE)
    void add(@Body MaintenanceType maintenance, Callback<String> response) ;

//    TODO: crear  metodo para cargar los datos extras del tipo de  mantenoimientos( fecha, si es necesario realizar, y costo)

    @GET(ApiConstants.URL_MAINTENANCE_TYPE_ID)
    void getMantenanceType(@Path(ApiConstants.PARAM_ID) int id, Callback<MaintenanceType> response) ;


}
