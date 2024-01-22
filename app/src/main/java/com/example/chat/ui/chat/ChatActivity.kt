package com.example.chat.ui.chat

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.R
import com.example.chat.databinding.ActivityChatBinding
import com.example.chat.model.Room
import com.example.chat.ui.Constants

class ChatActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityChatBinding
    val viewModel: ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initParams()
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel.toastLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        viewModel.newMessageLiveData
            .observe(this) {
                messagesAdapter.addNewMessage(it)
                viewBinding.content.messageRecycler.smoothScrollToPosition(
                    messagesAdapter.itemCount
                )
            }
    }

    private fun initParams() {

        val room = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.EXTRA_ROOM, Room::class.java)
        } else {
            intent.getParcelableExtra(Constants.EXTRA_ROOM) as Room?
        }

        viewModel.changeRoom(room)
    }

    val messagesAdapter = MessagesAdapter(mutableListOf())
    private fun initViews() {
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this
        viewBinding.content.messageRecycler.adapter = messagesAdapter
        (viewBinding.content.messageRecycler.layoutManager as LinearLayoutManager).stackFromEnd =
            true
    }
}