package io.redditapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import io.redditapp.R
import io.redditapp.data.Preferences
import io.redditapp.ui.base.BaseFragment
import io.redditapp.ui.base.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*

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
        val sp = Preferences(requireContext())
        viewModel = ViewModelProvider(this, ViewModelFactory(sp)).get(MainViewModel::class.java)

        publicationAdapter = PublicationAdapter {
            it?.let {
                viewModel.imageClicked(it)
            }
        }
        recyclerView.adapter = publicationAdapter

        viewModel.respPosts observe {
            Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
            publicationAdapter.setData(it)
            showLoading(false)
        }
        viewModel.errorMsg observe {
            showDialog(requireContext(), null, "it")
            showLoading(false)
        }
        viewModel.getTopPublications()

        rlSwipe.setOnRefreshListener {
            viewModel.getTopPublications()
        }

    }

    fun showLoading(isLoading: Boolean) {
        rlSwipe.isRefreshing = false
    }

}