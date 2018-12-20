package ch.bfh.christianmueller.mymemory.data

import ch.bfh.christianmueller.mymemory.model.GameResult


class GameResultsRepo(appDatabase: AppDatabase){

    private var database: AppDatabase = appDatabase

    fun saveGameResult(gameResult: GameResult): Long{
        return database.gameResultDAO().saveGameResult(gameResult)
    }

    fun findAllGameResults(): List<GameResult>{
        return database.gameResultDAO().findAllGameResults()
    }
}
