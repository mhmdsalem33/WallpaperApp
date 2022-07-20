package mhmd.salem.wallpaper.Activites

import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mhmd.salem.wallpaper.Adapters.CartAdapter
import mhmd.salem.wallpaper.Models.CartModel
import mhmd.salem.wallpaper.R
import mhmd.salem.wallpaper.databinding.ActivityCartBinding
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class CartActivity : AppCompatActivity() {
    private lateinit var binding :ActivityCartBinding
    private lateinit var id   :String
    private lateinit var name :String
    private lateinit var url  :String


    private var firestore :FirebaseFirestore ? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()


        getIntentInformation()
        loadDataCart()
       // onItemClick()



    }

/*
    private fun onItemClick() {
        adapter!!.onItemCallback =  { data ->
             val url =     data.link
             val urlImage = URL(url)



            val builder  =AlertDialog.Builder(this)
                .setTitle("Set Wallpaper")
                .setMessage("Do you want Change Wallpaper or download it?")
                .setNegativeButton("Download"){ dialogInterface ,_->
                    val result: kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async {
                        urlImage.toBitmap()
                    }
                    GlobalScope.launch(Dispatchers.Main) {
                        saveImage(result.await())
                    }

                      }
                .setPositiveButton("Set Wallpaper"){yes ,_->
                    val result :kotlinx.coroutines.Deferred<Bitmap?> = GlobalScope.async {
                        urlImage.toBitmap()
                    }
                    GlobalScope.launch(Dispatchers.Main) {
                        val wallPaperManager =   WallpaperManager.getInstance(applicationContext)
                        wallPaperManager.setBitmap(result.await())
                        Toast.makeText(applicationContext, "Wallpaper set", Toast.LENGTH_SHORT).show()
                    }
                }.setCancelable(false)
                .setNeutralButton("Cancel"){ dialoginterface , _->
                    dialoginterface.dismiss()
                }


            val dialog = builder.create()
                dialog.setOnShowListener {
                    dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
                        .setTextColor(Color.RED)
                }
                dialog.show()






        }
    }


 */


    private fun getIntentInformation() {
        val intent =  intent
        id         =  intent.getStringExtra("id")!!
        name       =  intent.getStringExtra("name")!!
        binding.txtAbstract.text = name
    }

    private fun loadDataCart() {




       firestore!!.collection("categories")
           .document(id)
           .collection("wallpaper")
           .addSnapshotListener { value ,  error ->
               val cartList = ArrayList<CartModel>()
               val model     = value?.toObjects(CartModel::class.java)
               cartList.addAll(model!!)



             //  binding.cartRec.layoutManager = StaggeredGridLayoutManager(2 , RecyclerView.VERTICAL)
             //  binding.cartRec.adapter = adapter
             //  binding.countAvailable.text   = "Total Available ${cartList.size}"

               binding.cartRec.apply {
                   layoutManager = StaggeredGridLayoutManager(2 , RecyclerView.VERTICAL)
                   adapter       = CartAdapter(cartList , this@CartActivity)
                   binding.countAvailable.text   = "Total Available ${cartList.size}"
               }

           }
    }


    fun URL.toBitmap(): Bitmap? {
        return try {
            BitmapFactory.decodeStream(openStream())
        } catch (e: IOException) {
            null
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

}