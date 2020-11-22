package io.redditapp.ui.view_image

import android.content.ContentResolver
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import io.redditapp.data.Preferences
import io.redditapp.utils.DateUtils
import io.redditapp.utils.ImageUtils
import io.redditapp.utils.SingleLiveEvent

class ImageViewModel(val pref: Preferences) : ViewModel() {

    val imgUrl = SingleLiveEvent<Pair<String, Boolean>>()
    val imgSaved = SingleLiveEvent<Boolean>()

    fun urlArgReceived(url: String?, isImgFull: Boolean?) {

        if (url != null && isImgFull != null)
            imgUrl.postValue(Pair(url, isImgFull))
    }

    fun saveClicked(contentResolver: ContentResolver, drawable: Drawable) {
        val name = DateUtils.getDateString()
        val isSaved = ImageUtils.saveImage(contentResolver, drawable.toBitmap(), name)
        imgSaved.postValue(isSaved)
    }

}