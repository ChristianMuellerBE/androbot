package ch.bfh.christianmueller.mymemory.game


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView

import ch.bfh.christianmueller.mymemory.R
import ch.bfh.christianmueller.mymemory.data.AppDatabase
import ch.bfh.christianmueller.mymemory.data.GameResultsRepo
import ch.bfh.christianmueller.mymemory.data.PlayerRepo
import ch.bfh.christianmueller.mymemory.model.GameResult
import java.text.SimpleDateFormat


class HallOfFameFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var database: AppDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hall_of_fame, container, false)
        listView = view.findViewById(R.id.lv_hall_of_fame)
        database = AppDatabase.build(requireContext().applicationContext)
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, getFameItems())
        listView.adapter = adapter
        return view
    }

    private fun getFameItems(): List<String> {
        return PlayerRepo(database).findAllPlayerIds()
            .mapNotNull { playerId -> GameResultsRepo(database).findBestResultForPlayer(playerId) }
            .sortedBy { it.usedClicks }
            .map{ gameResult ->  createListItem(gameResult)}
    }

    private fun createListItem(result: GameResult): String{
        val player = PlayerRepo(database).findPlayerById(result.playerId)
        val formattedDate = SimpleDateFormat("dd:MM:yyyy - HH:mm:ss").format(result.date)
        return "$formattedDate - ${player?.name ?: ' '}: ${result.usedClicks} clicks by ${result.amountOfCards} cards"
    }
}
