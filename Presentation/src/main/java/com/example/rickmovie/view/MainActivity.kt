package com.example.rickmovie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickmovie.R
import com.example.rickmovie.databinding.ActivityMainBinding
import com.example.rickmovie.model.ResultDto
import com.example.rickmovie.repository.remote.pagedDataSource.MovieDataSource
import com.example.rickmovie.view.adapter.MovieAdapter
import com.example.rickmovie.view.adapter.SearchAdapter
import com.example.rickmovie.viewModel.MovieViewModel
import com.example.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val movieViewModel by viewModels<MovieViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var searchAdapter: SearchAdapter
    private var searchList = arrayListOf<ResultDto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        movieAdapter = MovieAdapter(this)
        searchAdapter = SearchAdapter(this)
        binding.recMovies.adapter = movieAdapter
        binding.recSearch.adapter = searchAdapter


        fetchData()

        binding.edSearchName.doAfterTextChanged {
            Timber.d("searchKey ${it.toString()}")
            if (it?.length == 0) {
                binding.recMovies.visibility = View.VISIBLE
                binding.recSearch.visibility = View.GONE
            } else {
                searchList.clear()
                binding.recMovies.visibility = View.GONE
                binding.recSearch.visibility = View.VISIBLE
                searchName(it.toString())

            }
        }


    }

    private fun fetchData() {
        movieViewModel.getPosts().observe(this)
        {

            Timber.d("in getData")
            movieAdapter.submitList(it)

        }

        movieViewModel.getState().observe(this) {
            when (it!!) {
                MovieDataSource.Network.LOADING -> {
                    binding.progressMovie.visibility = View.VISIBLE
                }
                MovieDataSource.Network.ERROR -> {
                    binding.progressMovie.visibility = View.GONE
                }
                MovieDataSource.Network.SUCCESS -> {
                    binding.progressMovie.visibility = View.GONE
                }

            }
        }

    }


    fun searchName(name: String) {

        Timber.d("in search list ${searchList.size}")
        movieViewModel.getSearchKey(name).observe(this) {
            when (it) {
                is Resource.Loading -> {
                    Timber.d("in search loading")
                    binding.progressMovie.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Timber.d("in search success ${it.data.size}")
                     searchList = it.data as ArrayList<ResultDto>
                    if (!searchList.isNullOrEmpty()) {
                        searchAdapter.searchData = searchList

                    }
                    binding.progressMovie.visibility = View.GONE


                }

                is Resource.Error -> {
                    Timber.d("in search error ${it.message}")
                    binding.progressMovie.visibility = View.GONE


                }
            }
        }
    }


}