package dev.mickablondo.missiondefis.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenges")
data class Challenge(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val childId: Int,
    val title: String,
    val icon: String, // peut contenir un emoji : 🧹 📚 🏃
    val completed: Boolean = false
)
