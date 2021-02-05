package m.woong.giphysample.data.source.remote

import m.woong.giphysample.api.GiphyService
import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val service: GiphyService
): RemoteDataSource {
    override suspend fun getTrendingGiphy(itemsPerPage: Int, page: Int): RemoteTrendingGiphyResponse {
        return service.requestTrendingGiphy(limit = itemsPerPage, offset = page)
    }
}