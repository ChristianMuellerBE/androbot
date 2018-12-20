package ch.bfh.christianmueller.mymemory.onboarding

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Button
import android.widget.EditText
import ch.bfh.christianmueller.mymemory.R
import ch.bfh.christianmueller.mymemory.StartActivity
import ch.bfh.christianmueller.mymemory.StartActivityActionInterface
import java.lang.IllegalStateException

class RegisterFragment : Fragment() {

    private lateinit var callback: StartActivityActionInterface
    private lateinit var lazyButton: Button
    private lateinit var userNameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        callback = requireContext() as? StartActivityActionInterface ?: throw IllegalStateException("shit happends")
        lazyButton = view.findViewById(R.id.bu_lazy_register)
        lazyButton.setOnClickListener { insertDefaultUserInformantion() }
        userNameEditText = view.findViewById(R.id.et_username)
        passwordEditText = view.findViewById(R.id.et_password)
        confirmPasswordEditText = view.findViewById(R.id.et_password_confirm)
        setHasOptionsMenu(true)
        return view
    }

    private fun insertDefaultUserInformantion() {
        userNameEditText.setText("Max Power")
        passwordEditText.setText("pow3er")
        confirmPasswordEditText.setText("pow3er")
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.onboarding_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (userInputIsValid()) {
            saveRegistration()
            callback.finishedRegisterMenuClicked()
            return true
        } else {
            return false
        }
    }

    private fun saveRegistration() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(StartActivity.USER_NAME_PREF_TAG, userNameEditText.editableText.toString().trim())
            putString(StartActivity.PASSWORD_PREF_TAG, passwordEditText.editableText.toString())
            commit()
        }
    }

    private fun userInputIsValid(): Boolean {
        val userNameValid: Boolean = userNameIsValid()
        val passwordValid: Boolean = validatePassword()
        return userNameValid && passwordValid
    }

    private fun userNameIsValid(): Boolean {
        return validateNotEmpty(userNameEditText, getString(R.string.error_imput_empty))
    }

    private fun validatePassword(): Boolean {
        var passwordNotEmpty = validateNotEmpty(passwordEditText, getString(R.string.error_imput_empty))
        var confirmPasswordNotEmpty = validateNotEmpty(confirmPasswordEditText, getString(R.string.error_imput_empty))
        if (passwordNotEmpty && confirmPasswordNotEmpty) {
            if (inputMatches(passwordEditText, confirmPasswordEditText)) {
                return true
            } else {
                confirmPasswordEditText.error = getString(R.string.error_password_not_match)
            }
        }
        return false
    }

    private fun inputMatches(passwordEditText: EditText, confirmPasswordEditText: EditText): Boolean {
        return passwordEditText.editableText.toString() ==  confirmPasswordEditText.editableText.toString()
    }

    private fun validateNotEmpty(editText: EditText, errorText: String): Boolean {
        val username = editText.editableText.toString().trim()
        if (username.isNotBlank()) {
            return true
        } else {
            editText.error = errorText
            return false
        }
    }
}
