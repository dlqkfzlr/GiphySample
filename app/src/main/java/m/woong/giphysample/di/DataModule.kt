package m.woong.giphysample.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import m.woong.giphysample.data.repo.GiphyRepository
import m.woong.giphysample.data.repo.GiphyRepositoryImpl
import m.woong.giphysample.data.source.local.LocalDataSource
import m.woong.giphysample.data.source.local.LocalDataSourceImpl
import m.woong.giphysample.data.source.remote.RemoteDataSource
import m.woong.giphysample.data.source.remote.RemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindGiphyRepository(
        giphyRepositoryImpl: GiphyRepositoryImpl
    ): GiphyRepository

    @Binds
    abstract fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

}