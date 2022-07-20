package mhmd.salem.wallpaper.Activites

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import mhmd.salem.wallpaper.R
import mhmd.salem.wallpaper.databinding.ActivityMainBinding
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    private lateinit var permissionLauncher :ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranted = false
    private var isSetWallpaperGranted   = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val bottomNavigation = binding.btnNavigation
        val findNav          = Navigation.findNavController(this , R.id.hostFragment)
            NavigationUI.setupWithNavController(bottomNavigation , findNav)



        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permission ->

            // it's meean if it's == null will generatge permission
            isReadPermissionGranted   =  permission[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
          //  isSetWallpaperGranted   =  permission[android.Manifest.permission.SET_WALLPAPER]   ?: isSetWallpaperGranted
        }

        requestPermission()

    }


 private fun requestPermission() {
    isReadPermissionGranted = ContextCompat.checkSelfPermission(
        this ,
       android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED


     val  permissionRequest : MutableList<String> = ArrayList()
     if (!isReadPermissionGranted)
     {
         permissionRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
     }


     if (permissionRequest.isNotEmpty())
     {
         permissionLauncher.launch(permissionRequest.toTypedArray())
     }



/*
     isSetWallpaperGranted = ContextCompat.checkSelfPermission(
         this,
         android.Manifest.permission.SET_WALLPAPER) == PackageManager.PERMISSION_GRANTED


 */




     /*
     if (!isSetWallpaperGranted)
     {
         permissionRequest.add(android.Manifest.permission.SET_WALLPAPER)
     }


      */



  }




}