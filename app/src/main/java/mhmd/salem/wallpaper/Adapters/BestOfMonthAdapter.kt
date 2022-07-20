package mhmd.salem.wallpaper.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mhmd.salem.wallpaper.Activites.FinalWallPaperActivity
import mhmd.salem.wallpaper.Models.BestOfMonthModel
import mhmd.salem.wallpaper.databinding.ItemBomBinding

class BestOfMonthAdapter(val bestMonthList:ArrayList<BestOfMonthModel>):RecyclerView.Adapter<BestOfMonthAdapter.ViewHolder>() {


    class ViewHolder(val binding :ItemBomBinding):RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestOfMonthAdapter.ViewHolder {
      return ViewHolder(ItemBomBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: BestOfMonthAdapter.ViewHolder, position: Int) {
            Glide.with(holder.itemView)
                .load(bestMonthList[position].link)
                .into(holder.binding.imgMonth)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context , FinalWallPaperActivity::class.java)
                intent.putExtra("link" , bestMonthList[position].link)
             holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return bestMonthList.size
    }
}