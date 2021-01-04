package com.sizdev.arkhirefortalent.homepage.item.company.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sizdev.arkhirefortalent.R
import com.sizdev.arkhirefortalent.databinding.ActivityCompanyProfileBinding
import com.sizdev.arkhirefortalent.webviewer.ArkhireWebViewerActivity
import com.squareup.picasso.Picasso

class CompanyProfileActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityCompanyProfileBinding
    private var defaultLocation = LatLng(-6.200000, 106.816666)
    private lateinit var markerDefault: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_profile)

        val companyName = intent.getStringExtra("companyName")
        val companyType = intent.getStringExtra("companyType")
        val companyImage = intent.getStringExtra("companyImage")
        val companyLinkedin = intent.getStringExtra("companyLinkedin")
        val companyInstagram = intent.getStringExtra("companyInstagram")
        val companyFacebook = intent.getStringExtra("companyFacebook")
        val companyDesc = intent.getStringExtra("companyDesc")
        val companyLatitude = intent.getStringExtra("companyLatitude")
        val companyLongitude = intent.getStringExtra("companyLongitude")

        if (companyLatitude != "null" && companyLongitude != "null") {
            if (companyLatitude != null && companyLongitude != null) {
                defaultLocation = LatLng(companyLatitude.toDouble(), companyLongitude.toDouble())
            }
        }

        binding.rvCompanyLookingFor.adapter = CompanyLookingForAdapter()
        binding.rvCompanyLookingFor.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        //Set Data
        binding.tvCompanyProfileName.text = companyName
        binding.tvCompanyType.text = companyType
        binding.tvCompanyDescription.text = companyDesc

        when (companyType){
            "Enterprise" -> binding.ivCompanyProfileCover.setImageResource(R.drawable.ic_enterprise)
            "Startup" -> binding.ivCompanyProfileCover.setImageResource(R.drawable.ic_startup)
            else -> binding.ivCompanyProfileCover.setImageResource(R.drawable.ic_software_house)
        }

        //Set Profile Images
        if (companyImage == "null"){
            binding.ivCompanyProfileImage.setImageResource(R.drawable.ic_empty_image)
        }
        else {
            Picasso.get()
                    .load("http://54.82.81.23:911/image/$companyImage")
                    .resize(512, 512)
                    .centerCrop()
                    .into(binding.ivCompanyProfileImage)
        }


        binding.ivCompanyLinkedIn.setOnClickListener {
            if(companyLinkedin == "null"){
                Toast.makeText(this, "This company not publish that info", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, ArkhireWebViewerActivity::class.java)
                intent.putExtra("url", companyLinkedin)
                startActivity(intent)
            }

        }

        binding.ivCompanyInstagram.setOnClickListener {
            if (companyInstagram == "null"){
                Toast.makeText(this, "This company not publish that info", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, ArkhireWebViewerActivity::class.java)
                intent.putExtra("url", companyInstagram)
                startActivity(intent)
            }
        }

        binding.ivCompanyFacebook.setOnClickListener {
            if (companyFacebook == "null"){
                Toast.makeText(this, "This company not publish that info", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, ArkhireWebViewerActivity::class.java)
                intent.putExtra("url", companyFacebook)
                startActivity(intent)
            }
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.menuButton.setOnClickListener {
            val showMenu = PopupMenu(this, binding.menuButton)
            showMenu.menu.add(Menu.NONE, 0 ,0, "Save Favorite")
            showMenu.menu.add(Menu.NONE, 1 ,1, "Report")
            showMenu.show()

            showMenu.setOnMenuItemClickListener { menuItem ->
                val id = menuItem.itemId

                when (id) {
                    0 -> {Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()}
                    1 -> {Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()}
                }
                false
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN

        markerDefault = googleMap.addMarker(
            MarkerOptions()
                .position(defaultLocation)
                .title(binding.tvCompanyProfileName.text as String?)
        )

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12f))
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Toast.makeText(this, "Navigate to ${marker.title} ?", Toast.LENGTH_SHORT).show()
        return false
    }
}