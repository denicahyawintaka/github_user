package com.example.githubuser.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.model.User
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_follower.*

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    private val scopeProvider = AndroidLifecycleScopeProvider.from(this)

    private val detailViewModel: DetailViewModel by viewModels()

    private lateinit var userAdapter: UserAdapter

    companion object {

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(DetailFragment.ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.initScopeProvider(scopeProvider)

        arguments?.let {
            it.getString(DetailFragment.ARG_USERNAME)?.let { username ->
                detailViewModel.fetchFollowing(username)
            }
        }

        initRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        detailViewModel.getFollowing().observe(requireActivity(), Observer { userDetail ->
            userDetail?.let { followers ->
                renderRecyclerViewData(followers)
            }
        })
    }

    private fun renderRecyclerViewData(userList: List<User>) {
        if (userList.isNotEmpty()) {
            tv_no_data.visibility = View.INVISIBLE
            userAdapter.submitList(userList)
        } else {
            userAdapter.submitList(emptyList())
            tv_no_data.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerView() {
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )

        userAdapter = UserAdapter(emptyList())
        rv_search.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
            adapter = userAdapter
        }
    }
}
