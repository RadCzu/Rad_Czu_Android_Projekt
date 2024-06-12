package com.example.ad_gdynia

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import com.example.ad_gdynia.database.GA_LocationDatabase
import com.example.ad_gdynia.database.entity.Location
import com.example.ad_gdynia.database.repository.LocationRepository
import com.example.ad_gdynia.database.viewmodel.LocationViewModel
import com.example.ad_gdynia.database.viewmodel.LocationViewModelFactory
import com.example.ad_gdynia.dataclasses.Vector2
import com.example.ad_gdynia.googlemaps.MapScreen
import com.example.ad_gdynia.ui.theme.AD_GdyniaTheme

class MainActivity : FragmentActivity() {

    // Dependency injection of the view model and location provider
    private lateinit var locationProvider: LocationProvider
    private lateinit var locationViewModel: LocationViewModel

    //Start providing location data whenever permissions are granted
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        locationProvider.startLocationUpdates()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "App has launched successfully")

        // Initialise the view model via Factory
        val locationDatabase = GA_LocationDatabase.getInstance(applicationContext)
        val locationDao = locationDatabase.dao
        val locationRepository = LocationRepository(locationDao)
        val factory = LocationViewModelFactory(locationRepository)
        val locationViewModel = ViewModelProvider(this, factory)[LocationViewModel::class.java]

        this.locationViewModel = locationViewModel

        // Initialise the location provider object
        locationProvider = LocationProvider(this)

        // Permissions
        val coarseLocationPermission = Manifest.permission.ACCESS_COARSE_LOCATION
        val fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION

        // Check if both coarse and fine location permissions are granted
        val coarseLocationPermissionGranted = ContextCompat.checkSelfPermission(this, coarseLocationPermission) == PackageManager.PERMISSION_GRANTED
        val fineLocationPermissionGranted = ContextCompat.checkSelfPermission(this, fineLocationPermission) == PackageManager.PERMISSION_GRANTED

        if(!coarseLocationPermissionGranted || !fineLocationPermissionGranted) {
            // Request both coarse and fine location permissions if not
            ActivityCompat.requestPermissions(
                this,
                arrayOf(coarseLocationPermission, fineLocationPermission),
                R.integer.location_request_code
            )
            Log.d("MainActivity", "Permissions not granted, requesting permissions")
        }

        // Switch to the main screen
        switchToMainScreen()
    }

    // Method for the activity to show the main screen
    private fun switchToMainScreen() {
        setContent {
            AD_GdyniaTheme {
                GdyniaFadeInGifFragmentContent()
                MainScreen(
                    locationsFlow = locationViewModel.locations.asFlow(),
                    locationProvider = locationProvider,
                    onItemClick = { location -> switchToMap(location) }
                )
            }
        }
    }

    // Method for the activity to show the google map
    private fun switchToMap(location: Location): Unit {
        setContent {
            GdyniaFadeInGifFragmentContent()
            AD_GdyniaTheme {
                MapScreen(location = location)
            }
        }
    }
}

// Test
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AD_GdyniaTheme {
        Text(text = "hello")
    }
}

// I intended to play a gif whenever the app starts, but at some point in development it just stopped showing up in time.
// I did not have time to bring it back
@Composable
fun GdyniaFadeInGifFragmentContent() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                // Create a container for the fragment
                FragmentContainerView(context).apply {
                    id = View.generateViewId()

                    // Add the fragment to its container
                    val fragmentActivity = context as FragmentActivity
                    fragmentActivity.supportFragmentManager.commit {
                        replace(id, GdyniaFadeInGifFragment())
                    }

                    // Remove the fragment after two seconds
                    Handler(Looper.getMainLooper()).postDelayed({
                        fragmentActivity.supportFragmentManager.commit {
                            remove(fragmentActivity.supportFragmentManager.findFragmentById(id)!!)
                        }
                    }, 2000)
                }
            }
        )
    }
}
