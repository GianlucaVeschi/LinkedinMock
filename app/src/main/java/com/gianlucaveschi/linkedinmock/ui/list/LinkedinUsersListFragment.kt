package com.gianlucaveschi.linkedinmock.ui.list

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gianlucaveschi.linkedinmock.databinding.UsersListFragmentBinding
import com.gianlucaveschi.util.NetworkStateHelper
import com.gianlucaveschi.linkedinmock.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import androidx.lifecycle.flowWithLifecycle

@AndroidEntryPoint
class LinkedinUsersListFragment : Fragment(), UsersAdapter.OnUserClickListener {

    private lateinit var binding: UsersListFragmentBinding
    private lateinit var usersAdapter: UsersAdapter
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UsersListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        setRecyclerView()

        if (networkIsAvailable()) {
            collectLinkedinUsers()
        }
    }

    private fun collectLinkedinUsers() {
        lifecycleScope.launchWhenStarted {
            viewModel.getLinkedinUsersList()
            viewModel.linkedinUsersBasic
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED) //Avoid collecting flows when UI is in the background
                .collect {
                    if (!it.isNullOrEmpty()) {
                        usersAdapter.setLinkedinUsersList(it)
                        binding.mainProgressBar.visibility = View.INVISIBLE
                        binding.usersRecView.adapter = usersAdapter
                    }
                }
        }
    }

    private fun setRecyclerView() {
        usersAdapter = UsersAdapter(this)
        binding.usersRecView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = usersAdapter
        }
    }

    private fun networkIsAvailable(): Boolean {
        val available = NetworkStateHelper.isNetworkConnected(
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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