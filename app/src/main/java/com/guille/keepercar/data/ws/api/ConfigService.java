package com.guille.keepercar.data.ws.api;

import com.guille.keepercar.data.model.Configuration;
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
public interface ConfigService {

    @GET(ApiConstants.URL_CONFIGURATIONS_ID)
    void findById(@Path(ApiConstants.PARAM_ID) String idUSer, Callback<List<Configuration>> response) ;

    @POST(ApiConstants.URL_CONFIGURATIONS_CUSTOM)
    void addConfig(@Body Configuration config, @Path(ApiConstants.PARAM_ID) String idUSer, Callback<Configuration> response) ;

}

