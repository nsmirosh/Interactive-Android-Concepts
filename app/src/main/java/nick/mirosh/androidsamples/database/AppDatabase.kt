package nick.mirosh.androidsamples.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nick.mirosh.androidsamples.models.DatabaseTodo

const val DATABASE_NAME = "todos-db"

@Database(entities = [DatabaseTodo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
