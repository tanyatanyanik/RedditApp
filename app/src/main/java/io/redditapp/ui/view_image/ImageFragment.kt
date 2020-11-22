package io.redditapp.ui.view_image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import io.redditapp.R
import io.redditapp.data.Preferences
import io.redditapp.ui.base.BaseFragment
import io.redditapp.ui.base.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_image.*

const val IMG_URL = "img_url"
const val IS_IMG_FULLSIZE = "is_img_fullsize"

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = Preferences(requireContext())
        viewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(ImageViewModel::class.java)

        val imgUrl = arguments?.getString(IMG_URL, "")
        val isImgFull = arguments?.getBoolean(IS_IMG_FULLSIZE, false)
        viewModel.urlArgReceived(imgUrl, isImgFull)

        viewModel.imgUrl observe {
            Glide.with(requireContext()).load(it.first).into(ivLargeImg)
            if (!it.second)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.no_origin_img),
                    Toast.LENGTH_SHORT
                ).show()
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        ivSave.setOnClickListener {
            viewModel.saveClicked()
        }

    }

}