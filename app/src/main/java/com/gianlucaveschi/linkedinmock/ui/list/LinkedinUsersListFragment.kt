package com.gianlucaveschi.linkedinmock.ui.list

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gianlucaveschi.linkedinmock.R
import com.gianlucaveschi.linkedinmock.databinding.UsersListFragmentBinding
import com.gianlucaveschi.linkedinmock.domain.util.NetworkStateHelper
import com.gianlucaveschi.linkedinmock.ui.SharedViewModel
import com.gianlucaveschi.linkedinmock.ui.detail.LinkedinUserDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber


@AndroidEntryPoint
class LinkedinUsersListFragment : Fragment(), UsersAdapter.OnUserClickListener {

    private lateinit var binding: UsersListFragmentBinding
    private lateinit var usersAdapter: UsersAdapter
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UsersListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        viewModel.getLinkedinUsersList()

        //Set adapter and recyclerview
        setRecyclerView()

        //Setting up the observers internally triggers the data to be retrieved from a DataSource
        if (networkIsAvailable()) {
            collectLinkedinUsers()
        }
    }

    private fun collectLinkedinUsers() {
        lifecycleScope.launchWhenStarted {
            viewModel.linkedinUsers.collect {
                if (!it.isNullOrEmpty()) {
                    Timber.d("got the $it")
                    usersAdapter.setLinkedinUsersList(it)
                    binding.mainProgressBar.visibility = View.INVISIBLE
                    binding.usersRecView.adapter = usersAdapter
                }
            }
        }
    }

    private fun setRecyclerView() {
        //Initialize adapter
        usersAdapter = UsersAdapter(this)
        binding.usersRecView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = usersAdapter
        }
    }

    private fun networkIsAvailable(): Boolean {
        val available =
            NetworkStateHelper.isNetworkConnected(
                activity?.getSystemService(Context.CONNECTIVITY_SERVICE)
                        as ConnectivityManager
            )
        if (!available) {
            alertError()
        }
        return available
    }

    private fun alertError() {
        AlertDialog.Builder(activity).setTitle("No Internet Connection")
            .setMessage("Please check your internet connection and try again")
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert).show()
    }

    override fun onUserClicked(userUid: Int) {
        val action = LinkedinUsersListFragmentDirections.actionListFragmentToDetailFragment(userUid)
        findNavController().navigate(action)
    }
}