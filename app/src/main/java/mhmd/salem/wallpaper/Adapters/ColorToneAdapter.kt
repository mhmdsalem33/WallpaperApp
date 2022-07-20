package mhmd.salem.wallpaper.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mhmd.salem.wallpaper.Models.ColorToneModel
import mhmd.salem.wallpaper.databinding.ColorToneRowBinding

class ColorToneAdapter(var colorList :ArrayList<ColorToneModel>):RecyclerView.Adapter<ColorToneAdapter.ViewHolder>() {


    class ViewHolder(val binding:ColorToneRowBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ColorToneRowBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val color = colorList[position].color
        holder.binding.colorTone.setCardBackgroundColor(Color.parseColor(color!!))
    }

    override fun getItemCount(): Int {
       return  colorList.size
    }


}