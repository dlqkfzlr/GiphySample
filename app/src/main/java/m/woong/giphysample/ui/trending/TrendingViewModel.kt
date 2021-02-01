package m.woong.giphysample.ui.trending

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import m.woong.giphysample.data.repo.GiphyRepository
import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val repository: GiphyRepository
)  : ViewModel() {

    private var currentTrendingGif: Flow<PagingData<RemoteTrendingGiphyResponse.Data>>? = null

    fun getTrendingGif(): Flow<PagingData<RemoteTrendingGiphyResponse.Data>> {
        val lastReponse = currentTrendingGif
        if (lastReponse != null) return lastReponse
        val newResponse: Flow<PagingData<RemoteTrendingGiphyResponse.Data>>
                = repository.getTrendingGiphyStream()
            .cachedIn(viewModelScope)
        currentTrendingGif = newResponse
        return newResponse
    }

    companion object{
        val TAG = TrendingViewModel::class.java.simpleName
    }
}