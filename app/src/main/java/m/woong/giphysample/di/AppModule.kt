package m.woong.giphysample.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.woong.giphysample.api.GiphyApi
import m.woong.giphysample.data.source.local.GiphyDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.giphy.com/v1/gifs/"

    @Singleton
    @Provides
    fun provideGiphyApi(): GiphyApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GiphyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = GiphyDatabase.invoke(app)

    @Singleton
    @Provides
    fun provideGifDao(db: GiphyDatabase) = db.gifsDao()
}