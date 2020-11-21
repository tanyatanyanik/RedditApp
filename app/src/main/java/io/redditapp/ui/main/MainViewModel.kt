package io.redditapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.redditapp.api.ApiRepository
import io.redditapp.api.Result
import io.redditapp.api.pesponses.ResponsePublicationsModel
import io.redditapp.data.DataChildrenEntity
import io.redditapp.data.Preferences
import io.redditapp.data.RedditRepository
import io.redditapp.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(val pref: Preferences) : ViewModel() {

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    val respPosts = MutableLiveData<List<DataChildrenEntity>>()
    val imgUrl = MutableLiveData<String?>()
    val errorMsg = SingleLiveEvent<String>()

    private val repo = RedditRepository()

    fun getTopPublications() {
        scope.launch {
            val resp = ApiRepository().getTopPublications()
            when (resp) {
                is Result.Success -> {
                    scope.launch(Dispatchers.Main) {
                        val answer = resp.data as ResponsePublicationsModel
                        repo.saveNewPublications(answer)
                        loadPublicationsFromRepository()
                    }
                }
                is Result.Error -> {
                    errorMsg.postValue(resp.errMsg)
                    scope.launch(Dispatchers.Main) {
                        errorMsg.postValue(resp.errMsg)
                        loadPublicationsFromRepository()
                    }
                }
            }
        }
    }

    private fun loadPublicationsFromRepository() {
        repo.getPublications()?.let {
            respPosts.postValue(it)
        }
    }

    fun imageClicked(dataChildrenId: String) {

        val imageUrl = (respPosts.value?.firstOrNull {
            it.id == dataChildrenId
        })?.getFullImageUrl()

        imgUrl.postValue(imageUrl)
    }

}