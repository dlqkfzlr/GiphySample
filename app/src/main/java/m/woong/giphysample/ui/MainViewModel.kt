package m.woong.giphysample.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import m.woong.giphysample.data.repo.GiphyRepository
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.utils.Event
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    private var currentTrendingGif: Flow<PagingData<Gif>>? = null
    private var currentFavoriteGif: Flow<PagingData<Gif>>? = null

    private var _update = MutableLiveData<Event<Boolean>>()
    val update: LiveData<Event<Boolean>>
    get() = _update

    fun getTrendingGif(): Flow<PagingData<Gif>> {
        val lastResponse = currentTrendingGif
        if (lastResponse != null) return lastResponse
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
                _update.postValue(Event(true))
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