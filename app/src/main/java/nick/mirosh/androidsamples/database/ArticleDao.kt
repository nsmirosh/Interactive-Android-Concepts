package nick.mirosh.androidsamples.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import nick.mirosh.androidsamples.models.DatabaseTodo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getAllTodos():  List<DatabaseTodo>

    @Query("SELECT * FROM todos WHERE completed = 1")
    fun getCompletedTodos(): List<DatabaseTodo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(todos: List<DatabaseTodo>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: DatabaseTodo)

    @Delete
    fun delete(todo: DatabaseTodo)
}
