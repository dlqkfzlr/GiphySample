package m.woong.giphysample.data.source.remote

import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse

interface RemoteDataSource {
    suspend fun getTrendingGiphy(itemsPerPage: Int, offset: Int): RemoteTrendingGiphyResponse
}