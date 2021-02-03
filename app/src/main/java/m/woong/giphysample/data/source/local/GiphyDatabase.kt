package m.woong.giphysample.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class GiphyDatabase : RoomDatabase() {

    abstract fun gifsDao(): GifDao

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