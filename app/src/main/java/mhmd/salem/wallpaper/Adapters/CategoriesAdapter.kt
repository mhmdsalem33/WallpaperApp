package mhmd.salem.wallpaper.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mhmd.salem.wallpaper.Activites.CartActivity
import mhmd.salem.wallpaper.Models.CategoriesModel
import mhmd.salem.wallpaper.databinding.CategoriesRowBinding

class CategoriesAdapter(var categoryList :ArrayList<CategoriesModel>):RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {


    class ViewHolder(val binding :CategoriesRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategoriesRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       Glide.with(holder.itemView)
           .load(categoryList[position].link)
           .into(holder.binding.categoryImg)
        holder.binding.categoryName.text = categoryList[position].name


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context , CartActivity::class.java)
                intent.putExtra("id" , categoryList[position].id)
                intent.putExtra("name" , categoryList[position].name)
                holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
       return  categoryList.size
    }


}