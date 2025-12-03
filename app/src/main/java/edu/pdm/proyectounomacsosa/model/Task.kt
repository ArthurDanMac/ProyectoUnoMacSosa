package edu.pdm.proyectounomacsosa.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(
    tableName = "task",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val plannedD: String,
    val status: Int, // mejor usar Boolean
    @ColumnInfo(name = "user_id") val idUser: Int // alinear con SQL
)
