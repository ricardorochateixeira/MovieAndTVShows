package com.ricardoteixeira.movietvshowsexplorer.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.mappers.NetworkMapper
import com.ricardoteixeira.movietvshowsexplorer.app.utils.DateUtil
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDateFormat(): SimpleDateFormat {
        val sdf = SimpleDateFormat("yyy-mm-DD HH:MM:SS a", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC-7")
        return sdf
    }

    @Provides
    @Singleton
    fun provideDateUtil(dateFormat: SimpleDateFormat): DateUtil{
        return DateUtil(dateFormat)
    }

    @Provides
    @Singleton
    fun provideNoteCacheMapper(dateUtil: DateUtil): NetworkMapper {
        return NetworkMapper(dateUtil)
    }

    @Provides
    @Singleton
    fun provideFirebaseSAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}