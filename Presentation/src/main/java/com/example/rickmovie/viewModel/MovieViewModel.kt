package com.example.rickmovie.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.rickmovie.model.ResultDto
import com.example.rickmovie.repository.remote.MovieRepository
import com.example.rickmovie.repository.remote.pagedDataSource.MovieDataSource
import com.example.rickmovie.repository.remote.pagedDataSource.MovieDataSourceFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject


@HiltViewModel
class MovieViewModel
@Inject constructor(
    private val movieDataSourceFactory: MovieDataSourceFactory ,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val dataSourceFactory: MovieDataSourceFactory = movieDataSourceFactory
    private val executor: Executor
    private var movieLiveData: LiveData<PagedList<ResultDto>>
    private var networkState: LiveData<MovieDataSource.Network> =
        Transformations.switchMap(movieDataSourceFactory.dataSourceLiveData) {
            it.getState()
        }

    init {


        val config = PagedList.Config.Builder()
            .setPageSize(42)
            .setPrefetchDistance(7)
            .setInitialLoadSizeHint(20)
            .setEnablePlaceholders(false)
            .build()

        executor = Executors.newFixedThreadPool(4)
        movieLiveData = LivePagedListBuilder(dataSourceFactory, config)
            .setFetchExecutor(executor).build()
    }


    fun getPosts(): LiveData<PagedList<ResultDto>> = movieLiveData

    fun getState() = networkState

    fun getSearchKey(name:String) = movieRepository.searchData(name).asLiveData()
}
