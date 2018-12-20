package ch.bfh.christianmueller.mymemory.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import ch.bfh.christianmueller.mymemory.model.GameResult

@Dao
interface GameResultDao {

    @Insert
    fun saveGameResult(gameResult: GameResult): Long

    @Query("SELECT * FROM GameResult")
    fun findAllGameResults(): List<GameResult>

}