package io.redditapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.redditapp.R
import io.redditapp.data.Preferences
import io.redditapp.ui.base.BaseFragment
import io.redditapp.ui.base.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*

const val IMG_URL = "img_url"
const val IS_IMG_FULLSIZE = "is_img_fullsize"

class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var publicationAdapter: PublicationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = Preferences(requireContext())
        viewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MainViewModel::class.java)

        showLoading(true)

        publicationAdapter = PublicationAdapter {
            it?.let {
                viewModel.imageClicked(it)
            }
        }
        recyclerView.adapter = publicationAdapter

        viewModel.respPosts observe {
            publicationAdapter.setData(it)
            showLoading(false)
        }
        viewModel.errorMsg observe {
            showLoading(false)
            showDialog(requireContext(), null, "it")
        }
        viewModel.imageUrl observe {
            val bundle = Bundle()
            bundle.putString(IMG_URL, it.first)
            bundle.putBoolean(IS_IMG_FULLSIZE, it.second)
            findNavController().navigate(R.id.action_mainFragment_to_imageFragment, bundle)
        }

        rlSwipe.setOnRefreshListener {
            viewModel.refreshClicked()
        }

    }

    fun showLoading(isLoading: Boolean) {
        rlSwipe.isRefreshing = isLoading
    }

}