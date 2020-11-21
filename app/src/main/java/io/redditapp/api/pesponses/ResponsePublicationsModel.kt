package io.redditapp.api.pesponses

import com.squareup.moshi.Json

data class ResponsePublicationsModel(
    @field:Json(name = "kind") val kind: String?
    , @field:Json(name = "data") val data: DataModel?
)

data class DataModel(
    @field:Json(name = "dist") val dist: Int?
    , @field:Json(name = "children") val children: List<ChildrenModel>?
)

data class ChildrenModel(
    @field:Json(name = "kind") val kind: String?
    , @field:Json(name = "data") val data: DataChildrenModel?
)

data class DataChildrenModel(
    @field:Json(name = "id") val id: String?
    , @field:Json(name = "author") val author: String?
    , @field:Json(name = "created") val createdDate: Float?
    , @field:Json(name = "thumbnail") val thumbnailUrl: String?
    , @field:Json(name = "num_comments") val numComments: Int?
    , @field:Json(name = "preview") val previewImages: PreviewImageModel?

)

data class PreviewImageModel(
    @field:Json(name = "images") val images: List<ImageModel>?
    , @field:Json(name = "enabled") val enabled: Boolean?
)

data class ImageModel(
    @field:Json(name = "source") val source: ImageItemModel?
)

data class ImageItemModel(
    @field:Json(name = "url") val url: String?
    , @field:Json(name = "width") val width: Int?
    , @field:Json(name = "height") val height: Int?
)