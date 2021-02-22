package com.ricardoteixeira.movietvshowsexplorer.di

import com.ricardoteixeira.movietvshowsexplorer.data.repository.common.InsertOrUpdateUserProfile
import com.ricardoteixeira.movietvshowsexplorer.data.repository.userprofile.GetUserProfile
import com.ricardoteixeira.movietvshowsexplorer.domain.usecases.common.InsertOrUpdateUserProfileUseCase
import com.ricardoteixeira.movietvshowsexplorer.domain.usecases.userprofile.GetUserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    fun provideGetUserProfileUseCase(getUserProfile: GetUserProfile): GetUserProfileUseCase = GetUserProfileUseCase(getUserProfile)

    @Provides
    fun provideInsertOrUpdateUseCase(insertOrUpdateUserProfile: InsertOrUpdateUserProfile): InsertOrUpdateUserProfileUseCase = InsertOrUpdateUserProfileUseCase(insertOrUpdateUserProfile)
}