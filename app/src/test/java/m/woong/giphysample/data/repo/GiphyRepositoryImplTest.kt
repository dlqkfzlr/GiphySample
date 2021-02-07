package m.woong.giphysample.data.repo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.local.entity.RemoteKey

class GiphyRepositoryImplTest: GiphyRepository {

    override fun getTrendingGifStream(): Flow<PagingData<Gif>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTrendingGifs(gifs: List<Gif>) {
        TODO("Not yet implemented")
    }

    override fun getFavoriteGif(): Flow<PagingData<Gif>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateFavoriteGif(gif: Gif): Int {
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