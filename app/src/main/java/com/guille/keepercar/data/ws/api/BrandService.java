package com.guille.keepercar.data.ws.api;

import com.guille.keepercar.data.model.Brand;
import com.guille.keepercar.data.ws.ApiConstants;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Guille on 11/24/2015.
 */
public interface BrandService {

//
//    @GET("")
//    public void create(Brand entity);
//
//
//    @PUT("")
//    public void edit(Integer id,  Brand entity) ;
//
//    @DELETE("")
//    public void remove(@PathVariable Integer id);

//    @GET(ApiConstants.URL_DEALS_BY_DESC)
//    public Brand find(@Query(ApiConstants.PARAM_ID) String desc, Callback<List<Brand>> response) ;

    @GET(ApiConstants.URL_BRAND)
    void findAll(Callback<List<Brand>> response) ;
}

