package com.guille.keepercar.view;

import android.os.Bundle;
import android.widget.TextView;

import com.guille.keepercar.R;
import com.guille.keepercar.data.model.Maintenance;

public class DetailActivity extends BaseActivity {

    private Maintenance m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        setToolbar();


        init();
    }


    private void init() {
        m = (Maintenance) getIntent().getSerializableExtra("maintenance");

        TextView type= (TextView) findViewById(R.id.txt_type_maint);
        TextView cost= (TextView) findViewById(R.id.txt_distance_daily_value);
        TextView date= (TextView) findViewById(R.id.txt_date);
        TextView desc= (TextView) findViewById(R.id.et_description);
//        TextView recommended= (TextView) findViewById(R.id.txt_recommended);
        TextView used= (TextView) findViewById(R.id.txt_used);
        TextView distanceDo= (TextView) findViewById(R.id.txt_current_km);

        type.setText(m.getType().getName());
        cost.setText(" "+m.getCost());
        date.setText(m.getDate());
//        TODO: eliminar esta linea
//        date.setText(Util.formarDate(m.getDate()));
        desc.setText(m.getDesc());
//        recommended.setText(m.getRecommended());
        used.setText(m.getUsed());
        distanceDo.setText(""+m.getDistanceDo());

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_destail, menu);
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
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
