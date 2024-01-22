package com.example.chat.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chat.R
import com.example.chat.databinding.ActivityRegisterBinding
import com.example.chat.ui.home.HomeActivity
import com.example.chat.ui.login.LoginActivity
import com.example.chat.ui.showMessage

class RegisterActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityRegisterBinding
    lateinit var viewModel: RegisterViewModel
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
                negAction = message.onNegActionClick
            )

        }
        viewModel.events.observe(this, ::handleEvents)
    }

    private fun handleEvents(registerViewEvent: RegisterViewEvent?) {
        when (registerViewEvent) {
            RegisterViewEvent.NavigateToHome -> {
                navigateToHome()
            }

            RegisterViewEvent.NavigateToLogin -> {
                naviagteToLogin()
            }

            else -> {}
        }


    }

    private fun naviagteToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun initView() {
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this

    }
}