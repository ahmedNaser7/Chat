package com.example.chat.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.chat.R
import com.example.chat.ui.addRoom.AddRoomActivity
import com.example.chat.databinding.ActivityHomeBinding
import com.example.chat.model.Room
import com.example.chat.ui.Constants
import com.example.chat.ui.login.LoginActivity
import com.example.chat.ui.showMessage
import com.example.chat.ui.chat.ChatActivity

class HomeActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initView()
        subscribeToLiveData()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadRooms()
    }

    private fun subscribeToLiveData() {
        viewModel.events.observe(this, ::handleEvents)
        viewModel.roomsLiveData.observe(this) {
            adapter.chengeData(it)
        }
        viewModel.messageLiveData.observe(this) {
            showMessage(
                it.message ?: "",
                posActionName = it.posActionName,
                posAction = it.onPosActionClick,
                negAction = it.onNegActionClick,
                negActionName = it.negActionName,
                isCancelable = it.isCancelable
            )
        }
    }

    private fun handleEvents(homeViewEvents: HomeViewEvents?) {

        when (homeViewEvents) {
            HomeViewEvents.navigateToAddRoom -> {
                navigateToAddRoom()
            }

            HomeViewEvents.navigateToLogIn -> {
                navigateToLogIn()
            }

            else -> {}
        }

    }

    private fun navigateToLogIn() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToAddRoom() {
        val intent = Intent(this, AddRoomActivity::class.java)
        startActivity(intent)
    }

    val adapter = RoomsRecyclerAdapter()
    private fun initView() {
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this
        viewBinding.content.roomsRecycler.adapter = adapter
        adapter.onItemClickListener =
            RoomsRecyclerAdapter.OnItemClickListener { position, room ->
                navigateToRoon(room!!)
            }
    }

    private fun navigateToRoon(room: Room) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(Constants.EXTRA_ROOM, room)
        startActivity(intent)
    }
}