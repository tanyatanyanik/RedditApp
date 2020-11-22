package io.redditapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.redditapp.data.Preferences
import io.redditapp.ui.main.MainViewModel
import io.redditapp.ui.view_image.ImageViewModel

class ViewModelFactory(val pref: Preferences) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }


}