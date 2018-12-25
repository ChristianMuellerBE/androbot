package ch.bfh.christianmueller.mymemory.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import ch.bfh.christianmueller.mymemory.R
import ch.bfh.christianmueller.mymemory.StartActivity
import ch.bfh.christianmueller.mymemory.data.AppDatabase
import ch.bfh.christianmueller.mymemory.data.GameResultsRepo
import ch.bfh.christianmueller.mymemory.data.PlayerRepo
import ch.bfh.christianmueller.mymemory.model.GameResult
import ch.bfh.christianmueller.mymemory.model.Player
import java.util.*

class BoardgameActivity : AppCompatActivity(), BoardGameActions {

    private lateinit var navigation: BottomNavigationView

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boardgame)
        navigation = findViewById(R.id.bottom_nav)
        navigation.setOnNavigationItemSelectedListener { clickedMenuItem -> selectMenuItem(clickedMenuItem) }

        if (savedInstanceState == null) {
            startMemoryGame()
        }
        database = AppDatabase.build(applicationContext)
    }

    private fun selectMenuItem(clickedMenuItem: MenuItem): Boolean {

        Log.i("MyMemory", "selectMenuItem: ${clickedMenuItem.title}")
        when (clickedMenuItem.itemId) {
            R.id.menu_game -> gameMenuClicked()
            R.id.menu_ranking -> rankingMenuClicked()
            R.id.menu_hall_of_fame -> hallOfFameClicked()
            R.id.menu_logout -> logoutClicked()
        }
        return true
    }

    private fun logoutClicked() {
        val preferences = getSharedPreferences(StartActivity.SHARED_PREF_TAG, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.commit()
        finish()
    }

    private fun startMemoryGame() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_game_board, MemoryGameFragment()).commit()
    }

    private fun hallOfFameClicked() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_game_board, HallOfFameFragment()).commit()
    }

    private fun rankingMenuClicked() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_game_board, RankingFragment()).commit()
    }

    private fun gameMenuClicked() {
        startMemoryGame()
    }

    companion object {
        fun getBoardGameActivityIntent(ctx: Context) = Intent(ctx, BoardgameActivity::class.java)
    }

    override fun saveGameResult(clickesNeeded: Int, amountOfCards: Int?) {
        val userName = getSharedPreferences(
            StartActivity.SHARED_PREF_TAG,
            Context.MODE_PRIVATE
        ).getString(StartActivity.USER_NAME_PREF_TAG, null)
        userName?.let { userName ->
            Log.i("MyMemory", "Look for User with Name : $userName")
            val playerId: Int? = findOrCreatePlayer(userName)
            if (playerId != null) {
                val gameResult = GameResult(null, Date(), clickesNeeded, amountOfCards, playerId)
                GameResultsRepo(database).saveGameResult(gameResult)
            } else {
                Log.e("MyMemory","No player found and creation of new Player failed: $userName")
            }
        }
    }

    private fun findOrCreatePlayer(userName: String): Int? {
        val existingPlayer = PlayerRepo(database).findPlayerByName(userName)
        existingPlayer?.let { player: Player ->
            Log.i("MyMemory", "Found exixting Player: ${player.name}  id: ${player.id}")
            return player.id
        }
        val savedPlayerId = PlayerRepo(database).savePlayer(Player(null, userName))
        Log.i("MyMemory", "Saved new Player with Id: ${savedPlayerId}")
        return savedPlayerId.toInt()
    }
}
