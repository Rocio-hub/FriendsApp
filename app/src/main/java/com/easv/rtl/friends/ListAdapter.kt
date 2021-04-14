package com.easv.rtl.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easv.rtl.friends.Data.BEFriend

class ListAdapter(private val friends: List<BEFriend>, var clickListener: OnItemClickListener) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*val friend = friends[position]
        holder.bind(friend) */
        holder.initialize(friends.get(position), clickListener)
    }

    override fun getItemCount(): Int = friends.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtName = view.findViewById<TextView>(R.id.text_name)
        private val txtPhone = view.findViewById<TextView>(R.id.text_phone)
        private val imgProfile = view.findViewById<ImageView>(R.id.img_profile)

        fun initialize(friend: BEFriend, action: OnItemClickListener){
            txtName.text = friend.name
            txtPhone.text = friend.phone
      //      imgProfile.setImageBitmap(friend.cameraFile))

            itemView.setOnClickListener{
                action.onItemClick(friend, adapterPosition)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick (friends: BEFriend, position: Int)
    }
} 