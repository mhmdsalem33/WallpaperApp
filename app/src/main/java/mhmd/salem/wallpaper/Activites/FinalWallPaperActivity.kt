package mhmd.salem.wallpaper.Activites

import android.app.WallpaperManager
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.inject.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mhmd.salem.wallpaper.R
import mhmd.salem.wallpaper.databinding.ActivityFinalWallPaperBinding
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.net.URL
import java.util.*

class FinalWallPaperActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFinalWallPaperBinding
    private lateinit var url    : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalWallPaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        url =  intent.getStringExtra("link")!!

        Glide.with(applicationContext)
            .load(url)
            .into(binding.imgFinal)

        val urlImage =  URL(url)


        binding.imgSetWallpaper.setOnClickListener {
            val result :kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }
            GlobalScope.launch(Dispatchers.Main) {
              val wallPaperManager =   WallpaperManager.getInstance(applicationContext)
                    wallPaperManager.setBitmap(result.await())
                Toast.makeText(applicationContext, "Wallpaper set", Toast.LENGTH_SHORT).show()
            }
        }




        binding.imgDownload.setOnClickListener {
            val result: kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }
            GlobalScope.launch(Dispatchers.Main) {
                    saveImage(result.await())
            }
        }
    }

    private fun saveImage(image: Bitmap?) {

        val random1 = Random().nextInt(520985)
        val random2 = Random().nextInt(520985)

        val name = "AMOLED-${random1 + random2}"

        val data: OutputStream
        try {
            val resolver = contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator + "Amoled Wallpaper"
            )
            val imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            data = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
            image?.compress(Bitmap.CompressFormat.JPEG, 100, data)
            Objects.requireNonNull<OutputStream?>(data)
            Toast.makeText(this, "Image Save", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Image Not Save", Toast.LENGTH_SHORT).show()
        }

    }


    fun URL.toBitmap(): Bitmap? {
        return try {
            BitmapFactory.decodeStream(openStream())
        } catch (e: IOException) {
            null
        }

    }

}