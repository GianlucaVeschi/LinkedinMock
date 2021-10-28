package com.gianlucaveschi.linkedinmock.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gianlucaveschi.linkedinmock.databinding.UserItemViewBinding
import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserBasic
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class UsersAdapter(
    private val listener: OnUserClickListener
) : RecyclerView.Adapter<UsersAdapter.LinkedinUserViewHolder>() {

    //Internal List containing all LinkedinUsers
    private val linkedinUsersList = ArrayList<LinkedinUserBasic>()

    fun setLinkedinUsersList(newLinkedinUserBasics: List<LinkedinUserBasic>) {
        this.linkedinUsersList.clear()
        this.linkedinUsersList.addAll(newLinkedinUserBasics)
    }

    // LinkedinUser View Holder
    inner class LinkedinUserViewHolder(
        private val binding: UserItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(linkedinUserBasicItem: LinkedinUserBasic) {
            binding.linkedinUserId.text = linkedinUserBasicItem.uid.toString()
            binding.linkedinUserTitle.text = linkedinUserBasicItem.info.name
        }

        fun bindAdapterImage(imageUrl : String) {
            Picasso.get()
                .load(imageUrl)
                .resize(200,200)
                .centerInside()
                .into(binding.linkedinUserImage, object : Callback {
                    override fun onSuccess() {
                        binding.linkedinUserImage.visibility = View.VISIBLE
                        binding.progBar.visibility = View.INVISIBLE
                    }

                    override fun onError(e: Exception?) {
                        binding.progBar.visibility = View.VISIBLE
                        binding.linkedinUserImage.visibility = View.INVISIBLE
                    }
                })
        }

        override fun onClick(v: View?) {
            listener.onUserClicked(linkedinUsersList[adapterPosition].uid)
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
        val linkedinUserItem : LinkedinUserBasic = linkedinUsersList[position]

        // Pass the current LinkedinUser to the viewHolder
        holder.bind(linkedinUserItem)
        linkedinUserItem.info.pictureUrl?.let{
            holder.bindAdapterImage(linkedinUserItem.info.pictureUrl)
        }
    }

    interface OnUserClickListener {
        fun onUserClicked(userUid : Int)
    }
}