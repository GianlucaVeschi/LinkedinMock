package com.gianlucaveschi.linkedinmock.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gianlucaveschi.linkedinmock.databinding.UserItemViewBinding
import com.gianlucaveschi.linkedinmock.domain.LinkedinUser
import java.lang.Exception

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.LinkedinUserViewHolder>() {

    //Internal List containing all LinkedinUsers
    private val linkedinUsersList = ArrayList<LinkedinUser>()

    fun setLinkedinUsersList(newLinkedinUsers: List<LinkedinUser>) {
        this.linkedinUsersList.clear()
        this.linkedinUsersList.addAll(newLinkedinUsers)
        notifyDataSetChanged()
    }

    // LinkedinUser View Holder
    class LinkedinUserViewHolder(private val binding: UserItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //Using data binding
        fun bind(linkedinUserItem: LinkedinUser) {
            binding.linkedinUserId.text = linkedinUserItem.uid.toString()
            binding.linkedinUserTitle.text = linkedinUserItem.info.name
        }

        fun bindImage(LinkedinUserItem: LinkedinUser) {
            //Set LinkedinUser image
//            Picasso.get()
//                .load(LinkedinUserItem.LinkedinUserUrl)
//                .into(binding.LinkedinUserImage, object : Callback {
//                    override fun onSuccess() {
//                        binding.LinkedinUserImage.visibility = View.VISIBLE
//                        binding.progBar.visibility = View.INVISIBLE
//                    }
//
//                    override fun onError(e: Exception?) {
//                        binding.progBar.visibility = View.VISIBLE
//                        binding.LinkedinUserImage.visibility = View.INVISIBLE
//                    }
//                })
        }

    }

    // Called when the RecyclerView needs a view holder to represent an item.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkedinUserViewHolder {

        // Used to create views from XML layouts
        val layoutInflater = LayoutInflater.from(parent.context)

        //ViewHolderBinding Object
        val binding = UserItemViewBinding
            .inflate(layoutInflater, parent, false)

        return LinkedinUserViewHolder(binding)
    }

    // Tell the RecyclerView how many items the adapter has for it to display
    override fun getItemCount() = linkedinUsersList.size

    // Called by RecyclerView to display the data for one list item at the specified position
    override fun onBindViewHolder(holder: LinkedinUserViewHolder, position: Int) {

        // Get the current LinkedinUser in the list
        val linkedinUserItem = linkedinUsersList[position]

        // Pass the current LinkedinUser to the viewHolder
        holder.bind(linkedinUserItem)
        holder.bindImage(linkedinUserItem)
    }
}