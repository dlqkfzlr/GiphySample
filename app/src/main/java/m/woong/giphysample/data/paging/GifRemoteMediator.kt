package m.woong.giphysample.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import m.woong.giphysample.api.GiphyService.Companion.TRENDING_STARTING_PAGE_INDEX
import m.woong.giphysample.data.source.local.LocalDataSource
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.local.entity.RemoteKey
import m.woong.giphysample.data.source.remote.RemoteDataSource
import m.woong.giphysample.utils.mapToGif
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class GifRemoteMediator(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : RemoteMediator<Int, Gif>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Gif>
    ): MediatorResult {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: TRENDING_STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    /*val remoteKeys = getRemoteKeyForFirstItem(state)
                        ?: throw InvalidObjectException("Remote key and the prevKey should not be null")

                    remoteKeys.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = true)*/
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    if (remoteKeys?.nextKey == null) throw InvalidObjectException("Remote key should not be null for $loadType")
                    remoteKeys.nextKey
                }
            }
        try {
            val pageSize = state.config.pageSize
            val response =
                remoteDataSource.getTrendingGiphy(itemsPerPage = pageSize, offset = page * pageSize)

            val gifs = response.mapToGif()
            val endOfPageReached = gifs.isEmpty()
            withContext(Dispatchers.IO) {
                /*if (loadType == LoadType.REFRESH) {
                    with(localDataSource) {
                        clearGifs()
                        clearRemoteKeys()
                    }
                }*/
                val prevKey = if (page == TRENDING_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPageReached) null else page + 1
                val keys = gifs.map {
                    RemoteKey(gifId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                with(localDataSource) {
                    saveRemoteKeys(keys)
                    saveGifs(gifs)
                }
            }
            return MediatorResult.Success(endOfPageReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }


    // LoadType.REFRESH
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Gif>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { gifId ->
                localDataSource.getRemoteKeyWithGifId(gifId)
            }
        }
    }

    // LoadType.PREPEND
    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Gif>
    ): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { gif ->
                localDataSource.getRemoteKeyWithGifId(gif.id)
            }
    }

    // LoadType.APPEND
    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Gif>
    ): RemoteKey? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { gif ->
                localDataSource.getRemoteKeyWithGifId(gif.id)
            }
    }

}