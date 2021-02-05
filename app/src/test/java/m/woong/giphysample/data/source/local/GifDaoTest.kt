package m.woong.giphysample.data.source.local

import android.content.Context
import android.os.Build
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import m.woong.giphysample.data.source.local.dao.GifDao
import m.woong.giphysample.data.source.local.entity.Gif
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class GifDaoTest {

    private lateinit var gifDao: GifDao
    private lateinit var db: GiphyDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, GiphyDatabase::class.java)
            .build()
        gifDao = db.gifDao()
    }

    @Test
    fun insertTwoFavoriteGifAndSelectOneFavoriteGif() = runBlocking {
        val gif1 = Gif("1", "0", "0", "save_url", "0", false)
        val gif2 = Gif("2", "0", "0", "save_url", "0", true)
        gifDao.insertGifs(listOf(gif1, gif2))
        val favoriteGif =  gifDao.selectFavoriteGifs()
        assertThat(favoriteGif, not(nullValue()))
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }
}