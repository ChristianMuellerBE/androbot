package ch.bfh.christianmueller.mymemory.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import ch.bfh.christianmueller.mymemory.R

class BoardgameActivity : AppCompatActivity() {

    private lateinit var navigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boardgame)
        navigation = findViewById(R.id.bottom_nav)
        navigation.setOnNavigationItemSelectedListener { clickedMenuItem -> selectMenuItem(clickedMenuItem) }

        startMemoryGame()
    }

    private fun selectMenuItem(clickedMenuItem: MenuItem): Boolean {

        when (clickedMenuItem.itemId) {
            R.id.menu_game -> gameMenuClicked()
            R.id.menu_ranking -> rankingMenuClicked()
            R.id.menu_hall_of_fame -> hallOfFameClicked()
        }
        return true
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
}
