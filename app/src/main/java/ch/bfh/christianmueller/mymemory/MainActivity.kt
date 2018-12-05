package ch.bfh.christianmueller.mymemory

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val SCORE_TAG: String = "scoreTag"

    private var score: Int = 0

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.tv_score)
        restoreScore(savedInstanceState)
        initButtons()
    }

    private fun initButtons() {
        val bordGame = findViewById<LinearLayout>(R.id.ll_game_bord)

        setButtonActionOnChildButtons(bordGame)
    }

    private fun setButtonActionOnChildButtons(bordGame: LinearLayout) {
        for (i in 0 until bordGame.childCount) {
            val child = bordGame.getChildAt(i)
            if (child is Button) {
                child.setOnClickListener { buttonAction() }
            } else if (child is LinearLayout) {
                setButtonActionOnChildButtons(child)
            }
        }
    }

    private fun buttonAction() {
        Toast.makeText(this, "Woah!", Toast.LENGTH_SHORT).show()
        updateScore(++score)
    }


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

    fun updateScore(newScore: Int) {
        textView.text = "$newScore"
    }

    fun onResetButtonClick(view: View) {
        score = 0
        updateScore(score)
    }
}
