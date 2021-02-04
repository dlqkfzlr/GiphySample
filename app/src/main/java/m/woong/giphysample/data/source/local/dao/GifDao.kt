package m.woong.giphysample.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import m.woong.giphysample.data.source.local.entity.Gif

@Dao
interface GifDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGifs(gifs: List<Gif>)

    @Query("SELECT * FROM gif")
    fun selectGifs(): PagingSource<Int, Gif>

    /*@Query("SELECT * FROM gif WHERE isFavorite = :isFavorite" +
            "ORDERS LIMIT :offset, :size")
    fun selectGifsWithFavorite(offset: Int, size: Int, isFavorite: Boolean = true): Flow<PagingData<Gif>>
*/
    @Query("SELECT * FROM gif WHERE isFavorite = :isFavorite")
    fun selectFavoriteGifs(isFavorite: Boolean = true): PagingSource<Int, Gif>

    @Update
    suspend fun updateGif(gif: Gif): Int

    @Query("DELETE FROM gif")
    suspend fun clearGifs()
}