package com.example.rickmovie.repository.remote.pagedDataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.rickmovie.model.ResultDto
import com.example.rickmovie.repository.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class MovieDataSource
    @Inject constructor(private val apiService: ApiService ,private val scope: CoroutineScope) : PageKeyedDataSource<Int, ResultDto>() {
    private val state = MutableLiveData<Network>()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultDto>
    ) {
        scope.launch {
            state.postValue(Network.LOADING)
            try {
                val result = apiService.getAllCharacters("1")
                Timber.d(" result -> ${result.info.next}")
                state.postValue(Network.SUCCESS)

                callback.onResult(result.results, null, 1)

            } catch (e: Exception) {
                Timber.d("error in fetch")
                state.postValue(Network.ERROR)
            }


        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultDto>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultDto>) {

        scope.launch {
            state.postValue(Network.LOADING)
            try {

                val result = apiService.getAllCharacters(params.key.toString())
                Timber.d(" result after -> ${result.info.next}")
                state.postValue(Network.SUCCESS)

                callback.onResult(result.results, params.key + 1)

            } catch (e: Exception) {
                state.postValue(Network.ERROR)

            }
        }
    }

     fun getState() = state

    enum class Network{
        LOADING,SUCCESS,ERROR
    }


}

