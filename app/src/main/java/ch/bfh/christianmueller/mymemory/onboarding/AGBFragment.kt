package ch.bfh.christianmueller.mymemory.onboarding


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.*
import android.widget.TextView

import ch.bfh.christianmueller.mymemory.R
import ch.bfh.christianmueller.mymemory.StartActivityActionInterface
import java.lang.IllegalStateException

class AGBFragment : Fragment() {

    private lateinit var callback: StartActivityActionInterface
    private lateinit var agbTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_agb, container, false)
        callback = requireContext() as? StartActivityActionInterface ?: throw IllegalStateException("context is not StartActivityActionInterface")
        agbTextView = view.findViewById(R.id.tv_agb)
        addAGBToTextView()
        setHasOptionsMenu(true)
        return view
    }

    private fun addAGBToTextView() {
        val agb = resources.openRawResource(R.raw.agb).bufferedReader().use {
            it.readText()
        }
        agbTextView.text = Html.fromHtml(agb)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        callback.agbAccepted()
        return true
    }
}
