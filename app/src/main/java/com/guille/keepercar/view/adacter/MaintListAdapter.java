package com.guille.keepercar.view.adacter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.guille.keepercar.R;
import com.guille.keepercar.data.dal.SQLHelper;
import com.guille.keepercar.data.model.MaintenanceType;
import com.guille.keepercar.data.model.Vehicle;
import com.guille.keepercar.data.model.json.MaintenanceTypeSimple;
import com.guille.keepercar.data.model.json.VehicleMaintenanceTypeSimple;
import com.guille.keepercar.data.ws.api.MaintenanceTypeService;
import com.guille.keepercar.data.ws.api.ServiceFacade;
import com.guille.keepercar.data.ws.api.VehicleService;
import com.guille.keepercar.view.MaintByTypeActivity;


import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.TreeMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Guille on 08/30/2015.
 */
public class MaintListAdapter extends BaseCustomAdapter {

    private final String TAG = "MaintListAdapter";

    private LayoutInflater mInflater;
    private List<MaintenanceType> typeList;
    private TreeMap<String, MaintenanceType> typesTM= null;
    private  Vehicle vehicle;
    private VehicleService vehicleService;
    private final MaintenanceTypeService typeService = ServiceFacade.getMaintenanceTypeService();

    public MaintListAdapter(Context context,SQLHelper sqlHelper,Vehicle v,VehicleService service){
        super(context, sqlHelper);

        typesTM = new TreeMap<>();
        mInflater= LayoutInflater.from(context);
        this.vehicleService=service;
//        typeList = getSqlHelper().getMaintenanceTypeByVehicle(v, getDb());
        typeList = v.getMaintenanceType();

        for(MaintenanceType mt: typeList ){
//            typeService.fin
            typesTM.put(mt.getName(),mt);

        }


        Log.d(TAG,typeList.toString());
        vehicle=v;
    }




//    /**
//     * @param context
//     * @param sqlHelper
//     * @param v
//     * @param maintenanceTypes
//     */
//    public MaintListAdapter(Context context,SQLHelper sqlHelper,Vehicle v,List<MaintenanceType> maintenanceTypes){
//        super(context, sqlHelper);
//
//        typesTM = new TreeMap<>();
//        mInflater= LayoutInflater.from(context);
//        typeList = maintenanceTypes;
//
//        for(MaintenanceType mt: typeList )
//            typesTM.put(mt.getName(),mt);
//
//        Log.d(TAG,typeList.toString());
//        vehicle=v;
//    }

    public List<MaintenanceType> getTypeList() {
        return typeList;
    }

    public boolean exist(String name){

        if(typesTM.get(name)==null) return false;

        return true;
    }
    @Override
    public int getCount() {
        return typeList.size();
    }

    @Override
    public Object getItem(int i) {
        return typeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final  MaintViewHolder holder= new MaintViewHolder();

        view=  mInflater.inflate(R.layout.maint_card,null);
        final MaintenanceType type = typeList.get(i);
        final int index=i;


        Log.d(TAG,type.toString());

        holder.cost= (TextView) view.findViewById(R.id.txt_distance_daily_value);
        holder.type= (TextView) view.findViewById(R.id.txt_type_maint);
        holder.used = (TextView) view.findViewById(R.id.txt_date);
        holder.add= (Button) view.findViewById(R.id.b_add);
        holder.delete= (Button) view.findViewById(R.id.b_delete);


        holder.type.setText(type.getName().toUpperCase()+"( "+new Formatter().format("%,d",  type.getDistanceToApply())+" KMs )");


        holder.recordCount= (Button) view.findViewById(R.id.b_count);
        holder.recordCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaintByTypeActivity(type,false);
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaintByTypeActivity(type, true);
            }
        });

        //delete
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<MaintenanceTypeSimple> l= new ArrayList<>();
                l.add(typeList.get(index).toMaintenanceTypeSimple());

                vehicleService.deleteMaintByVeh(new VehicleMaintenanceTypeSimple(vehicle.getId(), l), new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                typeList.remove(index);
                notifyDataSetChanged();

            }
        });

        int size=getSqlHelper().getCountMaintenanceByType(type,getDb());
        holder.recordCount.setText(String.valueOf(size));

        view.setTag(holder);
        return view;
    }

    private void openMaintByTypeActivity(MaintenanceType type,boolean addNew){

        Intent myIntent = new Intent(getContext(), MaintByTypeActivity.class);
        myIntent.putExtra("type", type);
        myIntent.putExtra("vehicle",vehicle);
        myIntent.putExtra("addNew",addNew);

        getContext().startActivity(myIntent);

    }

    public MaintenanceType getType(String d) {
        return typesTM.get(d);

    }
}
