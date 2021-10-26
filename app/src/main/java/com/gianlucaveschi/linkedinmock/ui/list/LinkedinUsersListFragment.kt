package com.gianlucaveschi.linkedinmock.ui.list

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gianlucaveschi.linkedinmock.databinding.UsersListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class LinkedinUsersListFragment : Fragment() {

    private lateinit var binding: UsersListFragmentBinding
    private lateinit var usersAdapter: UsersAdapter
    private val viewModel: LinkedinUsersListViewModel by viewModels()

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
        //if(networkIsAvailable()){
        //setUpObservers()
    }

//    private fun setUpObservers() {
//        viewModel.usersList.let {
//            if (!it.isNullOrEmpty()) {
//                usersAdapter.setLinkedinUsersList(it)
//                binding.mainProgressBar.visibility = View.INVISIBLE
//                binding.usersRecView.adapter = usersAdapter
//            }
//        }
//    }


    private fun setRecyclerView() {
        //Initialize adapter
        usersAdapter = UsersAdapter()
        binding.usersRecView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = usersAdapter
        }
    }

//    private fun networkIsAvailable(): Boolean {
//        val available =
//            NetworkStateHelper.isNetworkConnected(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
//        if (!available) {
//            alertError()
//        }
//        return available
//    }

    companion object {
        fun newInstance() = LinkedinUsersListFragment()
    }
}