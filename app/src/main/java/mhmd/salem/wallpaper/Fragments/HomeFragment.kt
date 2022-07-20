package mhmd.salem.wallpaper.Fragments

import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import mhmd.salem.wallpaper.Adapters.BestOfMonthAdapter
import mhmd.salem.wallpaper.Adapters.CategoriesAdapter
import mhmd.salem.wallpaper.Adapters.ColorToneAdapter
import mhmd.salem.wallpaper.Models.BestOfMonthModel
import mhmd.salem.wallpaper.Models.CategoriesModel
import mhmd.salem.wallpaper.Models.ColorToneModel
import mhmd.salem.wallpaper.R
import mhmd.salem.wallpaper.databinding.FragmentHomeBinding
import java.io.File
import java.io.OutputStream
import java.lang.Exception
import java.util.*
import kotlin.math.log


class HomeFragment : Fragment() {

    private lateinit var binding :FragmentHomeBinding
    private var firesstore : FirebaseFirestore ? = null
    private lateinit var imageView: ImageView

    private val takePicture = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ){
         bitmap ->
        imageView.setImageBitmap(bitmap)

    }




   // val   listOfBestOfMonth = arrayListOf<BestOfMonthModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firesstore = FirebaseFirestore.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loadDataBestOfMonth()
        loadDataColorTone()
        loadCategoriesData()




    }



    private fun loadCategoriesData() {
        firesstore!!.collection("categories")
            .addSnapshotListener{value , error ->
                val listData  = arrayListOf<CategoriesModel>()
                val categoryModel = value?.toObjects(CategoriesModel::class.java)
                     listData.addAll(categoryModel!!)

                binding.recCategories.apply {
                    layoutManager = GridLayoutManager(context , 2 , RecyclerView.VERTICAL , false)
                    adapter       = CategoriesAdapter(listData)
                }
            }
    }

    private fun loadDataColorTone() {
        firesstore!!.collection("thecolortone").addSnapshotListener{value , error ->
            var cartData  = arrayListOf<ColorToneModel>()
            val listColor = value?.toObjects(ColorToneModel::class.java)
                cartData.addAll(listColor!!)

            binding.recColorTone.apply {
                layoutManager =    LinearLayoutManager(context , RecyclerView.HORIZONTAL , false)
                adapter       = ColorToneAdapter(cartData)
            }

        }
    }

    private fun loadDataBestOfMonth() {
        firesstore!!.collection("bestofmoth").addSnapshotListener{value , error ->
            val   listOfBestOfMonth = arrayListOf<BestOfMonthModel>()
            val bestModel = value?.toObjects(BestOfMonthModel::class.java)

            listOfBestOfMonth.addAll(bestModel!!)


            binding.recMonth.apply {
                layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL , false)
                adapter  = BestOfMonthAdapter(listOfBestOfMonth)
            }
            /*
           for (i in listOfBestOfMonth)
           {
               Log.e("eeee" ,"onCreateView:"+i.id)
           }


            */

        }




    }

}