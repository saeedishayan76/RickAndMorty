package com.example.rickmovie.repository.remote

import com.example.rickmovie.model.ResultDto
import com.example.rickmovie.model.RickDto
import com.example.rickmovie.repository.ApiService
import com.example.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.Exception

class MovieRepository
@Inject constructor( private val apiService: ApiService){


    fun searchData(key:String):Flow<Resource<List<ResultDto>>> = flow {
        emit(Resource.Loading)

        try {
            val response = apiService.searchName(name = key)
            emit(Resource.Success(data = response.results?: listOf()))
        }catch (e :Exception)
        {
            emit(Resource.Error(e.localizedMessage?:"error in fetch"))
        }
    }
}