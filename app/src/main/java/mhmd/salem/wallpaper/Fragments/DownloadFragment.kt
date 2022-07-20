package mhmd.salem.wallpaper.Fragments

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import mhmd.salem.wallpaper.Adapters.CollectionAdapter
import mhmd.salem.wallpaper.databinding.FragmentDownloadBinding
import java.io.File


class DownloadFragment : Fragment() {

    private lateinit var binding :FragmentDownloadBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDownloadBinding.inflate(inflater , container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allFiles   : Array<File>
        val imageList = arrayListOf<String>()

        val targetPath =
            Environment.getExternalStorageDirectory().absolutePath +"/Pictures/Amoled Wallpaper"

        val targetFile = File(targetPath)
            allFiles   = targetFile.listFiles()

        for (data in allFiles)
        {
            imageList.add(data.absolutePath)
        }


        for (i in imageList)
        {
            //Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show()
        }

        binding.downloadRec.apply {
            layoutManager = GridLayoutManager(context , 2 , RecyclerView.VERTICAL , false)
            adapter       = CollectionAdapter(imageList)
        }



    }


}