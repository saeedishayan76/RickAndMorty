package com.example.rickmovie.repository.remote.pagedDataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.rickmovie.model.ResultDto
import javax.inject.Inject


class MovieDataSourceFactory
    @Inject constructor(private val movieDataSource: MovieDataSource): DataSource.Factory<String,ResultDto>(){
    val dataSourceLiveData = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<String, ResultDto> {
        dataSourceLiveData.postValue(movieDataSource)
        return movieDataSource as DataSource<String, ResultDto>
    }
}