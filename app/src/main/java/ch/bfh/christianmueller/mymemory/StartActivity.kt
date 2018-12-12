package ch.bfh.christianmueller.mymemory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import ch.bfh.christianmueller.mymemory.game.MainActivity
import ch.bfh.christianmueller.mymemory.onboarding.OnBoardingFragment
import ch.bfh.christianmueller.mymemory.onboarding.ProfileFragment
import ch.bfh.christianmueller.mymemory.onboarding.RegisterFragment

class StartActivity : AppCompatActivity(), StartActivityActionInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_start_activity, OnBoardingFragment())
            .commit()
    }

    override fun loggingButtonClicked() {
        Toast.makeText(this,"Not yet implemented!!", Toast.LENGTH_SHORT).show()
    }

    override fun registerButtonClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_start_activity, RegisterFragment())
            .commit()
    }

    override fun finishedRegisterMenuClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_start_activity, ProfileFragment())
            .commit()
    }

    override fun finishedProfileMenuClicked() {
        startActivity(MainActivity.getMainActivityIntent(this))
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_start_activity, OnBoardingFragment())
            .commit()
    }

    companion object {
        const val PROFILE_PICTURE_PREF_TAG: String  = "profilePicture_pref"
        const val USER_NAME_PREF_TAG: String  = "userName_pref"
        const val PASSWORD_PREF_TAG: String  = "password_pref"
    }
}
