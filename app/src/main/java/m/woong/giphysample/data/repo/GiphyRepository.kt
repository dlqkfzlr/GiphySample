package m.woong.giphysample.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import m.woong.giphysample.data.ResWrapper
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.local.entity.RemoteKeys
import m.woong.giphysample.data.source.remote.model.RemoteTrendingGiphyResponse

interface GiphyRepository {

    /* Gif */
//    fun saveFavoriteGif(): Flow<Gif>
//    suspend fun getFavoriteGifs(offset: Int, size: Int): Flow<PagingData<Gif>>
    fun getTrendingGifStream(): Flow<PagingData<Gif>>
    suspend fun saveTrendingGifs(gifs: List<Gif>)
    fun getFavoriteGif(): Flow<PagingData<Gif>>
    suspend fun updateFavoriteGif(gif: Gif): Int
    suspend fun clearGifs()

    /* RemoteKeysDao */
    suspend fun saveRemoteKeys(remoteKey: List<RemoteKeys>)
    suspend fun getRemoteKeysWithId(gifId: String): RemoteKeys?
    suspend fun clearRemoteKeys()
}