package m.woong.giphysample.data.repo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import m.woong.giphysample.data.ResWrapper
import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse

interface GiphyRepository {

    suspend fun getTrendingGiphy(): ResWrapper<RemoteTrendingGiphyResponse>
    fun getTrendingGiphyStream(): Flow<PagingData<RemoteTrendingGiphyResponse.Data>>
}