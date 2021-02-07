package m.woong.giphysample.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import m.woong.giphysample.data.paging.GifRemoteMediator
import m.woong.giphysample.data.paging.TrendingPagingSource
import m.woong.giphysample.data.source.local.LocalDataSource
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.local.entity.RemoteKey
import m.woong.giphysample.data.source.remote.RemoteDataSource
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GiphyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : BaseRepository(), GiphyRepository {

    override fun getTrendingGifStream(): Flow<PagingData<Gif>> {
        val pagingSourceFactory = { localDataSource.getGifsTrending() }
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = GifRemoteMediator(remoteDataSource, localDataSource),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    /* In-memory Cache
    override fun getTrendingGifStream(): Flow<PagingData<Gif>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { TrendingPagingSource(remoteDataSource = remoteDataSource) }
        ).flow
    }*/

    override fun getFavoriteGif(): Flow<PagingData<Gif>> {
        val pagingSourceFactory = { localDataSource.getFavoriteGifs() }
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override suspend fun saveTrendingGifs(gifs: List<Gif>) {
        localDataSource.saveGifs(gifs)
    }

    override suspend fun updateFavoriteGif(gif: Gif): Int {
        return localDataSource.updateGif(gif)
    }

    override suspend fun clearGifs() {
        localDataSource.clearGifs()
    }

    override suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>) {
        localDataSource.saveRemoteKeys(remoteKeys)
    }

    override suspend fun getRemoteKeyWithGifId(gifId: String): RemoteKey? {
        return localDataSource.getRemoteKeyWithGifId(gifId)
    }

    override suspend fun clearRemoteKeys() {
        localDataSource.clearRemoteKeys()
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 15
    }
}