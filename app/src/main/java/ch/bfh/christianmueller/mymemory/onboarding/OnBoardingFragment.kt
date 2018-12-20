package ch.bfh.christianmueller.mymemory.onboarding

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ch.bfh.christianmueller.mymemory.R
import ch.bfh.christianmueller.mymemory.StartActivity
import ch.bfh.christianmueller.mymemory.StartActivityActionInterface
import java.lang.IllegalStateException
import java.lang.RuntimeException

class OnBoardingFragment : Fragment() {

    private lateinit var callback: StartActivityActionInterface

    private lateinit var loggingButton: Button
    private lateinit var registerButton: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)
        callback = requireContext() as? StartActivityActionInterface ?: throw IllegalStateException("context is not StartActivityActionInterface")
        setupButtons(view)
        reloadLastRegistration()
        return view
    }

    private fun reloadLastRegistration() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val username = sharedPref.getString(StartActivity.USER_NAME_PREF_TAG, null)
        username?.let {
            loggingButton.isEnabled = true
            loggingButton.text = "Login as $it"
        }
    }

    private fun setupButtons(view: View) {
        loggingButton = view.findViewById(R.id.bu_login)
        loggingButton.setOnClickListener { callback.loggingButtonClicked() }
        registerButton = view.findViewById(R.id.bu_register)
        registerButton.setOnClickListener { callback.registerButtonClicked() }
    }
}