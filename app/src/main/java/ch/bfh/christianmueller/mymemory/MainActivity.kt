package ch.bfh.christianmueller.mymemory

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val SCORE_TAG: String = "scoreTag"
    private var pictures = mutableListOf("🐶", "🐱", "🐻", "🐨", "🐷", "🐸", "🐙")
    private val cardsOnBoardGame = mutableListOf<Button>()
    private val cardIconMap = mutableMapOf<Button, String>()
    private val currentCards = mutableListOf<Button>()

    private var first: String? = null
    private var second: String? = null
    private var score: Int = 0
    private var matchesToFinish: Int = 0


    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.tv_score)
        restoreScore(savedInstanceState)
        initGame(savedInstanceState)
    }

    private fun initGame(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val bordGame = findViewById<LinearLayout>(R.id.ll_game_bord)
            findCardsOnBoardGame(bordGame)
            shuffleCardsAndInitGame()
        }
    }

    private fun shuffleCardsAndInitGame() {
        val ids = (0..(cardsOnBoardGame.size - 1)).toMutableList()
        val possibleMatches = cardsOnBoardGame.size/ 2
        for (i in 0 until possibleMatches) {
            val emoji = pictures.removeAt(Random.nextInt(pictures.size))
            picOnCard(ids, emoji)
            picOnCard(ids, emoji)
        }
    }

    private fun picOnCard(ids: MutableList<Int>, emoji: String) {
        val nextInt = Random.nextInt(ids.size)
        val removeAt = ids.removeAt(nextInt)
        val button = cardsOnBoardGame[removeAt]
        button.setBackgroundColor(Color.BLUE)
        button.setOnClickListener { buttonAction(button) }
        cardIconMap[button] = emoji
        button.text = ""
    }

    private fun findCardsOnBoardGame(bordGame: LinearLayout) {
        for (i in 0 until bordGame.childCount) {
            val child = bordGame.getChildAt(i)
            if (child is Button) {
                cardsOnBoardGame.add(child)
            } else if (child is LinearLayout) {
                findCardsOnBoardGame(child)
            }
        }
    }

    private fun buttonAction(clickedButton: Button) {
        updateScore(++score)
        if (clickedOnFirstCard()) {
            val iconUnderClickedButton = cardIconMap[clickedButton]
            first = iconUnderClickedButton
            turnCard(clickedButton, iconUnderClickedButton)
        } else if (clickedOnSecondCard()) {
            val iconUnderClickedButton = cardIconMap[clickedButton]
            second = iconUnderClickedButton
            turnCard(clickedButton, iconUnderClickedButton)
            if (cardsMatch()) {
                resetCurrentCards()
                disableMatchedCards()
                Toast.makeText(this, "It's a Match", Toast.LENGTH_SHORT).show()
            }
        } else if (nextClickAfterMissMatch()) {
            turnCardsBack()
        }
    }

    private fun cardsMatch() = first == second

    private fun turnCard(cardToTurn: Button, iconOnButton: String?) {
        if (iconOnButton != null) {
            cardToTurn.text = iconOnButton
            currentCards.add(cardToTurn)
            cardToTurn.setOnClickListener { null }
        } else {
            Log.wtf("MainActivity.turnCard", "no Icon on Card")
        }
    }

    private fun turnCardsBack() {
        for (b in currentCards) {
            b.text = ""
            b.setOnClickListener { buttonAction(b) }
        }
        resetCurrentCards()
    }

    private fun disableMatchedCards() {
        for (b in currentCards) {
            b.setOnClickListener { null }
        }
    }

    private fun clickedOnSecondCard() = first != null && second == null

    private fun clickedOnFirstCard() = first == null && second == null

    private fun nextClickAfterMissMatch() = first != null && second != null

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let {
            it.putInt(SCORE_TAG, score)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        restoreScore(savedInstanceState)
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun restoreScore(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            score = it.getInt(SCORE_TAG)
            updateScore(score)
        }
    }

    private fun updateScore(newScore: Int) {
        textView.text = "$newScore"
    }

    fun onResetButtonClick(view: View) {
        if (view is Button) {
            score = 0
            updateScore(score)
            clearAllListsAndStates()
            pictures = mutableListOf("🐶", "🐱", "🐻", "🐨", "🐷", "🐸", "🐙")
            initGame(null)
        }else{
            Log.wtf("MainActivity.onResetButtonClick","onResetButtonClick was not on a Button")
        }
    }

    private fun clearAllListsAndStates() {
        cardsOnBoardGame.clear()
        cardIconMap.clear()
        resetCurrentCards()
    }

    private fun resetCurrentCards() {
        first = null
        second = null
        currentCards.clear()
    }
}
