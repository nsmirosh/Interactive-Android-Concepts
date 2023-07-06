package nick.mirosh.androidsamples.models

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "todos")
data class DatabaseTodo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String, val description: String, val completed: Boolean
)

fun DatabaseTodo.asDomainModel() = Todo(
    id = id,
    title = title,
    description = description,
    completed = completed
)
