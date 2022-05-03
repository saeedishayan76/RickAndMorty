package com.example.rickmovie.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmovie.databinding.MovieItemBinding
import com.example.rickmovie.model.ResultDto
import java.lang.Exception

class MovieAdapter(private val context: Context) :
    PagedListAdapter<ResultDto, MovieAdapter.ViewHolder>(DiffUtilCallBack()) {


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

        holder.bindData(getItem(position)!!)


    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<ResultDto>() {
        override fun areItemsTheSame(oldItem: ResultDto, newItem: ResultDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultDto, newItem: ResultDto): Boolean {
            return oldItem == newItem
        }

    }
}