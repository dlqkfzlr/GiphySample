package m.woong.giphysample.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import m.woong.giphysample.data.repo.GiphyRepository
import m.woong.giphysample.data.source.local.LocalDataSource
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.local.entity.mapToGif
import m.woong.giphysample.data.source.remote.RemoteDataSource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


const val TRENDING_STARTING_PAGE_INDEX = 1

@Deprecated("Replaced with RemoteMediator")
class TrendingPagingSource @Inject constructor(
private val remoteDataSource: RemoteDataSource
    ): PagingSource<Int, Gif>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Gif> {
        val position = params.key ?: TRENDING_STARTING_PAGE_INDEX
        return try {
            val response = remoteDataSource.getTrendingGiphy(position, params.loadSize)
            val gifDatas = response.mapToGif()
            /*val gifDatas = repository.getFavoriteGifs(position, params.loadSize)*/
            LoadResult.Page(
                data = gifDatas,
                prevKey = if (position == TRENDING_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (gifDatas.isEmpty()) null else position + 1
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