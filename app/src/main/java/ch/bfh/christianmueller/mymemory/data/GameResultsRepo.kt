package ch.bfh.christianmueller.mymemory.data

import ch.bfh.christianmueller.mymemory.model.GameResult


class GameResultsRepo(appDatabase: AppDatabase){

    private var database: AppDatabase = appDatabase

    fun saveGameResult(gameResult: GameResult): Long{
        return database.gameResultDAO().saveGameResult(gameResult)
    }

    fun findOrderedGameResultsForPlayer(playerId: Int): List<GameResult>{
        return database.gameResultDAO().findOrderedGameResultsForPlayer(playerId)
    }

    fun findBestResultForPlayer(playerId: Int): GameResult?{
        return database.gameResultDAO().findBestResultForPlayer(playerId).maxBy { result -> result.date!! }
    }
}
