package com.guille.keepercar.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.guille.keepercar.R;
import com.guille.keepercar.data.model.Brand;
import com.guille.keepercar.data.model.Configuration;
import com.guille.keepercar.data.model.User;
import com.guille.keepercar.data.model.Vehicle;
import com.guille.keepercar.data.model.json.UserVehSimple;
import com.guille.keepercar.data.ws.ApiAdapter;
import com.guille.keepercar.data.ws.api.BrandService;
import com.guille.keepercar.data.ws.api.VehicleService;
import com.guille.keepercar.logic.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddVehActivity extends BaseActivity {

    private static final String TAG = "AddVehActivity";

    //ui
    private Spinner spBrand,
            spYears;

    private EditText etCurrentKms,
            etDailyKms,
            etCarModel;

    private Button bSave;

    private RadioButton radioButto;

    private ArrayAdapter<String> brandsAdapter,
            yearsAdapter;

    //member
    private User user;


    private ApiAdapter<VehicleService> vehicleApiAdapter;
    private VehicleService service;
    private HashMap<String, Brand> brandMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_veh);
        setToolbar();

        init();
    }


    protected void init() {

        String unitVal = Util.getConfig(Configuration.UNIT_KEY);
        radioButto= (RadioButton) findViewById(R.id.rb_km);
        radioButto.setText(unitVal);

        createDBConnection();

        user = Util.getUser();


        bSave = (Button) findViewById(R.id.button_save);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked(v);
            }
        });

        spYears = (Spinner) findViewById(R.id.sp_years);
        fillYear();

        etCurrentKms = (EditText) findViewById(R.id.et_current_kms);
        etDailyKms = (EditText) findViewById(R.id.et_daily_kms);
        etCarModel = (EditText) findViewById(R.id.et_model);

        brandMap= new HashMap<>();
        initBrand();

        vehicleApiAdapter = new ApiAdapter<>();
        service = ((VehicleService) vehicleApiAdapter.getService(VehicleService.class));

    }

    @Override
    protected void setToolbar() {
        super.setToolbar();
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        close();
    }

    //carga las  marcas
    private void initBrand() {
        if(spBrand==null)
            spBrand = (Spinner) findViewById(R.id.sp_brands);

//        ApiAdapter.getBrandService().findAll(this);
        ApiAdapter<BrandService> adapter = new ApiAdapter<>();
        ((BrandService) adapter.getService(BrandService.class)).findAll(new Callback<List<Brand>>() {
            @Override
            public void success(List<Brand> brands, Response response) {
                fillBrand(brands);
//                if(brands.size()<=getSqlHelper().getBrands(getDb()).size()) return ;
//
//                getSqlHelper().deleteAllBrands(getDb());
////
////                for(Brand b: brands ){
////                    Log.d(TAG, "Save:  " + brands.toString());
////                    getSqlHelper().saveBrand(b,getDb());
////
////                }
            }

            @Override
            public void failure(RetrofitError error) {

                Log.d(TAG, error.toString());

            }
        });
    }


    private void fillBrand(List<Brand> brands) {


        brandsAdapter = new ArrayAdapter<>(this, R.layout.spinner_list_item, getBrandsName(brands));

        spBrand.setAdapter(brandsAdapter);
    }

    private List<String> getBrandsName(List<Brand> brands) {

        List<String> r = new ArrayList<>();
//        List<Brand> list= getSqlHelper().getBrands(getDb());
        for (Brand brand : brands) {
            brandMap.put(brand.getName(), brand);
            r.add(brand.getName());
        }
        return r;
    }

    private void fillYear() {

        yearsAdapter = new ArrayAdapter<String>(this, R.layout.spinner_list_item, getYearsName());
        spYears.setAdapter(yearsAdapter);
    }

    private List<String> getYearsName() {

        List<String> r = new ArrayList<>();

        for (int i = Calendar.getInstance().get(Calendar.YEAR); i >= 1990; i--)
            r.add(String.valueOf(i));

        return r;
    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_add_veh, menu);
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
//
//        return super.onOptionsItemSelected(item);
//    }

    //event

    public void onSaveButtonClicked(View v) {


        String dailyKmsVal = etDailyKms.getText().toString();
        String carModelValue= etCarModel.getText().toString();
        String yearValue = spYears.getSelectedItem().toString();

        if(dailyKmsVal.isEmpty() || yearValue.isEmpty()) {
            Toast.makeText(this,"no debe dejar campos vacios",Toast.LENGTH_LONG).show();
            return;
        }
        int dailyKms = Integer.parseInt(dailyKmsVal);

        int year = Integer.parseInt(yearValue);

        String currentKms = String.valueOf(etCurrentKms.getText());

//        Brand brand = getSqlHelper().getBrandByName(spBrand.getSelectedItem().toString(), getDb());
        Brand brand = brandMap.get(spBrand.getSelectedItem());

        if(brand==null){
            Toast.makeText(AddVehActivity.this, getString(R.string.veh_add_error), Toast.LENGTH_SHORT).show();
            return;
        }

        final Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setDistanceActual(Long.parseLong(currentKms));
        vehicle.setDistanceDaiLy(dailyKms);
        vehicle.setYear(year);
        vehicle.setCarModel(carModelValue);


        service.add(vehicle, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Toast.makeText(AddVehActivity.this, "Vehiculo Guardado - ID: " + s, Toast.LENGTH_SHORT).show();

                getUserService().addVehicle(new UserVehSimple(user.getId(), Integer.parseInt(s)), new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        Toast.makeText(AddVehActivity.this, "Vehiculo Guardado ", Toast.LENGTH_LONG).show();
                        close();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(AddVehActivity.this, getString(R.string.veh_add_error), Toast.LENGTH_LONG).show();
                    }
                });
//                getSqlHelper().saveVehicle(vehicle, getDb());

            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(AddVehActivity.this, "No se puedo Guardar el Vehiculo ", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void close() {
        Intent myIntent = new Intent( AddVehActivity.this,MainActivity.class);
        startActivity(myIntent);
        finish();
    }


    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();

//        Toast.makeText(AddVehActivity.this, "Kilometros", Toast.LENGTH_LONG).show();

        // Check which radio button was clicked
        switch (v.getId()) {

            case R.id.rb_km:
                if (checked)
                    Toast.makeText(AddVehActivity.this, "Para cambiar debe ir a configuracion > Generales", Toast.LENGTH_LONG).show();
                break;

//            case R.id.rb_mile:
//                if (checked)
//                    Toast.makeText(AddVehActivity.this, "millas", Toast.LENGTH_LONG).show();
//                break;
        }

    }

}
