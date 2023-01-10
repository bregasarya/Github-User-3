package com.example.githubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.ConstantObject.Companion.EXTRA_USERNAME
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.ui.main.ListGitUserAdapter

class FollowingFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: ListGitUserAdapter
    private lateinit var username: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments
        username = arg?.getString(EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = ListGitUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowUsers.setHasFixedSize(true)
            rvFollowUsers.layoutManager = LinearLayoutManager(activity)
            rvFollowUsers.adapter = adapter
        }
        showLoading(true)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.getFollowingData(username)
        viewModel.getListFollowingData().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}