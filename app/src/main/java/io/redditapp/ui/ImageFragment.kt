package io.redditapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import io.redditapp.R
import io.redditapp.ui.base.BaseFragment

class ImageFragment : BaseFragment() {

    companion object {
        fun newInstance() = ImageFragment()
    }

    private lateinit var viewModel: ImageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ImageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}