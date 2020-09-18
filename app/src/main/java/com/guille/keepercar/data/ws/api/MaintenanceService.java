package com.guille.keepercar.data.ws.api;


import com.guille.keepercar.data.model.Maintenance;
import com.guille.keepercar.data.model.json.MaintenanceSimple;
import com.guille.keepercar.data.ws.ApiConstants;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by Guille on 11/24/2015.
 */
public interface MaintenanceService {


    @GET(ApiConstants.URL_MAINTENANCE)
    void findAll(Callback<List<Maintenance>> response) ;

    @GET(ApiConstants.URL_MAINTENANCE_FIND)
    void find(@Path(ApiConstants.PARAM_ID_TYPE) int idtype, @Path(ApiConstants.PARAM_ID_VEH) int idveh, Callback<List<Maintenance>> response) ;

    @POST(ApiConstants.URL_MAINTENANCE)
    void add(@Body MaintenanceSimple maintenance, Callback<String> response) ;

    @PUT(ApiConstants.URL_MAINTENANCE)
    void update(@Body MaintenanceSimple maintenance, Callback<String> response) ;

    @DELETE(ApiConstants.URL_MAINTENANCE_DELETE)
    void delete(@Path(ApiConstants.PARAM_ID) int id, Callback<String> response) ;





}
