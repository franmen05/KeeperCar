package com.guille.keepercar.data.ws;


public class ApiConstants {

//    GET api/Deals?offset={offset}&limit={limit}
//    No documentation available.
//    GET api/Deals?description={description}&offset={offset}&limit={limit}

//    public static final String URL_BASE="http://192.168.1.101:8084";
    public static final String URL_BASE="http://10.0.0.11:8084";
//    public static final String URL_BASE="http://edeals.do:8080";
    public static final String URL_WS="/ManVehServ/";


    public static final String URL_BRAND="/ManVehServ/brand/";
//    public static final String URL_DEALS="/api/deals?limit=400&offset=0";
//    public static final String URL_DEALS_BY_DESC=URL_DEALS+ PARAM_ID +"={desValue}";

    public static final String URL_MAINTENANCE_TYPE=URL_WS+"maintenancetype/";
    public static final String URL_MAINTENANCE_TYPE_ID=URL_MAINTENANCE_TYPE+"{"+ApiConstants.PARAM_ID +"}";

    /**
     * Tipo de mantemientos segun vehiculo
     */
    public static final String URL_VEH_MAINTENANCETYPE=URL_WS+"vehicleMaintenanceType/";
    public static final String URL_VEH_MAINTENANCETYPE_DELETE=URL_VEH_MAINTENANCETYPE+"delete";
    public static final String URL_VEH_MAINTENANCETYPE_UPDATE=URL_VEH_MAINTENANCETYPE+"update";

    public static final String URL_MAINTENANCE=URL_WS+"maintenance/";
    public static final String URL_MAINTENANCE_DELETE=URL_MAINTENANCE+"{"+ApiConstants.PARAM_ID +"}";
    public static final String URL_MAINTENANCE_FIND=URL_MAINTENANCE+"{"+ApiConstants.PARAM_ID_TYPE +"}/{"+ApiConstants.PARAM_ID_VEH+"}";


    public static final String URL_VEHICLE=URL_WS+"vehicle/";
    public static final String URL_VEHICLE_DELETE=URL_VEHICLE+"{"+ApiConstants.PARAM_ID +"}";
    public static final String URL_VEHICLE_ID=URL_VEHICLE_DELETE;


    public static final String URL_USER=URL_WS+"user/";
    public static final String URL_USER_ID=URL_WS+"user/{"+ApiConstants.PARAM_ID +"}";
    public static final String URL_USER_VEH=URL_WS+"user/vehicle/";

    //parametros
    public static final String PARAM_ID="id";
    public static final String PARAM_ID_VEH = "id_veh";
    public static final String PARAM_ID_TYPE = "id_type";

    //configuraciones
    public static final String URL_CONFIGURATIONS=URL_WS+"configurations/";
    public static final String URL_CONFIGURATIONS_ID=URL_WS+"configurations/{"+ApiConstants.PARAM_ID +"}";
    public static final String URL_CONFIGURATIONS_CUSTOM=URL_WS+"configurations/custom/{"+ApiConstants.PARAM_ID +"}";

}