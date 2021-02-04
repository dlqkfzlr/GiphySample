package m.woong.giphysample.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import m.woong.giphysample.data.source.local.entity.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE gifId = :gifId")
    suspend fun selectKeysGifId(gifId: String): RemoteKeys?


    @Query("DELETE FROM remote_keys")
    suspend fun clearKeys()
}