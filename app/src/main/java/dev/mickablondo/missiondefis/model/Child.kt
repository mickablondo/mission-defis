package dev.mickablondo.missiondefis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "children")
data class Child(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)