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

    @Query("SELECT * FROM GameResult WHERE playerId  = :playerId order by usedClicks asc, date desc")
    fun findOrderedGameResultsForPlayer(playerId: Int): List<GameResult>

    @Query("SELECT * FROM GameResult S WHERE S.usedClicks = (SELECT MIN(usedClicks) FROM GameResult P WHERE S.playerId = P.playerId) AND S.playerId = :playerId")
    fun findBestResultForPlayer(playerId: Int): List<GameResult>

}