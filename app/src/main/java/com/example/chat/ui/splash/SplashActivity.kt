package com.example.chat.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.example.chat.R
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.home.HomeViewEvents
import com.example.chat.ui.login.LoginActivity


class SplashActivity : AppCompatActivity() {

    val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
        subScribeToLiveData()
    }

    private fun subScribeToLiveData() {
        viewModel.events
            .observe(this) {
                when (it) {
                    SplashViewEvents.navigateToHome -> {
                        startToHome()
                    }

                    SplashViewEvents.navigateToLogin -> {
                        startToLogIn()
                    }

                    else -> {}
                }
            }
    }

    private fun startToLogIn() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initView() {
        Handler().postDelayed({
            viewModel.redirect()
        }, 2000)
    }

    private fun startLoginActivty() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}