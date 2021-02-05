package m.woong.giphysample.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import m.woong.giphysample.data.source.local.dao.GifDao
import m.woong.giphysample.data.source.local.dao.RemoteKeysDao
import m.woong.giphysample.data.source.local.entity.Gif
import m.woong.giphysample.data.source.local.entity.RemoteKeys


@Database(
    entities = [Gif::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class GiphyDatabase : RoomDatabase() {

    abstract fun gifDao(): GifDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile private var instance : GiphyDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }


        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            GiphyDatabase::class.java,
            "giphy.db")
            .fallbackToDestructiveMigration()
            .build()

    }
}