package com.guille.keepercar.view.adacter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.guille.keepercar.data.dal.SQLHelper;
import com.guille.keepercar.data.model.Maintenance;
import com.guille.keepercar.data.model.MaintenanceType;
import com.guille.keepercar.data.model.json.MaintenanceSimple;
import com.guille.keepercar.data.ws.api.MaintenanceService;
import com.guille.keepercar.view.DetailActivity;
import com.guille.keepercar.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Guille on 08/30/2015.
 */
public class MaintByTypeListAdapter extends BaseCustomAdapter {

//    private final MaintenanceType type;
    private LayoutInflater mInflater;
    private List<Maintenance> list;
    private MaintenanceType type;

    //service
    private MaintenanceService service;

    public MaintByTypeListAdapter(Context context,SQLHelper sqlHelper,MaintenanceType type,MaintenanceService service,List<Maintenance> list){
        super(context,sqlHelper);
        this.service= service;
        this.list=list;

        mInflater= LayoutInflater.from(context);
//        list =getSqlHelper().getMaintenanceByType(type, getDb());
        this.type=type;
    }

    /**
     * Indica  si un mantemientos ya fue agregado
     * @param date
     * @return
     */
    public boolean  exist( String date ){

        //TODO hacer esto de forma mas  eficiente
        for (Maintenance m :list) {

            if(m.getDate().equals(date)) return true;
        }
        return  false;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Maintenance m=list.get(i);
        m.setType(type);
        final  MaintViewHolder holder= new MaintViewHolder();

        view=  mInflater.inflate(R.layout.maint_by_card,null);
        view.setClickable(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetailActivity(m);
            }
        });


        holder.cost= (TextView) view.findViewById(R.id.txt_distance_daily_value);
        holder.used = (TextView) view.findViewById(R.id.txt_date);
        holder.type= (TextView) view.findViewById(R.id.txt_type_maint);


        holder.add= (Button) view.findViewById(R.id.b_add);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetailActivity(m);
            }
        });
//        holder.add.setVisibility(View.INVISIBLE);

        holder.recordCount= (Button) view.findViewById(R.id.b_count);
        holder.recordCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetailActivity(m);
            }
        });

        //set
        holder.recordCount.setText(" "+ m.getId());
        holder.used.setText(m.getUsed());
        holder.cost.setText("$"+String.valueOf(m.getCost()));
        holder.type.setText(m.getDate());
//        holder.type.setText(m.getDate()+" -  $"+ m.getCost());
        holder.delete= (Button) view.findViewById(R.id.b_delete_by);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(m);
            }
        });

        view.setTag(holder);
        return view;
    }

    private void delete(Maintenance m) {

//        getSqlHelper().deleteMaintenance(m, getDb());
        service.delete(m.getId(), new Callback<String>() {
            @Override
            public void success(String s, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        list.remove(m);
        notifyDataSetChanged();


    }

    private void goToDetailActivity(Maintenance m){
        Intent myIntent = new Intent(getContext(), DetailActivity.class);
        myIntent.putExtra("maintenance", m);
        getContext().startActivity(myIntent);
    }


    private void updateMaintenance(MaintenanceSimple maintenanceSimple) {

        service.update(maintenanceSimple, new Callback<String>() {
            @Override
            public void success(String s, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


}
