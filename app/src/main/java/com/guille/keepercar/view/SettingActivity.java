package com.guille.keepercar.view;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.guille.keepercar.R;
import com.guille.keepercar.data.model.Configuration;
import com.guille.keepercar.logic.Util;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        setToolbar();

        Util.getConfigMap().clear();
        initConfig();
    }

    @Override
    protected void postInitConfig(List<Configuration> configurations) {
        super.postInitConfig(configurations);
        setConfig();

    }

    public void setConfig() {

        String unitVal = Util.getConfig(Configuration.UNIT_KEY).trim();

        if(unitVal==null) return;

        if (unitVal.equalsIgnoreCase("km")) {
            Toast.makeText(this, "Kilometros", Toast.LENGTH_LONG).show();
            RadioButton rb = (RadioButton) findViewById(R.id.rb_km);
            rb.setChecked(true);

        } else if (unitVal.equalsIgnoreCase("milla")) {
            RadioButton rb = (RadioButton) findViewById(R.id.rb_mile);
            rb.setChecked(true);
            Toast.makeText(this, "millas", Toast.LENGTH_LONG).show();
        }
    }

    public void onRadioButtonClicked(View v) {

        boolean checked = ((RadioButton) v).isChecked();
        Configuration c = new Configuration();
        c.setId(Configuration.UNIT_KEY);


        // Check which radio button was clicked
        switch (v.getId()) {

            case R.id.rb_km:
                if (checked) {
                    c.setValue("km");
                    saveConfig(c);
                    Toast.makeText(this, "Kilometros", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.rb_mile:

                if (checked) {
                    c.setValue("milla");
                    saveConfig(c);
                    Toast.makeText(this, "millas", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    private void saveConfig(final Configuration c) {

        getConfigService().addConfig(c,getDeviceId(), new Callback<Configuration>() {
            @Override
            public void success(Configuration configuration, Response response) {
                Util.setConfig(Configuration.UNIT_KEY,c.getValue());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
