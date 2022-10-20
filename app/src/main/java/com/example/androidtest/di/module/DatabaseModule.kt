package com.example.androidtest.di.module

import android.content.Context
import androidx.room.Room
import com.example.androidtest.data.room.DatabaseUsers
import com.example.androidtest.data.room.dao.UserCacheDao
import com.example.androidtest.data.room.dao.UserFriendsCacheDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(
        appContext: Context
    ) : DatabaseUsers {
        return Room.databaseBuilder(
            appContext,
            DatabaseUsers::class.java,
            "user_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserCacheDao(
        appDatabase: DatabaseUsers
    ) : UserCacheDao {
        return appDatabase.userCacheDao()
    }

    @Provides
    @Singleton
    fun provideUserFriendsCacheDao(
        appDatabase: DatabaseUsers
    ) : UserFriendsCacheDao {
        return appDatabase.userFriendsCacheDao()
    }
}