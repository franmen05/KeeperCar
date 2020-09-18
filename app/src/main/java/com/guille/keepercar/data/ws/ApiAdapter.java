package com.guille.keepercar.data.ws;

import retrofit.RestAdapter;


public  class ApiAdapter<T> {

//    private static BrandService brandService;
//
//    public static BrandService getBrandService() {
//
//        if(brandService ==null) {
//            RestAdapter adapter = new RestAdapter.Builder()
//                    .setEndpoint(ApiConstants.URL_BASE)
//                    .setLogLevel(RestAdapter.LogLevel.BASIC).build();
//
//            brandService = adapter.create(BrandService.class);
//        }
//        return brandService;
//    }
////
//    private static MaintenanceTypeService maintananceTypeService;
//
//    public static MaintenanceTypeService getMaintenanceTypeService() {
//
//        if(maintananceTypeService ==null) {
//            RestAdapter adapter = new RestAdapter.Builder()
//                    .setEndpoint(ApiConstants.URL_BASE)
//                    .setLogLevel(RestAdapter.LogLevel.BASIC).build();
//
//            maintananceTypeService = adapter.create(MaintenanceTypeService.class);
//        }
//        return maintananceTypeService;
//    }
//
//    public ApiAdapter(Class<T> service) {
//        this.service = service;
//    }

    private  Object service;

    public  Object getService(Class<T> type) {

        if(service ==null) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ApiConstants.URL_BASE)
                    .setLogLevel(RestAdapter.LogLevel.BASIC).build();

            service = adapter.create(type);
        }
        return service;
    }
}
