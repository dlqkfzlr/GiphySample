package m.woong.giphysample.data.remote

import m.woong.giphysample.data.remote.model.RemoteTrendingGiphyResponse

interface RemoteDataSource {
    suspend fun getTrendingGiphy(): RemoteTrendingGiphyResponse
}