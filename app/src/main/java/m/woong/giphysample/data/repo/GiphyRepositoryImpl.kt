package m.woong.giphysample.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import m.woong.giphysample.data.paging.TrendingPagingSource
import m.woong.giphysample.data.source.remote.RemoteDataSourceImpl
import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse
import javax.inject.Inject

class GiphyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSourceImpl
): BaseRepository(), GiphyRepository {

    override suspend fun getTrendingGiphy() = safeApiCall {
        remoteDataSource.getTrendingGiphy()
    }

    override fun getTrendingGiphyStream(): Flow<PagingData<RemoteTrendingGiphyResponse.Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {TrendingPagingSource(remoteDataSource)}
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}