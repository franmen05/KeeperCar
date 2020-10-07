package com.guille.keepercar.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.guille.keepercar.R;
import com.guille.keepercar.data.model.Maintenance;
import com.guille.keepercar.data.model.MaintenanceType;
import com.guille.keepercar.data.model.Vehicle;
import com.guille.keepercar.data.ws.api.MaintenanceService;
import com.guille.keepercar.data.ws.api.ServiceFacade;
import com.guille.keepercar.logic.Util;
import com.guille.keepercar.view.adacter.MaintByTypeListAdapter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public  class MaintByTypeActivity extends BaseActivity {

    private static final String TAG="MaintByTypeActivity";

    private Vehicle vehicle;
    private MaintenanceType type;
    private boolean addNew;
    private Calendar calendar = Calendar.getInstance();
    private Date selectedDate;

    //ui component
    private ListView listView;
    private AlertDialog dialog;
    private EditText cost;
    private EditText desc;
    private EditText date;
    private EditText used;
    private EditText recommended;
    private EditText distanceActual;
    private EditText distanceDo;
    private Button bSave;

    //maintenanceService
    private MaintenanceService maintenanceService;

    //adapter
    private MaintByTypeListAdapter mMaintListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maint_by_type);
//        setToolbar();

        init();
    }


    private void init() {

        vehicle= (Vehicle) getIntent().getSerializableExtra("vehicle");
        type= (MaintenanceType) getIntent().getSerializableExtra("type");
        addNew = getIntent().getBooleanExtra("addNew", false);

        listView = (ListView) findViewById(R.id.maint_list);
        maintenanceService = ServiceFacade.getMaintenanceService();

        createMaintList();

        dialog= createMaintDialogo();

        if(addNew) dialog.show();

    }

    private void createMaintList() {


        maintenanceService.find(type.getId(), vehicle.getId(), new Callback<List<Maintenance>>() {

            @Override
            public void success(List<Maintenance> maintenances, Response response) {

                mMaintListAdapter = new MaintByTypeListAdapter(MaintByTypeActivity.this, getSqlHelper(), type,
                        maintenanceService,maintenances);

                listView.setAdapter(mMaintListAdapter);
//                Maintenance maintenance = null;
//                for (Maintenance m : maintenances) {
//                    Util.LOG_D(this, m.toString());
//                    //  getSqlHelper().saveMaintenance(m,getDb());
////                    if (m.getType().getId() == 1)
////                        maintenance = m;
//                }
//          para actualizar
//        maintenance.setDesc("Romo conio");
//        maintenance.setType(new MaintenanceType(1));
//        updateMaintenance(maintenance.toMaintenanceSimple());
            }


            @Override
            public void failure(RetrofitError error) {
                Util.LOG_D(this, error.toString());
            }
        });
    }


    public AlertDialog createMaintDialogo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View v = inflater.inflate(R.layout.activity_add_maint_by_type, null);
        builder.setView(v);

        cost= (EditText) v.findViewById(R.id.et_cost);
        date= (EditText) v.findViewById(R.id.et_date);
        desc= (EditText) v.findViewById(R.id.et_description);
        used= (EditText) v.findViewById(R.id.et_used);
//        recommended= (EditText) v.findViewById(R.id.et_recommended);
        distanceActual= (EditText) v.findViewById(R.id.et_distance_actual);
        distanceActual.setText(String.valueOf(vehicle.getDistanceActual()));

        bSave = (Button) v.findViewById(R.id.b_save);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSaveMaintenance(v);
            }
        });


        calendar= Calendar.getInstance();
        updateDateLabel();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final  DatePickerDialog.OnDateSetListener da = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }

        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog dp = new DatePickerDialog(MaintByTypeActivity.this, da, year, month, day);
                dp.show();
            }
        });

        return builder.create();
    }

    private void updateDateLabel() {
        date.setText(Util.formarDate(calendar));
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_maint_by_type, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings)
//            return true;
//
//        return super.onOptionsItemSelected(item);
//    }

    //Eventos

    public void onClickFlotingButton(View v){
        dialog.show();
    }

    public void  onClickSaveMaintenance(View v){

        final Maintenance maintenance = new Maintenance();

        String costVal = String.valueOf(cost.getText());
        String userVal = used.getText().toString();

        if(costVal.isEmpty() || userVal.isEmpty()) {
            Toast.makeText(this,getString(R.string.ms_maint_field_empty),Toast.LENGTH_LONG).show();
            return;
        }
        maintenance.setType(type);
        maintenance.setVehicle(vehicle);
        maintenance.setDesc(String.valueOf(desc.getText()));
        Date date = null;
        try {
            date = Util.getDate(this.date.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(this,getString(R.string.ms_error_x),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        maintenance.setDate(Util.formarDateForApi(date));
        maintenance.setUsed(used.getText().toString());
//        maintenance.setRecommended(recommended.getText().toString());
        maintenance.setCost(Double.parseDouble(costVal));
        maintenance.setDistanceDo(Long.valueOf(distanceActual.getText().toString()));


        if(mMaintListAdapter.exist(maintenance.getDate())){
            Toast.makeText(this,getString(R.string.ms_maint_exist),Toast.LENGTH_LONG).show();

        }else{

            maintenanceService.add(maintenance.toMaintenanceSimple(), new Callback<String>() {
                @Override
                public void success(String s, Response response) {
//                    getSqlHelper().saveMaintenance(maintenance, getDb());
                    createMaintList();

                }

                @Override
                public void failure(RetrofitError error) {
                    Util.LOG_D(this, error.getMessage());
                }
            });
        }

        //valida si se actualizó la distancia.
        String distanceActualText = distanceActual.getText().toString();
        if(!distanceActualText.equals(vehicle.getDistanceActual())){

            //actualiza  kilometraje si es necesario
            vehicle.setDistanceActual(Long.parseLong(distanceActualText));
            getVehicleService().add(vehicle, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
//                    Util.LOG_D(this, "XD se Act vehiculo");
                    Log.d(TAG, "XD se Act vehiculo");
                }

                @Override
                public void failure(RetrofitError error) {

                    Log.d(TAG, error.getMessage());
//                    Util.LOG_D(this, );
                }
            });
        }

        dialog.dismiss();
    }


}
