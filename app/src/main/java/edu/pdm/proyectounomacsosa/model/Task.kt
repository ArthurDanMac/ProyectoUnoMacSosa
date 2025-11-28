package edu.pdm.proyectounomacsosa.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName="task",
    foreignKeys =[
        ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["idUser"],
            onDelete = ForeignKey.CASCADE)
    ]) //nombre de tabla

data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val name: String,
    val plannedD: String,
    val status: Int,
    val idUser:Int
)
