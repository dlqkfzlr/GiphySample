package m.woong.giphysample.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.PagingSource
import androidx.paging.toLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import m.woong.giphysample.data.source.local.dao.GifDao
import m.woong.giphysample.data.source.local.dao.RemoteKeysDao
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.local.entity.RemoteKeys
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val gifDao: GifDao,
    private val remoteKeysDao: RemoteKeysDao
): LocalDataSource {

    /* GifDao */
    override suspend fun saveGifs(gifs: List<Gif>) {
        gifDao.insertGifs(gifs)
    }

    override fun getGifsTrending(): PagingSource<Int, Gif> {
        return gifDao.selectGifs()
    }

    override fun getFavoriteGifs(): PagingSource<Int, Gif> {
        return gifDao.selectFavoriteGifs()
    }

    override suspend fun updateGif(gif: Gif): Int {
        return gifDao.updateGif(gif)
    }

    override suspend fun clearGifs() {
        gifDao.clearGifs()
    }

    /* RemoteKeysDao */
    override suspend fun saveRemoteKeys(remoteKey: List<RemoteKeys>) {
        remoteKeysDao.insertKeys(remoteKey)
    }

    override suspend fun getRemoteKeysWithId(gifId: String): RemoteKeys? {
        return remoteKeysDao.selectKeysGifId(gifId)
    }

    override suspend fun clearRemoteKeys() {
        remoteKeysDao.clearKeys()
    }
}