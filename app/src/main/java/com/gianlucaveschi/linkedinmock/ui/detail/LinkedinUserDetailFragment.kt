package com.gianlucaveschi.linkedinmock.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.gianlucaveschi.linkedinmock.databinding.UserDetailViewBinding
import com.gianlucaveschi.linkedinmock.domain.LinkedinUser
import com.gianlucaveschi.linkedinmock.ui.SharedViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.lang.Exception

@AndroidEntryPoint
class LinkedinUserDetailFragment : Fragment() {

    private lateinit var binding: UserDetailViewBinding
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserDetailViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        viewModel.getLinkedinUserDetail(196373)

        //Setting up the observers internally triggers the data to be retrieved from a DataSource
        collectLinkedinUserDetail()
    }

    private fun collectLinkedinUserDetail() {
        lifecycleScope.launchWhenStarted {
            viewModel.linkedinUserDetail.collect {
                it?.let {
                    Timber.d("got the $it but the image is ${it.info.pictureUrl}")
                    binding.mainProgBar.visibility = View.INVISIBLE
                    binding.linkedinUserTitle.text = it.info.name
                    binding.linkedinUserId.text = it.uid.toString()
                    binding.linkedinUserEmail.text = it.info.email
                    bindImage(it)
                }
            }
        }
    }

    private fun bindImage(linkedinUserItem: LinkedinUser) {
        Picasso.get()
            .load(linkedinUserItem.info.pictureUrl)
            .into(binding.linkedinUserImage, object : Callback {
                override fun onSuccess() {
                    binding.linkedinUserImage.visibility = View.VISIBLE
                    binding.imageProgBar.visibility = View.INVISIBLE
                }

                override fun onError(e: Exception?) {
                    Timber.d("on error $e")
                    binding.imageProgBar.visibility = View.VISIBLE
                    binding.linkedinUserImage.visibility = View.INVISIBLE
                }
            })
    }

    companion object {
        fun newInstance() = LinkedinUserDetailFragment()
    }
}