package ch.bfh.christianmueller.mymemory.game

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import ch.bfh.christianmueller.mymemory.R
import ch.bfh.christianmueller.mymemory.StartActivityActionInterface
import java.lang.IllegalStateException
import kotlin.random.Random


class MemoryGameFragment : Fragment() {

    private val SCORE_TAG: String = "scoreTag"
    private var pictures = mutableListOf("🐶", "🐱", "🐻", "🐨", "🐷", "🐸", "🐙")
    private val cardsOnBoardGame = mutableListOf<Button>()
    private val cardIconMap = mutableMapOf<Button, String>()
    private val currentCards = mutableListOf<Button>()

    private var first: String? = null
    private var second: String? = null
    private var score: Int = 0
    private var matchesToFinish: Int = 0
    private var foundMatches: Int = 0


    private lateinit var textView: TextView
    private lateinit var bordGame: LinearLayout
    private lateinit var restartButton: Button

    private lateinit var callback: BoardGameActions

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_game, container, false)
        callback = requireContext() as? BoardGameActions ?: throw IllegalStateException("context is not BoardGameActions")
        textView = view.findViewById(R.id.tv_score)
        bordGame = view.findViewById(R.id.ll_game_bord)
        restartButton = view.findViewById(R.id.bu_restart_game)
        restartButton.setOnClickListener { restartGame() }
        restoreScore(savedInstanceState)
        initGame(savedInstanceState)
        return view
    }

    private fun initGame(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            findCardsOnBoardGame(bordGame)
            shuffleCardsAndInitGame()
        }
    }

    private fun shuffleCardsAndInitGame() {
        val ids = (0..(cardsOnBoardGame.size - 1)).toMutableList()
        matchesToFinish = cardsOnBoardGame.size / 2
        for (i in 0 until matchesToFinish) {
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
                Toast.makeText(requireContext(), "It's a Match", Toast.LENGTH_SHORT).show()
                foundMatches++
                if (allMatchesFound()) {
                    callback.saveGameResult(score, cardsOnBoardGame.size)
                    showWinDialog()
                }
            }
        } else if (nextClickAfterMissMatch()) {
            turnCardsBack()
        }
    }

    private fun showWinDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(R.string.alert_finished)
            .setMessage(R.string.alert_text_finished)
            .setPositiveButton("Ok") { _, _ -> restartAction() }.show()
    }

    private fun restartAction() {
        restartGame()
    }

    private fun allMatchesFound() = matchesToFinish == foundMatches

    private fun cardsMatch() = first == second

    private fun turnCard(cardToTurn: Button, iconOnButton: String?) {
        if (iconOnButton != null) {
            cardToTurn.text = iconOnButton
            currentCards.add(cardToTurn)
            cardToTurn.setOnClickListener { null }
        } else {
            Log.wtf("MemoryGameFragment.turnCard", "no Icon on Card")
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
    

    private fun restoreScore(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            score = it.getInt(SCORE_TAG)
            updateScore(score)
        }
    }

    private fun updateScore(newScore: Int) {
        textView.text = "$newScore"
    }

    private fun restartGame() {
        score = 0
        foundMatches = 0
        updateScore(score)
        clearAllListsAndStates()
        pictures = mutableListOf("🐶", "🐱", "🐻", "🐨", "🐷", "🐸", "🐙")
        initGame(null)
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
