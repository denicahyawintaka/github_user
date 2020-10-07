package com.example.githubuser.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.githubuser.MainActivity
import com.example.githubuser.R
import com.example.githubuser.model.UserDetail
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val scopeProvider = AndroidLifecycleScopeProvider.from(this)

    private val detailViewModel: DetailViewModel by viewModels()

    companion object {
        const val ARG_USERNAME = "argUsername"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.apply {
            title = getString(R.string.user_detail)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailViewModel.initScopeProvider(scopeProvider)

        getUsername()?.let { username ->
            detailViewModel.fetchUserDetail(username)
        }

        initTabs()
        observeViewModel()
    }

    private fun observeViewModel() {
        detailViewModel.getUserDetail().observe(requireActivity(), Observer { userDetail ->
            userDetail?.let {
                renderUserDetail(userDetail)
            }
        })

        detailViewModel.getLoading().observe(requireActivity(), Observer { isLoading ->
            isLoading?.let {
                handleLoading(isLoading)
            }
        })
    }

    private fun renderUserDetail(userDetail: UserDetail) {
        Glide.with(requireContext())
            .load(userDetail.avatar)
            .fitCenter()
            .into(avatar)

        name.text =
            if (!userDetail.name.isNullOrEmpty()) userDetail.name else getString(R.string.no_name)
        email.text = userDetail.email

        getUsername()?.let {
            username.text = getString(R.string.username_with_bracket, it)
        }

    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun getUsername() = arguments?.getString(ARG_USERNAME)

    private fun initTabs() {
        getUsername()?.let { username ->
            val sectionsPagerAdapter =
                SectionsPagerAdapter(
                    mContext = requireContext(),
                    username = username,
                    fm = childFragmentManager
                )
            view_pager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(view_pager)

            (activity as MainActivity).supportActionBar?.elevation = 0f
        }
    }
}
