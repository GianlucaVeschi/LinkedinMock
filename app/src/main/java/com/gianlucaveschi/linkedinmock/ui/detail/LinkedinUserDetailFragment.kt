package com.gianlucaveschi.linkedinmock.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.gianlucaveschi.linkedinmock.databinding.UserDetailViewBinding
import com.gianlucaveschi.linkedinmock.domain.users.LinkedinUserExtended
import com.gianlucaveschi.linkedinmock.ui.SharedViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.lang.Exception

@AndroidEntryPoint
class LinkedinUserDetailFragment : Fragment() {

    private val args: LinkedinUserDetailFragmentArgs by navArgs()

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

        collectLinkedinUserDetail()
    }

    private fun collectLinkedinUserDetail() {
        lifecycleScope.launchWhenStarted {
            viewModel.getLinkedinUserDetail(args.userUid)
            viewModel.linkedinUserExtendedDetail.collect {
                it?.let {
                    initiateDetailUI(it)
                }
            }
        }
    }

    private fun initiateDetailUI(it: LinkedinUserExtended) {
        //Set up info
        binding.apply {
            mainProgBar.visibility = View.INVISIBLE
            linkedinUserTitle.text = it.info.name
            linkedinUserId.text = it.uid
            linkedinUserEmail.text = it.info.email
            linkedinUserNickname.text = it.info.nickname
        }

        //Set up Image
        it.info.image?.let { imageUrl ->
            Timber.d("imageUrl is found: $imageUrl")
            binding.linkedinUserImageUrl.text = imageUrl
            bindUserDetailImage(imageUrl)
        }
    }

    private fun bindUserDetailImage(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .resize(200,200)
            .centerInside()
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
}
