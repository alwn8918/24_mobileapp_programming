package com.example.finalapplication

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalapplication.databinding.ItemListBinding

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
            holder.itemView.context.startActivity(intent)
        }
    }
}