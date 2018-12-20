package ch.bfh.christianmueller.mymemory.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import ch.bfh.christianmueller.mymemory.model.Player

@Dao
interface PlayerDao {

    @Insert
    fun savePlayer(player: Player): Long

    @Query("SELECT * FROM PLAYER P WHERE P.name = :name")
    fun findPlayerByName(name: String): Player

    @Query("DELETE FROM Player")
    fun nukePlayers()
}