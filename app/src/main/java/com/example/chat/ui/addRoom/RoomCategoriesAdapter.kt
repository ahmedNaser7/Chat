package com.example.chat.ui.addRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chat.databinding.ItemRoomCategoryBinding
import com.example.chat.model.Category

class RoomCategoriesAdapter(val items: List<Category>) : BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val viewHolder: ViewHolder

        if (convertView == null) {
            //  create view holder
            val itemBinding = ItemRoomCategoryBinding
                .inflate(
                    LayoutInflater.from(parent?.context),
                    parent,
                    false,
                )
            viewHolder = ViewHolder(itemBinding)
            itemBinding.root.tag = viewHolder

        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.bind(items[position])

        return viewHolder.itemBinding.root
    }

    class ViewHolder(val itemBinding: ItemRoomCategoryBinding) {
        fun bind(item: Category) {
            itemBinding.title.text = item.title
            itemBinding.image.setImageResource(item.imageId)
        }

    }
}