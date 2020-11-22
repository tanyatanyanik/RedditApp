package io.redditapp.ui.view_image

import androidx.lifecycle.ViewModel
import io.redditapp.data.Preferences
import io.redditapp.utils.SingleLiveEvent

class ImageViewModel(val pref: Preferences) : ViewModel() {

    val imgUrl = SingleLiveEvent<Pair<String, Boolean>>()

    fun urlArgReceived(url: String?, isImgFull: Boolean?) {

        if (url != null && isImgFull != null)
            imgUrl.postValue(Pair(url, isImgFull))
    }

    fun saveClicked() {
        // todo
    }


}