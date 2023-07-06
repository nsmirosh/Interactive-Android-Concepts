package nick.mirosh.androidsamples.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nick.mirosh.androidsamples.database.AppDatabase
import nick.mirosh.androidsamples.database.DATABASE_NAME
import nick.mirosh.androidsamples.database.TodoDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): TodoDao {
        return Room
            .databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
            .build()
            .todoDao()
    }
}
