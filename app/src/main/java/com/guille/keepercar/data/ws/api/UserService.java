package com.guille.keepercar.data.ws.api;


import com.guille.keepercar.data.model.User;
import com.guille.keepercar.data.model.json.UserVehSimple;
import com.guille.keepercar.data.ws.ApiConstants;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Guille on 11/24/2015.
 */
public interface UserService {


    @GET(ApiConstants.URL_USER_ID)
    void findById(@Path(ApiConstants.PARAM_ID) String id, Callback<User> response);


    @POST(ApiConstants.URL_USER_VEH)
    void addVehicle(@Body UserVehSimple userVeh, Callback<String> response);


    @POST(ApiConstants.URL_USER)
    void add(@Body User user, Callback<User> response);


}
