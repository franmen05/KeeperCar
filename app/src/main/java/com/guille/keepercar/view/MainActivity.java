package com.guille.keepercar.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.guille.keepercar.R;
import com.guille.keepercar.data.dal.SQLHelper;
import com.guille.keepercar.data.model.MaintenanceType;
import com.guille.keepercar.data.model.User;
import com.guille.keepercar.data.model.Vehicle;
import com.guille.keepercar.data.model.VehicleMaintenceType;
import com.guille.keepercar.data.model.json.MaintenanceTypeSimple;
import com.guille.keepercar.data.model.json.VehicleMaintenanceTypeSimple;
import com.guille.keepercar.data.ws.api.ServiceFacade;
import com.guille.keepercar.data.ws.api.VehicleService;
import com.guille.keepercar.logic.Util;
import com.guille.keepercar.service.UpdateDistanceService;
import com.guille.keepercar.view.adacter.MaintListAdapter;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends BaseActivity {


    private static final String TAG = "MainActivity";

    //ui component
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    /**
     * Lista  de los mantenimientos  agregados  por el usuario.
     */
    private ListView listView;
    /**
     * dialogo para  agregar manteniminetos.
     */
    private AlertDialog dialog;

    private Menu menuCar;

    //adapter

    /**
     * Mantenimientos  agregados  por el usuario.
     */
    private MaintListAdapter maintListAdapter;

    /**
     * Lista  de los mantenimientos  que se muestran en el dialogo  para  agregar tipos de  mantenimientos
     */
    private ArrayAdapter<String> typesAdapter;

    //db
    private SQLHelper sqlHelper;
    private SQLiteDatabase db;
    private BetterSpinner spMaintenceTypes;

    private TreeMap<String, MaintenanceType> maintenanceTypeTM = null;
    private TreeMap<String, Vehicle> vehicleTreeMap = null;
    private Vehicle vechiveSelected;
    private User user;

    // servicios
//    private ApiAdapter<MaintenanceTypeService> maintTypeApiAdapter;
    private boolean loadNow;
    private VehicleService vehicleService = getVehicleService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setMainToolbar();

//        testDB();

        init();
        Toast.makeText(this, getDeviceId(), Toast.LENGTH_LONG).show();

        drawerLayout.openDrawer(GravityCompat.START);

//        UpdateDistanceService us= new UpdateDistanceService();
        if (!UpdateDistanceService.isRunning())
            startService(new Intent(getBaseContext(), UpdateDistanceService.class));
    }

    private void init() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        buildMenu();

        createDBConnection();
        db = getDb();
        sqlHelper = getSqlHelper();
        apiLoaderMaintTypeList();

//        ApiAdapter<VehicleService> vehApiAdapter= new ApiAdapter<>();
//        ((VehicleService)vehApiAdapter.getService(VehicleService.class)).findAll(new Callback<List<Vehicle>>() {
//
//            @Override
//            public void success(List<Vehicle> vehicles, Response response) {
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });

        listView = (ListView) findViewById(R.id.maint_list);
        vechiveSelected = new Vehicle(1);

        loadUser();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(UpdateDistanceService.isRunning())
//            stopService(new Intent(getBaseContext(), UpdateDistanceService.class));
    }

    private void buildMenu() {

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //configuracion menu
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.configuration_section);
        MenuItem config = menuItem.getSubMenu().findItem(R.id.nav_general);
        config.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent myIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(myIntent);
                return true;
            }
        });

        /// acerca menu
        MenuItem about = menuItem.getSubMenu().findItem(R.id.nav_about);
        about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent myIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(myIntent);
                return true;
            }
        });
    }


    /**
     * Carga de usuario
     */
    private void loadUser() {

        if (user == null) {

            final String deviceId = getDeviceId();

            getUserService().findById(deviceId, new Callback<User>() {
                @Override
                public void success(User _user, Response response) {

                    if(_user==null) {
                        createUser(deviceId);
                        return;
                    }

                    Log.d(TAG, String.valueOf(_user));

                    user = _user;
                    Util.setUser(user);

                    List<Vehicle> vehicles = _user.getVehicleCollection();

                    if (vehicles != null) {

                        if (vehicles.size() > 0)
                            createVehicleList(navigationView, vehicles);
//                        else
                           // openAddVeh();
                    }

//                    createMaintTypeList();
                }

                @Override
                public void failure(RetrofitError error) {

//                    createMaintTypeList();
                }
            });
        } else {

            createMaintTypeList();
        }
    }

    private void createUser(String deviceId) {
        User u= new User();
        u.setId(deviceId);
        u.setName(deviceId);

        getUserService().add(u, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                loadUser();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, getString(R.string.ms_error_x), Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * crea  lista de  vehiculos
     *
     * @param view
     * @param vehicles
     */
    private void createVehicleList(NavigationView view, List<Vehicle> vehicles) {

        vehicleTreeMap = new TreeMap<>();

        menuCar = view.getMenu();
        MenuItem menuItem = menuCar.findItem(R.id.my_Car_section);
        SubMenu m = menuItem.getSubMenu();
        m.clear();


        int i = 1;
        for (Vehicle v : vehicles) {

            Log.d(TAG, v.toString());

//            VehicleUtil.updateDistance(v);
            if ("DISABLE".equals(v.getStatus())) continue;

            MenuItem mi = m.add(i + " - " + v.getBrand().getName() + " - " + v.getCarModel()).setIcon(R.drawable.baseline_directions_24);
            vehicleTreeMap.put(i + " - " + v.getBrand().getName() + " - " + v.getCarModel(), v);
            i++;

//            view.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Toast.makeText(MainActivity.this, "long  click", Toast.LENGTH_LONG).show();
//                    return false;
//                }
//            });

            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {


                @Override
                public boolean onMenuItemClick(MenuItem item) {


//                    Toast.makeText(MainActivity.this, vechiveSelected.getBrand().getName(), Toast.LENGTH_LONG).show();
                    vechiveSelected = vehicleTreeMap.get(item.getTitle());
                    createMaintTypeList();
                    updateMainCard(vechiveSelected);

                    drawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }
            });


//            Almacenamientos en la base de datos.
//
//            if (getSqlHelper().getMaintenanceTypeByVehicle(v, getDb()).size() != v.getMaintenanceType().size()) {
//
//                getSqlHelper().deleteMaintenanceTypeByVehicle(v, getDb());
//                Log.i(TAG, v.toString());
//                Log.i(TAG, " getMaintenanceType() : " + v.getMaintenanceType());
//
//                for (MaintenanceType my : v.getMaintenanceType()) {
//
//                    final VehicleMaintenceType type = new VehicleMaintenceType(v, my);
//                    getSqlHelper().saveMaintenanceTypeByVehicle(type, db);
//                }
//            }
//
//            service.add(v, new Callback<String>() {
//                @Override
//                public void success(String s, Response response) {
////                    Log.d(TAG, s);
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    Log.e(TAG, error.toString());
//                }
//            });


//            service.delete(v.getId(), new Callback<String>() {
//                @Override
//                public void success(String s, Response response) {
////                    Log.d(TAG, s);
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    Log.e(TAG, error.toString());
//                }
//            });

        }
        if (loadNow) {

            createMaintTypeList(loadNow);
            loadNow = false;
        }

    }

    private void updateMainCard(Vehicle v) {

        TextView titule = (TextView) findViewById(R.id.txt_type_maint);
        TextView distance = (TextView) findViewById(R.id.txt_distance_actual_value);
        TextView distanceDaily = (TextView) findViewById(R.id.txt_distance_daily_value_card_status);
        titule.setText(v.getBrand().getName());
        distance.setText((String.valueOf(v.getDistanceActual())));

        distanceDaily.setText((String.valueOf(v.getDistanceDaiLy())));

    }


//        private void createMaintTypeList(List<MaintenanceType> maintenanceTypes) {
//
//        //donde  se crea la lista
//        maintListAdapter = new MaintListAdapter(this, getSqlHelper(), vechiveSelected,maintenanceTypes);
//        listView.setAdapter(maintListAdapter);
//
//    }

    private void createMaintTypeList() {

        //donde  se crea la lista
        maintListAdapter = new MaintListAdapter(this, getSqlHelper(), vechiveSelected, vehicleService);
        listView.setAdapter(maintListAdapter);
    }

    private void createMaintTypeList(boolean fromServer) {

        if (fromServer) {

            vehicleService.findById(vechiveSelected.getId(), new Callback<Vehicle>() {
                @Override
                public void success(Vehicle vehicle, Response response) {
                    vechiveSelected = vehicle;
                    createMaintTypeList();
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(MainActivity.this, getString(R.string.ms_error_selected_veh), Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    private void initMaintenanceTypeTM(List<MaintenanceType> types) {

        if (maintenanceTypeTM == null) {
//            this.maintenanceTypeTM = getSqlHelper().getMaintenanceTypeMap(db);
            this.maintenanceTypeTM = new TreeMap<>();
            for (MaintenanceType type : types) {
                maintenanceTypeTM.put(type.getName(), type);

            }

        }
    }

    /**
     * carga los  tipos de  mantemientos desde el servidor
     */
    private void apiLoaderMaintTypeList() {

//        initMaintenanceTypeTM();

//        if (maintTypeApiAdapter == null) {

//            maintTypeApiAdapter = new ApiAdapter<>();
        //lista  de  mantenimientos que apareceran en el ComboBox de  tipos.

        ServiceFacade.getMaintenanceTypeService().findAll(new Callback<List<MaintenanceType>>() {

            @Override
            public void success(List<MaintenanceType> maintenanceType, Response response) {
//
//                            for (MaintenanceType mt : maintenanceType) {
//                                if (maintenanceTypeTM.get(mt.getName()) == null)
//                                    getSqlHelper().saveMaintenanceType(mt, getDb());
//                            }
                initMaintenanceTypeTM(maintenanceType);
                dialog = createMaintDialogo(maintenanceType);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "No se pudo  cargar ", Toast.LENGTH_LONG).show();
            }
        });
//        }
    }


    /**
     * creacion de  dialogo para  agregar  un nuevo tipo de mantenimiento
     *
     * @return
     */
    public AlertDialog createMaintDialogo(List<MaintenanceType> types) {

        initMaintenanceTypeTM(types);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.title_activity_add_maint);
        LayoutInflater inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.activity_add_maint, null);
        spMaintenceTypes = (BetterSpinner) v.findViewById(R.id.sp_type_maint);
//        spMaintenceTypes.setOnItemSelectedListener(new MaintanceTypeAV());
        typesAdapter = new ArrayAdapter<>(this, R.layout.spinner_list_item, getName(types));
        spMaintenceTypes.setAdapter(typesAdapter);

        builder.setView(v);

        Button bSave = (Button) v.findViewById(R.id.b_save);
        bSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String item = spMaintenceTypes.getText().toString();

                        //inicio vaidaciones
                        if (item.isEmpty()) {
                            Toast.makeText(MainActivity.this, getString(R.string.ms_maint_no_selected), Toast.LENGTH_LONG).show();
                            return;
                        }


                        if (maintListAdapter == null) {
                            Toast.makeText(MainActivity.this, getString(R.string.ms_veh_no_selected), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            return;
                        }

                        final VehicleMaintenceType type = new VehicleMaintenceType(vechiveSelected, maintenanceTypeTM.get(item));
                        if (type.getMaintenanceType() == null) {
                            Toast.makeText(MainActivity.this, getString(R.string.ms_maint_type_error), Toast.LENGTH_LONG).show();
                            return;
                        }

                        ///fin validaciones
                        if (!maintListAdapter.exist(type.getMaintenanceType().getName())) {

//                            vechiveSelected.setMaintenanceType(maintListAdapter.getTypeList());
                            VehicleMaintenanceTypeSimple vm = new VehicleMaintenanceTypeSimple(vechiveSelected.getId());
                            List<MaintenanceTypeSimple> t = new ArrayList<>();

                            MaintenanceTypeSimple mts = new MaintenanceTypeSimple(type.getMaintenanceType().getId());
                            mts.setDistance(type.getMaintenanceType().getDistanceToApply());
                            mts.setName(type.getMaintenanceType().getName());
                            t.add(mts);

                            vm.setTypes(t);

                            getVehicleService().addMaintByVeh(vm, new Callback<String>() {
                                @Override
                                public void success(String s, Response response) {

//                                    getSqlHelper().saveMaintenanceTypeByVehicle(type, db);
//                                    createMaintTypeList();
                                    user = null;
                                    loadNow = true;
                                    loadUser();
                                    Toast.makeText(MainActivity.this, getString(R.string.ms_maint_saved), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Toast.makeText(MainActivity.this, getString(R.string.ms_maint_no_saved), Toast.LENGTH_LONG).show();
                                    Log.e(TAG + " save tipo", error.toString());
                                }
                            });


                        } else {
                            Toast.makeText(MainActivity.this, getString(R.string.ms_maint_exist), Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();
                    }
                }
        );

        return builder.create();
    }

    //TODO mejorar esta  validacion
//    private TreeMap<String, MaintenanceType> typeTempTM = null;
    private List<String> getName(List<MaintenanceType> o) {

        List<String> r = new ArrayList<>();
//        List<MaintenanceType> o = getSqlHelper().getMaintenanceType(getDb());
//        initMaintenanceTypeTM(o);

        for (MaintenanceType m : o)
//            if (this.maintenanceTypeTM.get(m.getName()) != null)
            r.add(m.getName());

        return r;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_delete:

                vehicleService.delete(vechiveSelected.getId(), new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        Toast.makeText(MainActivity.this, getString(R.string.ms_veh_delete), Toast.LENGTH_LONG).show();
                        user=null;
                        loadUser();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(MainActivity.this, getString(R.string.ms_error_x), Toast.LENGTH_LONG).show();
                    }
                });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




}