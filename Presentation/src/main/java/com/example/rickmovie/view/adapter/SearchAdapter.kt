package com.example.rickmovie.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmovie.databinding.MovieItemBinding
import com.example.rickmovie.model.ResultDto

class SearchAdapter(private val context: Context) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    var searchData = arrayListOf<ResultDto>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(resultDto: ResultDto) {
            binding.tvNameMovie.text = resultDto.name
            binding.tvSpeciesData.text = resultDto.species
            binding.tvGenderData.text = resultDto.gender
            binding.tvStatusUser.text = resultDto.status

            Glide.with(context)
                .load(resultDto.image)
                .into(binding.imgMovie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(searchData[position])
    }

    override fun getItemCount(): Int = searchData.size
}