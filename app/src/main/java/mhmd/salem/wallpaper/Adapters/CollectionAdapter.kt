package mhmd.salem.wallpaper.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.transition.Hold
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso
import mhmd.salem.wallpaper.Activites.CartActivity
import mhmd.salem.wallpaper.R
import mhmd.salem.wallpaper.databinding.CategoriesRowBinding
import mhmd.salem.wallpaper.databinding.DownloadRowBinding

class CollectionAdapter(var categoryList: ArrayList<String>):RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {


    class ViewHolder(val binding: DownloadRowBinding):RecyclerView.ViewHolder(binding.root)
    {
        //val image = itemView.findViewById<RoundedImageView>(R.id.img_download_fragment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       // return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.download_row , parent ,false))
        return ViewHolder(DownloadRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(categoryList[position])
            .into(holder.binding.imgDownloadFragment)




    }

    override fun getItemCount(): Int {
       return  categoryList.size
    }


}