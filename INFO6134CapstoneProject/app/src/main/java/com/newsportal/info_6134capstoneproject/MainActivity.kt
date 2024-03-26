package com.newsportal.info_6134capstoneproject

import HomeFragment
import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.newsportal.info_6134capstoneproject.repository.LocationProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.newsportal.info_6134capstoneproject.data.FetchWeather
import com.newsportal.info_6134capstoneproject.data.WeatherFetchListener
import com.newsportal.info_6134capstoneproject.databinding.ActivityMainBinding
import com.newsportal.info_6134capstoneproject.ui.fragments.bookmarkfragment.BookmarkFragment
import com.newsportal.info_6134capstoneproject.ui.fragments.SearchFragment
import com.newsportal.info_6134capstoneproject.ui.fragments.SettingsFragment
class MainActivity : AppCompatActivity(), WeatherFetchListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var currentLocationTextView: TextView
    private lateinit var degreeTextView: TextView
    private lateinit var weatherConditionTextView: TextView
    private lateinit var weatherConditionImage: ImageView

    private lateinit var weatherFetcher: FetchWeather
    private lateinit var locationProvider: LocationProvider
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVars()
        initBottomNavigation()
    }
    private fun initVars() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
    }
    private fun initBottomNavigation() {
        val homeFragment = HomeFragment()
        val bookmarkFragment = BookmarkFragment()
        val searchFragment = SearchFragment()
        val settingsFragment = SettingsFragment()

        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->setCurrentFragment(homeFragment)
                R.id.bookmark ->setCurrentFragment(bookmarkFragment)
                R.id.search ->setCurrentFragment(searchFragment)
                R.id.settings ->setCurrentFragment(settingsFragment)
            }
            true
        }

        currentLocationTextView = findViewById(R.id.currentLocation)
        degreeTextView = findViewById(R.id.degree)
        weatherConditionTextView = findViewById(R.id.currentWeather)
        weatherConditionImage = findViewById(R.id.weatherConditionImg)

        initializeWeather()
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    override fun onStart() {
        super.onStart()
    }
    override fun onResume() {
        super.onResume()
    }
    override fun onPause() {
        super.onPause()
    }
    override fun onStop() {
        super.onStop()
    }
    override fun onRestart() {
        super.onRestart()
    }
    override fun onDestroy() {
        super.onDestroy()
    }

    //Weather Functions

    private fun initializeWeather() {
        weatherFetcher = FetchWeather(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationProvider = LocationProvider(fusedLocationClient)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            fetchUserLocation()
        }
    }

    private fun fetchUserLocation() {
        locationProvider.fetchUserLocation(object : LocationProvider.LocationFetchListener {
            override fun onLocationFetched(latitude: Double, longitude: Double) {
                weatherFetcher.fetchWeatherByCoordinates(latitude, longitude)
            }

            override fun onLocationFetchFailed(error: String) {
                Toast.makeText(this@MainActivity, "Failed to fetch location: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchUserLocation()
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onWeatherFetched(
        name: String,
        tempC: String,
        weatherCondition: String,
        conditionCode: Int
    ) {
        currentLocationTextView.text = name
        degreeTextView.text = "$tempC°C"
        weatherConditionTextView.text = weatherCondition

        val weatherConditionImg = when (conditionCode) {
            1000 -> R.drawable.clear
            1003 -> R.drawable.cloudy
            1006 -> R.drawable.cloudy
            1063 -> R.drawable.rainy
            1183 -> R.drawable.rainy
            1066 -> R.drawable.snowy
            1114 -> R.drawable.snowy
            else -> R.drawable.clear
        }
        weatherConditionImage.setImageResource(weatherConditionImg)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
}