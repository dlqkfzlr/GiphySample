package m.woong.giphysample.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.local.entity.RemoteKeys

interface LocalDataSource {

    /* GifDao */
    suspend fun saveGifs(gifs: List<Gif>)
    fun getGifsTrending(): PagingSource<Int, Gif>
    fun getFavoriteGifs(): PagingSource<Int, Gif>
    suspend fun updateGif(gif: Gif): Int
    suspend fun clearGifs()

    /* RemoteKeysDao */
    suspend fun saveRemoteKeys(remoteKey: List<RemoteKeys>)
    suspend fun getRemoteKeysWithId(gifId: String): RemoteKeys?
    suspend fun clearRemoteKeys()
}