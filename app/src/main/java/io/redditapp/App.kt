package io.redditapp

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

const val REALM_NAME = "redditapp.realm"

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name(REALM_NAME)
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)

    }
}