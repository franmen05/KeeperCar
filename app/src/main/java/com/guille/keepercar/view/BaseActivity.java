package com.guille.keepercar.view;

import android.app.NotificationManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.guille.keepercar.R;
import com.guille.keepercar.data.dal.DBConstants;
import com.guille.keepercar.data.dal.SQLHelper;
import com.guille.keepercar.data.model.Configuration;
import com.guille.keepercar.data.ws.api.ConfigService;
import com.guille.keepercar.data.ws.api.ServiceFacade;
import com.guille.keepercar.data.ws.api.UserService;
import com.guille.keepercar.data.ws.api.VehicleService;
import com.guille.keepercar.logic.Util;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BaseActivity extends AppCompatActivity {

    public static final int APP_ID = 234324543;

    //db
    private SQLHelper sqlHelper;
    private SQLiteDatabase db;

    private ConfigService configService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConfig();
    }


    //TODO evaludar  para que se usa este  metodo.
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initConfig();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initConfig();
    }

    protected void initConfig() {

        if(Util.getConfigMap().isEmpty()) {

            configService = ServiceFacade.getConfigService();
            configService.findById(getDeviceId(), new Callback<List<Configuration>>() {
                @Override
                public void success(List<Configuration> configurations, Response response) {
                    Util.setConfig(configurations);
                    postInitConfig(configurations);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }


    protected void postInitConfig(List<Configuration> configurations) {

    }

    protected String getDeviceId() {
//        TelephonyManager mTelephonyManager=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //        return mTelephonyManager.getDeviceId();
        return android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Util.getConfigMap().  clear();
        destroyDBConnection();

    }

    protected VehicleService getVehicleService(){
        return ServiceFacade.getVehicleService();
    }

    protected UserService getUserService() {
        return ServiceFacade.getUserService();
    }

    protected void setMainToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
//            ab.setShowHideAnimationEnabled(true);
        }
    }

    void  createDBConnection(){

         if(sqlHelper== null){

             sqlHelper= new SQLHelper(this, DBConstants.DB_NAME,null,SQLHelper.DB_VERSION);
             db= sqlHelper.getWritableDatabase();
         }
     }

     void  destroyDBConnection(){

         if(sqlHelper!= null) {


             db = sqlHelper.getWritableDatabase();
             if (db != null)
                 db.close();

             sqlHelper.close();
             sqlHelper = null;
         }

    }

     SQLHelper getSqlHelper() {
         createDBConnection();
        return sqlHelper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }



    protected void closeNotification() {

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(APP_ID);
    }

    public ConfigService getConfigService() {
        if(configService==null)
            configService = ServiceFacade.getConfigService();

        return configService;
    }
}
