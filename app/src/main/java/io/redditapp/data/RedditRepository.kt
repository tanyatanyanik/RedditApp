package io.redditapp.data

import io.realm.Realm
import io.redditapp.api.pesponses.ResponsePublicationsModel

class RedditRepository {

    var realm: Realm = Realm.getDefaultInstance()

    fun saveNewPublications(resp: ResponsePublicationsModel) {
        val v = realm.where(PublicationsEntity::class.java).findFirst()
        if (v != null) clearPublications()
        ModelToEntityMapper().convertPublications(resp)
    }

    fun getPublications(): List<DataChildrenEntity>? {
        val v = realm.where(PublicationsEntity::class.java).findFirst()
        v?.let {
            return it.getPublications()
        }
        return null
    }

    private fun clearPublications() {
        realm.beginTransaction()
        val b = realm.where(PublicationsEntity::class.java).findAll().deleteAllFromRealm()
        realm.commitTransaction()
    }

    private fun clearDatabase() {
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }

}