package m.woong.giphysample.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GifDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(gifs: List<Gifs>)

    @Query("SELECT * FROM gifs WHERE" +
            "is_favorite LIKE :isFavorite")
    fun gifByFavorite(isFavorite: Boolean): PagingSource<Int, Gifs>

    @Query("DELETE FROM gifs")
    suspend fun clearGifs()
}