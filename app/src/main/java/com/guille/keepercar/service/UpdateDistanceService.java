package com.guille.keepercar.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.guille.keepercar.R;
import com.guille.keepercar.data.model.Maintenance;
import com.guille.keepercar.data.model.MaintenanceType;
import com.guille.keepercar.data.model.User;
import com.guille.keepercar.data.model.Vehicle;
import com.guille.keepercar.data.model.json.MaintenanceTypeSimple;
import com.guille.keepercar.data.model.json.VehicleMaintenanceTypeSimple;
import com.guille.keepercar.data.ws.api.ServiceFacade;
import com.guille.keepercar.logic.Util;
import com.guille.keepercar.logic.VehicleUtil;
import com.guille.keepercar.data.model.Maintenance;
import com.guille.keepercar.data.model.MaintenanceType;
import com.guille.keepercar.data.model.User;
import com.guille.keepercar.data.model.Vehicle;
import com.guille.keepercar.data.model.json.MaintenanceTypeSimple;
import com.guille.keepercar.data.model.json.VehicleMaintenanceTypeSimple;
import com.guille.keepercar.data.ws.api.ServiceFacade;
import com.guille.keepercar.logic.Util;
import com.guille.keepercar.logic.VehicleUtil;
import com.guille.keepercar.view.BaseActivity;
import com.guille.keepercar.view.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UpdateDistanceService extends Service {

    private static UpdateDistanceService instance;
    private String tag;
    private String msj= "Debe realizar mantenimiento ";

    public UpdateDistanceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public static boolean isRunning() {
        return instance != null;
    }

//    public IBinder onBind(Intent intent) {
//        return null;
//    }

    public void onCreate() {
        Toast.makeText(this.getApplicationContext(), "Servicio  creado", Toast.LENGTH_LONG).show();
        instance = this;
    }

    public void onDestroy() {


        Toast.makeText(this.getApplicationContext(), "Servicio  destruido", Toast.LENGTH_LONG).show();
        hilo.interrupt();
        instance = null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
//        this.localizer.startLocation(this);
        Toast.makeText(this.getApplicationContext(), "Servicio  iniciado!!", Toast.LENGTH_LONG).show();
        if(!hilo.isAlive())
            hilo.start();

        return super.onStartCommand(intent, flags, startId);
    }

    Thread hilo = new Thread() {

        private DateFormat dateFormat;
        private String date;
        private boolean cent;

        public void run() {

            dateFormat = new SimpleDateFormat("HH");
            cent = true;

            while (cent){
                date = dateFormat.format(new Date());
                Log.d(tag, date );
                try {

                    if("04".equals(date)){

                        loadUser();
                    }
                    if("19".equals(date)){

                        loadUser();
                    }

                    // Stop 30 min
                    Thread.sleep(1800000);
//                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    };

    /**
     * Carga de usuario
     */
    private void loadUser() {
//        Toast.makeText(this.getApplicationContext(), "Servicio ejecuta: loadUser()", Toast.LENGTH_LONG).show();
        ServiceFacade.getUserService().findById("1", new Callback<User>() {
            @Override
            public void success(User _user, Response response) {

                Util.LOG_D(this, _user.toString());


                List<Vehicle> vehicles=_user.getVehicleCollection();
                for (final Vehicle v : vehicles) {
                    VehicleUtil.updateDistance(v);
                    for (final MaintenanceType mt : v.getMaintenanceType()) {
                        mt.getDistanceToApply();

                        ServiceFacade.getMaintenanceService().find(mt.getId(), v.getId(), new Callback<List<Maintenance>>() {

                            @Override
                            public void success(List<Maintenance> maintenances, Response response) {

                                List<MaintenanceTypeSimple> l= new ArrayList<>();

                                VehicleMaintenanceTypeSimple vm= new VehicleMaintenanceTypeSimple(v.getId(),
                                        VehicleMaintenanceTypeSimple.TODO);

                                for (Maintenance m : maintenances) {
                                    if(m!=null) {
                                        long d;
                                        if(null == m.getDistanceDo())
                                            d = mt.getDistanceToApply();
                                        else
                                            d = m.getDistanceDo() +  mt.getDistanceToApply();

                                        Log.d(tag, " Distance to apply : "+v.getId()+" => "+
                                                mt.getName()+"=>"+m.getId()+" => " + d);

                                        if(v.getDistanceActual()>=d) {
                                            tag = tag;
                                            Log.d(tag, " Se debe realizar mamtenimientos a : " + mt.getName());
                                            l.add(mt.toMaintenanceTypeSimple());

                                        }
                                    }
                                }
                                if(!l.isEmpty()) {

//                                  msj= msj + v.getBrand().getName()+" ";
                                    createNotification(msj);
                                    vm.setTypes(l);
                                    ServiceFacade.getVehicleService().updateMaintByVeh(vm, new Callback<String>() {
                                        @Override
                                        public void success(String s, Response response) {
                                            Log.d(tag, " actualizacion de mantemientos a realizar  exitosa ");
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.d(tag, error.getMessage());
                                        }
                                    });
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d(tag, error.getMessage());
                            }
                        });
//
//                        for (Maintenance m : mt.getMaintenances()) {
//                            Log.d(tag," " +m.getDistanceDo() );
//                        }

                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                Util.LOG_D(this, error.getMessage());

            }
        });
    }

    public void createNotification(String text) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.fab_bg_mini)
                        .setContentTitle(" Veh Man")
                        .setColor(getResources().getColor(R.color.blue_semi_transparent_pressed))
                        .setContentText(text);

// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.

        notificationManager.notify(BaseActivity.APP_ID, mBuilder.build());

    }
}


