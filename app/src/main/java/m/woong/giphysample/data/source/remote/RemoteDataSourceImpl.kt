package m.woong.giphysample.data.source.remote

import m.woong.giphysample.api.GiphyApi
import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: GiphyApi
): RemoteDataSource {
    override suspend fun getTrendingGiphy(): RemoteTrendingGiphyResponse {
        return api.requestTrendingGiphy()
    }
}