package com.example.mindguard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.widget.Button
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class EmergencyActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val wroclaw = LatLng(51.107883, 17.028957)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(wroclaw, 13f))

        addMarker(LatLng(51.113583, 17.023783), "MOPS - Filia Fabryczna", "ul. Fabryczna 12",BitmapDescriptorFactory.HUE_GREEN)
        addMarker(LatLng(51.098765, 17.034321), "Centrum Interwencji Kryzysowej", "ul. Kryzysowa 5", BitmapDescriptorFactory.HUE_BLUE)
        addMarker(LatLng(51.105432, 17.012765), "Punkt Pomocy Społecznej 'Nadodrze'", "ul. Nadodrzańska 20", BitmapDescriptorFactory.HUE_RED)
    }


    private fun addMarker(latLng: LatLng, title: String, snippet: String, color: Float) {
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.defaultMarker(color))
        )
    }
}