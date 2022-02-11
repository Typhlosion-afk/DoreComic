package com.example.dorecomic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dorecomic.R
import com.example.dorecomic.fragment.login.LoginFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginFragment = LoginFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.login_container, loginFragment, null)
            .commit()
    }

    override fun onBackPressed() {
        setResult(0)
        super.onBackPressed()
    }
}