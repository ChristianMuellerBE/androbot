package ch.bfh.christianmueller.mymemory.game


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import ch.bfh.christianmueller.mymemory.R
import ch.bfh.christianmueller.mymemory.data.AppDatabase
import ch.bfh.christianmueller.mymemory.data.PlayerRepo

class SettingsFragment : Fragment() {

    private lateinit var nukeButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        nukeButton = view.findViewById(R.id.bu_settings_nuke_players)
        nukeButton.setOnClickListener { nukePlayers() }
        return  view
    }

    private fun nukePlayers() {
        PlayerRepo(AppDatabase.build(requireContext().applicationContext)).deleteAllPlayerrs()
    }
}
