package com.example.finalapplication

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalapplication.databinding.ItemListBinding
import java.io.File
import java.io.OutputStreamWriter

class ListViewHolder(val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root)
class ListAdapter(val datas: MutableList<myXmlItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ListViewHolder).binding
        val model = datas!![position]

        binding.name.text = model.title
        binding.address.text = model.addr1
        binding.type.text = model.cat1 + " " + model.cat2 + " " + model.cat3

        Glide.with(binding.root)
            .load(model.firstimage)
            .override(150, 100)
            .into(binding.image)

        binding.list.setOnClickListener {
            Log.d("mobileApp", "listClick")
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            val title = model.title.toString()
            intent.putExtra("image", model.firstimage.toString())
            intent.putExtra("type", model.cat1.toString() + " > " + model.cat2.toString() + " > " + model.cat3.toString())
            intent.putExtra("name", title)
            intent.putExtra("address", model.addr1.toString())
            intent.putExtra("id", model.contentid.toString())
            intent.putExtra("x", model.mapx.toString())
            intent.putExtra("y", model.mapy.toString())

            val file = File(holder.itemView.context.filesDir, "recent.txt")
            val writeStream: OutputStreamWriter = file.writer()
            writeStream.write(title.toString())
            writeStream.flush()

            holder.itemView.context.startActivity(intent)
        }
    }
}