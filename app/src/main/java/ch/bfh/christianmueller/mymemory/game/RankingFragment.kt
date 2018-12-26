package ch.bfh.christianmueller.mymemory.game


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView

import ch.bfh.christianmueller.mymemory.R
import ch.bfh.christianmueller.mymemory.StartActivity
import ch.bfh.christianmueller.mymemory.data.AppDatabase
import ch.bfh.christianmueller.mymemory.data.GameResultsRepo
import ch.bfh.christianmueller.mymemory.data.PlayerRepo
import ch.bfh.christianmueller.mymemory.model.GameResult
import java.text.SimpleDateFormat


class RankingFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var database: AppDatabase
    private lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ranking, container, false)
        listView = view.findViewById(R.id.lv_ranking)
        database = AppDatabase.build(requireContext().applicationContext)
        textView = view.findViewById(R.id.tv_ranking_title)

        val titleRefix = getString(R.string.tv_ranking_title)
        val playerName = getPlayerName()
        textView.text = "$titleRefix $playerName"
        val list = findOrderedGameResultsForCurrentPlayer(playerName)
        val myListAdapter = MyListAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
        listView.adapter = myListAdapter
        return view
    }

    private fun findOrderedGameResultsForCurrentPlayer(playerName: String?): List<MyListItem> {
        playerName?.let {
            PlayerRepo(database).findPlayerByName(playerName)?.let {  player ->
                Log.i("MyMemory", "found Player: ${player.name} id: ${player.id}")
                val foundRecords =
                    GameResultsRepo(database).findOrderedGameResultsForPlayer(player.id!!).map(this::createMyListItem)
                if (foundRecords.isNotEmpty()){
                    return foundRecords
                }
            }
        }
        return createSingleNoRecordsListIte(playerName)
    }

    private fun getPlayerName(): String? {
        val playerName = requireContext()
            .getSharedPreferences(StartActivity.SHARED_PREF_TAG, Context.MODE_PRIVATE)
            .getString(StartActivity.USER_NAME_PREF_TAG, null)
        return playerName
    }


    private fun createSingleNoRecordsListIte(playerName: String?): List<MyListItem> {
        return listOf(MyListItem("No Records found for player ${playerName ?: '?'}"))
    }

    private fun createMyListItem(result: GameResult): MyListItem {
        val formattedDate = SimpleDateFormat("dd:MM:yyy - HH:mm:ss").format(result.date)
        val text = "$formattedDate - ${result.usedClicks} clicks used by ${result.amountOfCards} cards"
        return MyListItem(text)
    }
}
