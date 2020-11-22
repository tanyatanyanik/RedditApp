package io.redditapp.data

import io.realm.RealmList
import io.realm.RealmObject

const val EMPTY_THUMBNAIL_URL = "default"

open class PublicationsEntity(
    var kind: String? = null
    , var data: DataEntity? = null
) : RealmObject() {

    fun getPublications(): List<DataChildrenEntity>? {
        val list = ArrayList<DataChildrenEntity>()
        data?.children?.forEach { child ->
            child.data?.let {
                list.add(it)
            }
        }
        list.sortByDescending {
            it.numComments
        }
        return list
    }

}

open class DataEntity(
    var dist: Int? = null
    , var children: RealmList<ChildrenEntity>? = null
) : RealmObject()

open class ChildrenEntity(
    var kind: String? = null
    , var data: DataChildrenEntity? = null
) : RealmObject()

open class DataChildrenEntity(
    var authorFullname: String? = null
    , var id: String? = null
    , var title: String? = null
    , var created: Float? = null
    , var thumbnailUrl: String? = null
    , var imageUrl: String? = null
    , var numComments: Int? = null
    , var previewImages: PreviewImageEntity? = null
) : RealmObject() {

    fun getThumbNailUrl(): String? {
        if (thumbnailUrl != null && thumbnailUrl!!.isNotEmpty() && thumbnailUrl != EMPTY_THUMBNAIL_URL)
            return thumbnailUrl
        return null
    }

    fun getDateMillis(): Long? {
        created?.let {
            return it.toLong() * 1000L
        }
        return null
    }

    fun getFullImageUrl(): String? {
        if (imageUrl != null && imageUrl!!.isNotEmpty() && imageUrl != EMPTY_THUMBNAIL_URL) {
            if (imageUrl!!.contains(".jpg")) {
                return imageUrl!!.substringBefore(".jpg") + ".jpg"
            }
        }
        return null
    }

    fun getImageSource(): String? {
        val imgList = ArrayList<ImageEntity>()
        previewImages?.images?.let {
            imgList.addAll(it)
        }
        imgList.sortByDescending {
            it.source?.width
        }
        if (imgList.isNotEmpty()) {
            imgList[0].source?.url?.let {
                return it.substringBefore(".jpg") + ".jpg"
            }
        }
        return null
    }

}

open class PreviewImageEntity(
    var enabled: Boolean? = null
    , var images: RealmList<ImageEntity>? = null
) : RealmObject()

open class ImageEntity(
    var source: ImageItemEntity? = null
) : RealmObject()

open class ImageItemEntity(
    var url: String? = null
    , var width: Int? = null
    , var height: Int? = null
) : RealmObject()