package io.redditapp.data

import io.realm.Realm
import io.realm.RealmList
import io.redditapp.api.pesponses.*

class ModelToEntityMapper {

    private val realm: Realm = Realm.getDefaultInstance()

    fun convertPublications(resp: ResponsePublicationsModel) {

        realm.beginTransaction()

        val publicationsEntity = realm.createObject(PublicationsEntity::class.java)
        publicationsEntity.kind = resp.kind
        resp.data?.let { publicationsEntity.data = createDataEntity(it) }

        realm.commitTransaction()
    }

    private fun createDataEntity(model: DataModel): DataEntity? {
        val entity = realm.createObject(DataEntity::class.java)
        entity.dist = model.dist
        model.children?.let { entity.children = createChildrenEntity(it) }
        return entity
    }

    private fun createChildrenEntity(models: List<ChildrenModel>): RealmList<ChildrenEntity>? {
        val list = RealmList<ChildrenEntity>()
        models.forEach {
            val entity = realm.createObject(ChildrenEntity::class.java)
            entity.kind = it.kind
            it.data?.let { entity.data = createDataChildrenEntity(it) }
            list.add(entity)
        }
        return list
    }

    private fun createDataChildrenEntity(model: DataChildrenModel): DataChildrenEntity? {
        val entity = realm.createObject(DataChildrenEntity::class.java)
        entity.id = model.id
        entity.title = model.title
        entity.created = model.createdDate
        entity.authorFullname = model.author
        entity.numComments = model.numComments
        entity.thumbnailUrl = model.thumbnailUrl
        entity.imageUrl = model.imageUrl
        model.previewImages?.let { entity.previewImages = createPreviewImagesEntity(it) }
        return entity
    }

    private fun createPreviewImagesEntity(model: PreviewImageModel): PreviewImageEntity? {
        val entity = realm.createObject(PreviewImageEntity::class.java)
        entity.enabled = model.enabled
        model.images?.let { entity.images = createImagesEntity(it) }
        return entity
    }

    private fun createImagesEntity(models: List<ImageModel>): RealmList<ImageEntity>? {
        val list = RealmList<ImageEntity>()
        models.forEach {
            val entity = realm.createObject(ImageEntity::class.java)
            it.source?.let { entity.source = createImageItemEntity(it) }
            list.add(entity)
        }
        return list
    }

    private fun createImageItemEntity(model: ImageItemModel): ImageItemEntity? {
        val entity = realm.createObject(ImageItemEntity::class.java)
        entity.url = model.url
        entity.width = model.width
        entity.height = model.height
        return entity
    }

}