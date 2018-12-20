package ch.bfh.christianmueller.mymemory.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String
)