package m.woong.giphysample.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import m.woong.giphysample.data.source.remote.RemoteDataSource
import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


private const val TRENDING_STARTING_PAGE_INDEX = 1

class TrendingPagingSource @Inject constructor(
   private val remoteDataSource: RemoteDataSource
    ): PagingSource<Int, RemoteTrendingGiphyResponse.Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RemoteTrendingGiphyResponse.Data> {
        val position = params.key ?: TRENDING_STARTING_PAGE_INDEX
        return try {
            val response = remoteDataSource.getTrendingGiphy().data
            LoadResult.Page(
                data = response,
                prevKey = if (position == TRENDING_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RemoteTrendingGiphyResponse.Data>): Int? {
        TODO("Not yet implemented")
    }
}