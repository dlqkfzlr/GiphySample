package m.woong.giphysample.data.repo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.local.entity.RemoteKey

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
    suspend fun saveRemoteKey(remoteKey: RemoteKey)
//    suspend fun getRemoteKeysWithId(gifId: String): RemoteKey
    suspend fun clearRemoteKeys()
}