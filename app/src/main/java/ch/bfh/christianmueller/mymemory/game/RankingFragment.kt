package ch.bfh.christianmueller.mymemory.game


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import ch.bfh.christianmueller.mymemory.R
import ch.bfh.christianmueller.mymemory.data.AppDatabase
import ch.bfh.christianmueller.mymemory.data.GameResultsRepo
import ch.bfh.christianmueller.mymemory.model.GameResult
import java.text.SimpleDateFormat


class RankingFragment : Fragment() {

    private lateinit var listview: ListView
    private lateinit var database: AppDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ranking, container, false)
        listview = view.findViewById(R.id.lv_ranking)
        database = AppDatabase.build(requireContext().applicationContext)

        val list = GameResultsRepo(database).findAllGameResults().map { it -> createMyListItem(it) }
        val myListAdapter = MyListAdapter(requireContext(), R.layout.list_item, list)
        listview.adapter = myListAdapter
        return view
    }

    private fun createMyListItem(result: GameResult): MyListItem {
        val formattedDate = SimpleDateFormat("dd:MM:yyy - HH:mm:ss").format(result.date)
        val text = "$formattedDate - ${result.usedClicks} clicks used by ${result.amountOfCards} cards"
        return MyListItem(text)
    }
}
