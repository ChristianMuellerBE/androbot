package ch.bfh.christianmueller.mymemory.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class PlayerWithResults(
    @Embedded
    var playrer: Player? = null,
    @Relation(entity = GameResult::class, parentColumn = "id", entityColumn = "playerId")
    var gameResults: List<GameResult>? = null
)