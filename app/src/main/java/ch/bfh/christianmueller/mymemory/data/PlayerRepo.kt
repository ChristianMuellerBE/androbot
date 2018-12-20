package ch.bfh.christianmueller.mymemory.data

import ch.bfh.christianmueller.mymemory.model.Player


class PlayerRepo(appDatabase: AppDatabase){

    private var database: AppDatabase = appDatabase


    fun savePlayer(player: Player): Long{
        return database.playerDAO().savePlayer(player)
    }

    fun findPlayerByName(name: String): Player?{
        return database.playerDAO().findPlayerByName(name)
    }

    fun nuleAllPlayerrs(){
        return database.playerDAO().nukePlayers()
    }
}