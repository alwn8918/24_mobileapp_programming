package com.example.finalapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalapplication.databinding.ItemIntroBinding

class IntroViewHolder(val binding: ItemIntroBinding): RecyclerView.ViewHolder(binding.root)
class IntroAdapter(val datas: MutableList<myIntroItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return IntroViewHolder(ItemIntroBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as IntroViewHolder).binding
        val model = datas!![position]

        binding.tel.text = model.tel
        binding.overview.text = model.overview
    }
}