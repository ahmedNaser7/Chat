package com.example.chat.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.databinding.ItemRoomBinding
import com.example.chat.model.Category
import com.example.chat.model.Room

class RoomsRecyclerAdapter(var itemList: List<Room>? = listOf()) :
    RecyclerView.Adapter<RoomsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = itemList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList?.get(position))
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { view ->
                view.onItemClick(position, itemList!![position])
            }
        }
    }

    class ViewHolder(private val itemBinding: ItemRoomBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(room: Room?) {
            itemBinding.title.text = room?.title
            itemBinding.image.setImageResource(
                Category.getCategoriesImageByCategoryId(room?.categoryId)
            )
        }
    }

    fun chengeData(rooms: List<Room>?) {
        itemList = rooms
        notifyDataSetChanged()
    }


    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, room: Room?)
    }

}
