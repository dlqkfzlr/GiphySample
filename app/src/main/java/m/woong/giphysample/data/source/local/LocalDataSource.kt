package m.woong.giphysample.data.source.local

import androidx.paging.PagingSource
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.local.entity.RemoteKey

interface LocalDataSource {

    /* GifDao */
    suspend fun saveGifs(gifs: List<Gif>)
    fun getGifsTrending(): PagingSource<Int, Gif>
    fun getFavoriteGifs(): PagingSource<Int, Gif>
    suspend fun updateGif(gif: Gif): Int
    suspend fun clearGifs()

    /* RemoteKeysDao */
    suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>)
    suspend fun getRemoteKeyWithGifId(gifId: String): RemoteKey?
    suspend fun clearRemoteKeys()
}