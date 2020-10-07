package com.example.githubuser.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.detail.DetailFragment.Companion.ARG_USERNAME
import com.example.githubuser.model.User
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var userAdapter: UserAdapter

    private val scopeProvider = AndroidLifecycleScopeProvider.from(this)

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            it.title = getString(R.string.find_github_user)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel.initScopeProvider(scopeProvider)

        initRecyclerView()
        initSearchView()
        observeViewModel()
    }

    private fun observeViewModel() {
        searchViewModel.getUserList().observe(requireActivity(), Observer { userList ->
            userList?.let {
                renderRecyclerViewData(userList)
            }
        })

        searchViewModel.getLoading().observe(requireActivity(), Observer { isLoading ->
            isLoading?.let {
                handleLoading(isLoading)
            }
        })
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            tv_no_data.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun initSearchView() {
        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.searchUser(query)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
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
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val bundle = Bundle()
                bundle.putString(ARG_USERNAME, user.username)
                view?.findNavController()
                    ?.navigate(R.id.action_searchFragment_to_detailFragment, bundle)
            }
        })
    }
}
