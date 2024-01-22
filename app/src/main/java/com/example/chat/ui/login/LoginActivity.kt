package com.example.chat.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chat.R
import com.example.chat.databinding.ActivityLoginBinding
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.register.RegisterActivity
import com.example.chat.ui.showMessage

class LoginActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel.messageLiveData.observe(this) { message ->
            showMessage(
                message = message.message ?: "something went wrong",
                posActionName = "ok",
                posAction = message.onPosActionClick,
                negActionName = message.negActionName,
                negAction = message.onNegActionClick,
                isCancelable = message.isCancelable
            )

        }

        fun navigateToHome() {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()

        }

        fun navigateToRegister() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }

        fun handleEvents(loginActivityEvent: LoginActivityEvent?) {
            when (loginActivityEvent) {
                LoginActivityEvent.navigateToHome -> {
                    navigateToHome()
                }

                LoginActivityEvent.naviagteToRegister -> {
                    navigateToRegister()
                }

                else -> {}
            }
        }

        viewModel.events.observe(this, ::handleEvents)
    }


    private fun initView() {
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this

    }
}