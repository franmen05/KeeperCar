package com.guille.keepercar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.guille.keepercar.R
import com.guille.keepercar.view.adacter.MaintListAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    /**
     * Lista  de los mantenimientos  agregados  por el usuario.
     */
    private lateinit var listView: ListView

    //adapter

    //adapter
    /**
     * Mantenimientos  agregados  por el usuario.
     */
    private lateinit var maintListAdapter: MaintListAdapter


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        homeViewModel =ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel .text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        listView = root.findViewById(R.id.maint_list1)
        return root
    }


    private fun createMaintTypeList() {

//        maintListAdapter = MaintListAdapter(this.context, null, vechiveSelected, vehicleService)
//        listView.adapter = maintListAdapter
    }
}