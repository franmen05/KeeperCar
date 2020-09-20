package com.guille.keepercar.data.ws

object ApiConstants {
    //    GET api/Deals?offset={offset}&limit={limit}
    //    No documentation available.
    //    GET api/Deals?description={description}&offset={offset}&limit={limit}
    const val URL_BASE = "http://172.25.144.1:8080/"

    //    public static final String URL_BASE="http://10.0.0.11:8084";
    //    public static final String URL_BASE="http://edeals.do:8080";
    private const val URL_WS = "/"
    const val URL_USER_VEH = URL_WS + "user/vehicle/"

    //parametros
    const val PARAM_ID = "id"
    const val PARAM_ID_VEH = "id_veh"
    const val PARAM_ID_TYPE = "id_type"

    const val URL_BRAND = "${URL_WS}brand/"

    //    public static final String URL_DEALS="/api/deals?limit=400&offset=0";
    //    public static final String URL_DEALS_BY_DESC=URL_DEALS+ PARAM_ID +"={desValue}";
    const val URL_MAINTENANCE_TYPE = URL_WS + "maintenancetype/"
    const val URL_MAINTENANCE_TYPE_ID = "$URL_MAINTENANCE_TYPE{$PARAM_ID}"

    /**
     * Tipo de mantemientos segun vehiculo
     */
    const val URL_VEH_MAINTENANCETYPE = URL_WS + "vehicleMaintenanceType/"
    const val URL_VEH_MAINTENANCETYPE_DELETE = URL_VEH_MAINTENANCETYPE + "delete"
    const val URL_VEH_MAINTENANCETYPE_UPDATE = URL_VEH_MAINTENANCETYPE + "update"
    const val URL_MAINTENANCE = URL_WS + "maintenance/"
    const val URL_MAINTENANCE_DELETE = URL_MAINTENANCE + "{" + PARAM_ID + "}"
    const val URL_MAINTENANCE_FIND =
        URL_MAINTENANCE + "{" + PARAM_ID_TYPE + "}/{" + PARAM_ID_VEH + "}"
    const val URL_VEHICLE = URL_WS + "vehicle/"
    const val URL_VEHICLE_DELETE = URL_VEHICLE + "{" + PARAM_ID + "}"
    const val URL_VEHICLE_ID = URL_VEHICLE_DELETE
    const val URL_USER = URL_WS + "user/"
    const val URL_USER_ID = URL_WS + "user/{" + PARAM_ID + "}"


    //configuraciones
    const val URL_CONFIGURATIONS = URL_WS + "configurations/"
    const val URL_CONFIGURATIONS_ID = URL_WS + "configurations/{" + PARAM_ID + "}"
    const val URL_CONFIGURATIONS_CUSTOM = URL_WS + "configurations/custom/{" + PARAM_ID + "}"
}