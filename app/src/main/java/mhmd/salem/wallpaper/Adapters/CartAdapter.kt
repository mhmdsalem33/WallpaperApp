package mhmd.salem.wallpaper.Adapters

import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.Image
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mhmd.salem.wallpaper.Models.CartModel
import mhmd.salem.wallpaper.R

import mhmd.salem.wallpaper.databinding.CartRowBinding
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class CartAdapter(var cartList :ArrayList<CartModel> , val context: Context):RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    class ViewHolder(val binding :CartRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(CartRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



     Picasso.get()
         .load(cartList[position].link)
        .into(holder.binding.cartImage)


        holder.itemView.setOnLongClickListener {

            val url =     cartList[position].link
            val urlImage = URL(url)



            val builder  = AlertDialog.Builder(context)
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
                        val wallPaperManager =   WallpaperManager.getInstance(context)
                            wallPaperManager.setBitmap(result.await())
                        Toast.makeText(context, "Wallpaper set", Toast.LENGTH_SHORT).show()
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


        true
        }

    }

    override fun getItemCount(): Int {
       return  cartList.size
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
            val resolver = context.contentResolver
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
            Toast.makeText(context, "Image Save", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            Toast.makeText(context, "Image Not Save", Toast.LENGTH_SHORT).show()
        }

    }

}