package com.guille.keepercar.data.ws.api;

import com.guille.keepercar.data.ws.ApiAdapter;

/**
 * Created by Guille on 01/31/2016.
 */
public class ServiceFacade {

    //Servicios
    private static ApiAdapter<UserService> userApiAdapter;
    private static UserService userService;

    private static ApiAdapter<VehicleService> vehicleApiAdapter;
    private static VehicleService vehicleService;

    private static ApiAdapter<MaintenanceService> maintenanceApiAdapter;
    private static MaintenanceService maintenanceService;

    private static ApiAdapter<MaintenanceTypeService> maintenanceTypeApiAdapter;
    private static MaintenanceTypeService maintenanceTypeService;

    private static ApiAdapter<ConfigService> configApiAdapter;
    private static ConfigService configService;

    public static UserService getUserService() {
        if(userApiAdapter==null)
            userApiAdapter = new ApiAdapter<>();

        if(userService==null)
            userService = (UserService) userApiAdapter.getService(UserService.class);

        return userService;
    }

    public static VehicleService getVehicleService() {

        if(vehicleApiAdapter ==null)
            vehicleApiAdapter = new ApiAdapter<>();


        if(vehicleService ==null)
            vehicleService = ((VehicleService) vehicleApiAdapter.getService(VehicleService.class));

        return vehicleService;
    }

    public static MaintenanceService  getMaintenanceService() {
        if(maintenanceApiAdapter==null)
            maintenanceApiAdapter = new ApiAdapter<>();

        if(maintenanceService ==null)
            maintenanceService = ((MaintenanceService)maintenanceApiAdapter.getService(MaintenanceService.class));

        return maintenanceService;
    }

    public static MaintenanceTypeService  getMaintenanceTypeService() {
        if(maintenanceTypeApiAdapter==null)
            maintenanceTypeApiAdapter = new ApiAdapter<>();

        if(maintenanceTypeService ==null)
            maintenanceTypeService = ((MaintenanceTypeService)maintenanceTypeApiAdapter.getService(MaintenanceTypeService.class));

        return maintenanceTypeService;
    }

    public static ConfigService  getConfigService() {
        if(configApiAdapter==null)
            configApiAdapter= new ApiAdapter<>();

        if(configService==null)
            configService = ((ConfigService)configApiAdapter.getService(ConfigService.class));

        return configService;
    }

}

