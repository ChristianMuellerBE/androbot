package ch.bfh.christianmueller.mymemory.game

interface BoardGameActions {

    fun saveGameResult(clickesNeeded: Int, amountOfCards: Int?)
}