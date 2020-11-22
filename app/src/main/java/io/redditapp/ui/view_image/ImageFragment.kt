package io.redditapp.ui.view_image

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
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
const val PERMISSION_STORAGE = 454545

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

        checkImgPermission()

        val imgUrl = arguments?.getString(IMG_URL, "")
        val isImgFull = arguments?.getBoolean(IS_IMG_FULLSIZE, false)
        viewModel.urlArgReceived(imgUrl, isImgFull)

        viewModel.imgUrl observe {
            Glide.with(requireContext())
                .load(it.first)
                .placeholder(R.drawable.ic_insert_photo)
                .error(R.drawable.ic_image_not_supported)
                .fallback(R.drawable.ic_image_not_supported)
                .fitCenter()
                .into(ivLargeImg)

            if (!it.second)
                showToast(getString(R.string.no_origin_img))
        }
        viewModel.imgSaved observe {
            if (it)
                showToast(getString(R.string.img_saved))
            else
                showToast(getString(R.string.saving_failed))
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        ivSave.setOnClickListener {
            viewModel.saveClicked(requireContext().contentResolver, ivLargeImg.drawable)
        }

    }

    fun checkImgPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && ActivityCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE), PERMISSION_STORAGE
            )
        } else ivSave.isVisible = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && ActivityCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            ivSave.isVisible = true
        } else {
            ivSave.isVisible = false
            showToast(getString(R.string.unable_save))
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}