package com.example.farmconn

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.farmconn.databinding.ActivityMapsBinding
import android.location.Geocoder
import com.google.android.gms.maps.model.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)




    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Sets the map type to be "hybrid"
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        //my location



        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(baza,15F),500,null)

        var db=DatabaseHandler(this)
        // Add a marker in Sydney and move the camera
        var bazaLongLatLng = LatLng(0.0, 0.0)
        var tile=""
        var baza= HelperUser.getCurrentUser()?.let { db.getOneFarmByID(it.idFarm) }
        if(baza!=null){
            bazaLongLatLng= LatLng(baza.xCord,baza.yCord)
            tile=baza.nameFarm

        }


        mMap.addMarker(MarkerOptions().position(bazaLongLatLng).title(tile)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.home_house_thiago)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bazaLongLatLng, 15f))

        var machineList= HelperUser.getCurrentUser()?.let { db.viewMachineOnFarmList(it.idFarm) }
        var listField= HelperUser.getCurrentUser()?.let { db.viewFieldsFarmList(it.idFarm) }
        if(machineList!=null){
            for (item in machineList){
                mMap.addMarker(MarkerOptions().position(LatLng(item.xCords,item.yCords)).title(item.modelMachine)
                    .snippet(item.typeMachine).icon(BitmapDescriptorFactory.fromResource(R.drawable.tractor_vehicle_icon)))
                Log.d("dodało marker", item.modelMachine)
            }
        }
        if(listField!=null){
            for (item in listField){
                mMap.addMarker(MarkerOptions().position(LatLng(item.xField,item.yField)).title(item.nameField)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.circle_map_marker_pin_icon)))
                Log.d("dodało marker", item.nameField )

            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true




    }







}
/*

*/

