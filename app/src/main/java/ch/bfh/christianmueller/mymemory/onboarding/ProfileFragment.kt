package ch.bfh.christianmueller.mymemory.onboarding


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import ch.bfh.christianmueller.mymemory.R
import ch.bfh.christianmueller.mymemory.StartActivityActionInterface

class ProfileFragment : Fragment() {

    private lateinit var callback: StartActivityActionInterface

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        callback = requireContext() as StartActivityActionInterface ?: throw (RuntimeException("shit happends"))
        setHasOptionsMenu(true)
        return  view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        callback.finishedProfileMenuClicked()
        return true
    }


}
