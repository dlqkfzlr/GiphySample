package m.woong.giphysample.data.source.local

import androidx.paging.PagingSource
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.local.entity.RemoteKey

class FakeLocalDataSource : LocalDataSource {

    override suspend fun saveGifs(gifs: List<Gif>) {
        TODO("Not yet implemented")
    }

    override fun getGifsTrending(): PagingSource<Int, Gif> {
        TODO("Not yet implemented")
    }

    override fun getFavoriteGifs(): PagingSource<Int, Gif> {
        TODO("Not yet implemented")
    }

    override suspend fun updateGif(gif: Gif): Int {
        TODO("Not yet implemented")
    }

    override suspend fun clearGifs() {
        TODO("Not yet implemented")
    }

    override suspend fun saveRemoteKeys(remoteKeys: List<RemoteKey>) {
        TODO("Not yet implemented")
    }

    override suspend fun getRemoteKeyWithGifId(gifId: String): RemoteKey? {
        TODO("Not yet implemented")
    }

    override suspend fun clearRemoteKeys() {
        TODO("Not yet implemented")
    }
}