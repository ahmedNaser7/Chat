package com.example.chat.ui.addRoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.chat.R
import com.example.chat.databinding.ActivityAddRoomBinding
import com.example.chat.ui.showMessage

class AddRoomActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityAddRoomBinding
    private val viewModel: AddRoomViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initView()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
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
        viewModel.events.observe(this, ::handleEvents)
    }

    private fun handleEvents(addRoomViewEvents: AddRoomViewEvents?) {
        when (addRoomViewEvents) {
            AddRoomViewEvents.NavigteToHomeAndFinish -> {
                finish()
            }

            else -> {}
        }
    }


    lateinit var categoriesAdapter: RoomCategoriesAdapter
    private fun initView() {
        categoriesAdapter = RoomCategoriesAdapter(viewModel.categories)
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this
        viewBinding.content.categoriesSpinner.adapter = categoriesAdapter
        viewBinding.content.categoriesSpinner.onItemSelectedListener =
            object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.onCategorySelected(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
    }
}