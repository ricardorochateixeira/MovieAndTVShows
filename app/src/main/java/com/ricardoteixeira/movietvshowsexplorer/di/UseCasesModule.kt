package com.ricardoteixeira.movietvshowsexplorer.di

import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.repositoryimpl.common.InsertOrUpdateUserProfileImpl
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.repositoryimpl.userprofile.GetUserProfileImpl
import com.ricardoteixeira.movietvshowsexplorer.data.repository.common.InsertOrUpdateUserProfile
import com.ricardoteixeira.movietvshowsexplorer.data.repository.userprofile.GetUserProfile
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {

    @Binds
    abstract fun provideGetUserProfile(getUserProfile: GetUserProfileImpl): GetUserProfile

    @Binds
    abstract fun provideInsertOrUpdateUserProfile(insertOrUpdateUserProfileImpl: InsertOrUpdateUserProfileImpl): InsertOrUpdateUserProfile
}