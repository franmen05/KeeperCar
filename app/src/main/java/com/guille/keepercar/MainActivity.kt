package com.guille.keepercar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.guille.keepercar.data.dal.SQLHelper
import com.guille.keepercar.data.model.MaintenanceType
import com.guille.keepercar.data.model.User
import com.guille.keepercar.data.model.Vehicle
import com.guille.keepercar.data.model.VehicleMaintenceType
import com.guille.keepercar.data.model.json.MaintenanceTypeSimple
import com.guille.keepercar.data.model.json.VehicleMaintenanceTypeSimple
import com.guille.keepercar.data.ws.api.ServiceFacade
import com.guille.keepercar.view.AboutActivity
import com.guille.keepercar.view.AddVehActivity
import com.guille.keepercar.view.BaseActivity
import com.guille.keepercar.view.SettingActivity
import com.guille.keepercar.view.adacter.MaintListAdapter
import com.weiwangcn.betterspinner.library.BetterSpinner
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import java.util.*

class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"

    private lateinit var appBarConfiguration: AppBarConfiguration



    //ui component
    private val drawerLayout: DrawerLayout? = null
    private val navigationView: NavigationView? = null
    /**
     * dialogo para  agregar manteniminetos.
     */
    private var dialog: AlertDialog? = null
    private lateinit var spMaintenceTypes: BetterSpinner

    /**
     * Lista  de los mantenimientos  agregados  por el usuario.
     */
    private val listView: ListView? = null


    //adapter

    //adapter
    /**
     * Mantenimientos  agregados  por el usuario.
     */
    private val maintListAdapter: MaintListAdapter? = null

    /**
     * Lista  de los mantenimientos  que se muestran en el dialogo  para  agregar tipos de  mantenimientos
     */
    private var typesAdapter: ArrayAdapter<String>? = null

    //db
    private val sqlHelper: SQLHelper? = null
//    private val db: SQLiteDatabase? = null

    private var maintenanceTypeTM: TreeMap<String, MaintenanceType>? = null
    private val vehicleTreeMap: TreeMap<String, Vehicle>? = null
    private val vechiveSelected: Vehicle? = null
    private var user: User? = null


    // servicios
    //    private ApiAdapter<MaintenanceTypeService> maintTypeApiAdapter;
    private var loadNow = false
//    private val vehicleService = getVehicleService()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

        setMainToolbar()

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        onInit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private fun onInit(){
        buildNavMenu()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun buildNavMenu() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        navView = findViewById<View>(R.id.nav_view) as NavigationView

        //configuracion menu
        val menuItem: MenuItem =navView.getMenu().findItem(R.id.configuration_section)
        val config = menuItem.subMenu.findItem(R.id.nav_general)
        config.setOnMenuItemClickListener {
            val myIntent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(myIntent)
            true
        }

        /// acerca menu
        val about = menuItem.subMenu.findItem(R.id.nav_about)
        about.setOnMenuItemClickListener {
            val myIntent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(myIntent)
            true
        }
    }
    private fun initMaintenanceTypeTM(types: List<MaintenanceType>) {
        if (maintenanceTypeTM == null) {
//            this.maintenanceTypeTM = getSqlHelper().getMaintenanceTypeMap(db);
            this.maintenanceTypeTM = TreeMap()
            for (type in types) {
                maintenanceTypeTM!!.put(type.name, type)
            }
        }
    }

    /**
     * carga los  tipos de  mantemientos desde el servidor
     */
    private fun apiLoaderMaintTypeList() {

//        initMaintenanceTypeTM();

//        if (maintTypeApiAdapter == null) {

//            maintTypeApiAdapter = new ApiAdapter<>();
        //lista  de  mantenimientos que apareceran en el ComboBox de  tipos.
        ServiceFacade.getMaintenanceTypeService().findAll(object : Callback<List<MaintenanceType>> {

            override fun success(maintenanceType: List<MaintenanceType>, response: Response) {
//
//                            for (MaintenanceType mt : maintenanceType) {
//                                if (maintenanceTypeTM.get(mt.getName()) == null)
//                                    getSqlHelper().saveMaintenanceType(mt, getDb());
//                            }
                initMaintenanceTypeTM(maintenanceType)
                dialog = createMaintDialogo(maintenanceType)
            }

            override fun failure(error: RetrofitError) {
                Toast.makeText(this@MainActivity, "No se pudo  cargar ", Toast.LENGTH_LONG).show()
            }
        })
//        }
    }

    /**
     * creacion de  dialogo para  agregar  un nuevo tipo de mantenimiento
     *
     * @return
     */
    fun createMaintDialogo(types: List<MaintenanceType>): AlertDialog? {
        initMaintenanceTypeTM(types)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.title_activity_add_maint)
        val inflater = layoutInflater
        val v = inflater.inflate(R.layout.activity_add_maint, null)
        spMaintenceTypes = v.findViewById<View>(R.id.sp_type_maint) as BetterSpinner
        //        spMaintenceTypes.setOnItemSelectedListener(new MaintanceTypeAV());
        typesAdapter = ArrayAdapter<String>(this, R.layout.spinner_list_item, getName(types))
        spMaintenceTypes.setAdapter(typesAdapter)
        builder.setView(v)
        val bSave = v.findViewById<View>(R.id.b_save) as Button
        bSave.setOnClickListener(
            View.OnClickListener {
                val item: String = spMaintenceTypes.getText().toString()

                //inicio vaidaciones
                if (item.isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.ms_maint_no_selected),
                        Toast.LENGTH_LONG
                    ).show()
                    return@OnClickListener
                }
                if (maintListAdapter == null) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.ms_veh_no_selected),
                        Toast.LENGTH_LONG
                    ).show()
                    dialog?.dismiss()
                    return@OnClickListener
                }
                val type =
                    VehicleMaintenceType(vechiveSelected, maintenanceTypeTM!![item])
                if (type.maintenanceType == null) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.ms_maint_type_error),
                        Toast.LENGTH_LONG
                    ).show()
                    return@OnClickListener
                }

                ///fin validaciones
                if (!maintListAdapter.exist(type.maintenanceType.name)) {

                    //                            vechiveSelected.setMaintenanceType(maintListAdapter.getTypeList());
                    val vm = VehicleMaintenanceTypeSimple(vechiveSelected!!.id)
                    val t: MutableList<MaintenanceTypeSimple> = ArrayList()
                    val mts = MaintenanceTypeSimple(type.maintenanceType.id)
                    mts.distance = type.maintenanceType.distanceToApply
                    mts.name = type.maintenanceType.name
                    t.add(mts)
                    vm.types = t
                    vehicleService.addMaintByVeh(vm, object : Callback<String?> {
                        override fun success(s: String?, response: Response) {

                            //                                    getSqlHelper().saveMaintenanceTypeByVehicle(type, db);
                            //                                    createMaintTypeList();
                            user = null
                            loadNow = true
//                                loadUser()
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.ms_maint_saved),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun failure(error: RetrofitError) {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.ms_maint_no_saved),
                                Toast.LENGTH_LONG
                            ).show()
                            Log.e("$TAG save tipo", error.toString())
                        }
                    })
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.ms_maint_exist),
                        Toast.LENGTH_LONG
                    ).show()
                }
                dialog?.dismiss()
            }
        )
        return builder.create()
    }

    //TODO mejorar esta  validacion
    //    private TreeMap<String, MaintenanceType> typeTempTM = null;
    private fun getName(o: List<MaintenanceType>): List<String> {
        val r: MutableList<String> = ArrayList()
        //        List<MaintenanceType> o = getSqlHelper().getMaintenanceType(getDb());
//        initMaintenanceTypeTM(o);
        for (m in o)  //            if (this.maintenanceTypeTM.get(m.getName()) != null)
            r.add(m.name)
        return r
    }

    //event
    fun onClickAddMaintButton(v: View?) {
        if (dialog == null) Toast.makeText(this, "No se pudo  cargar ", Toast.LENGTH_LONG)
            .show() else dialog!!.show()
    }

    fun onClickAddVehicle(v: View?) {
        openAddVeh()
    }

    private fun openAddVeh() {
        val myIntent = Intent(this@MainActivity, AddVehActivity::class.java)
        myIntent.putExtra("user", user)
        startActivity(myIntent)
        finish()
    }

}