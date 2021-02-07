package m.woong.giphysample.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import m.woong.giphysample.api.GiphyService.Companion.TRENDING_STARTING_PAGE_INDEX
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.remote.RemoteDataSource
import m.woong.giphysample.utils.mapToGif
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TrendingPagingSource @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, Gif>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gif> {
        val pageSize = params.loadSize
        val position = params.key ?: TRENDING_STARTING_PAGE_INDEX
        return try {
            val response = remoteDataSource.getTrendingGiphy(itemsPerPage = pageSize, offset = position * pageSize)
            val gifs = response.mapToGif()
            LoadResult.Page(
                data = gifs,
                prevKey = if (position == TRENDING_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (gifs.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Gif>): Int? {
        TODO("Not yet implemented")
    }
}