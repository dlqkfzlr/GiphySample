package m.woong.giphysample.data.repo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import m.woong.giphysample.data.ResWrapper
import m.woong.giphysample.data.remote.RemoteDataSourceImpl
import m.woong.giphysample.data.remote.model.RemoteTrendingGiphyResponse

class GiphyRepositoryImpl(
    private val remoteDataSource: RemoteDataSourceImpl
): BaseRepository(), GiphyRepository {

    override suspend fun getTrendingGiphy() = safeApiCall {
        remoteDataSource.getTrendingGiphy()
    }

    override fun getTrendingGiphyStream(): Flow<PagingData<RemoteTrendingGiphyResponse>> {
        TODO("Not yet implemented")
    }
}