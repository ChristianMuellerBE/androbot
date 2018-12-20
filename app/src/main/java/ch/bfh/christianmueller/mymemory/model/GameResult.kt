package ch.bfh.christianmueller.mymemory.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(
    foreignKeys = [ForeignKey(entity = Player::class, parentColumns = ["id"],childColumns = ["playerId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["id", "playerId"])]
)
data class GameResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val date: Date? = Date(),
    val usedClicks: Int?,
    val amountOfCards: Int?,
    val playerId: Int
)