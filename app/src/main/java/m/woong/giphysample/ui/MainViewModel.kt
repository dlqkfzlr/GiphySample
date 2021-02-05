package m.woong.giphysample.ui

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import m.woong.giphysample.data.repo.GiphyRepository
import m.woong.giphysample.data.source.local.entity.Gif
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    private var currentTrendingGif: Flow<PagingData<Gif>>? = null
    private var currentFavoriteGif: Flow<PagingData<Gif>>? = null

    fun getTrendingGif(): Flow<PagingData<Gif>> {
        val lastResponse = currentTrendingGif
        if (lastResponse != null) return lastResponse
        Log.d("GIPHY", "MainViewModel] repository.getTrendingGifStream() 호출")
        val newResponse = fetchTrendingGif()
        currentTrendingGif = newResponse
        return newResponse
    }

    private fun fetchTrendingGif() = repository.getTrendingGifStream()
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)

    fun saveFavoriteGif(gif: Gif) {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.updateFavoriteGif(gif) == 1){
                Log.d(TAG, "UPDATE SUCCESS")
                // TODO: ITEM도 바꿔줄것
            } else {
                Log.d(TAG, "UPDATE FAILURE")
            }
        }
    }

    fun getFavoriteGif(): Flow<PagingData<Gif>> {
        val lastResponse = currentFavoriteGif
        if (lastResponse != null) return lastResponse
        val newResponse = fetchFavoriteGif()
        currentFavoriteGif = newResponse
        return newResponse
    }

    private fun fetchFavoriteGif() = repository.getFavoriteGif()
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    companion object {
        val TAG = MainViewModel::class.java.simpleName
    }
}