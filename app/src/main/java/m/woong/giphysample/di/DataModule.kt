package m.woong.giphysample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import m.woong.giphysample.data.repo.GiphyRepository
import m.woong.giphysample.data.repo.GiphyRepositoryImpl

@Module
@InstallIn(ActivityComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindGiphyRepository(
        giphyRepositoryImpl: GiphyRepositoryImpl
    ): GiphyRepository
}