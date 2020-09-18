package com.guille.keepercar.logic;

import android.util.Log;

import com.guille.keepercar.data.model.Vehicle;
import com.guille.keepercar.data.ws.api.ServiceFacade;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Guille on 01/31/2016.
 */
public class VehicleUtil {

    private static String TAG="VehicleUtil";

    public static  void updateDistance(Vehicle v) {
        try {
                Log.d(TAG, " XD "+ Util.getDiffDays(Util.getDateFromApi(v.getDateUpdate())));
                Log.d(TAG, " XD " + v.getDistanceDaiLy());

            long distanceActual = v.getDistanceActual() + (Util.getDiffDays(Util.getDateFromApi(v.getDateUpdate())) * v.getDistanceDaiLy());
            v.setDistanceActual(distanceActual);

//                Log.d(TAG, " XD " + new Date());
//                Log.d(TAG, " XD " + Util.formarDateForApi(new Date()));
//                Log.d(TAG, " XD " + new Date().getTime());
            v.setDateUpdate(Util.formarDateForApi(new Date()));

            //TODO: necesita validacion de cuando un vehiculo se actualizo correctamente.
            ServiceFacade.getVehicleService().add(v, new Callback<String>() {
                @Override
                public void success(String s, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(TAG, error.toString());
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void updateDistance(List<Vehicle> vehicles) {
        for (Vehicle v : vehicles) {
            updateDistance(v);
        }
    }
}
