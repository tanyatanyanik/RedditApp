package io.redditapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.redditapp.api.ApiRepository
import io.redditapp.api.Result
import io.redditapp.api.pesponses.ResponsePublicatiosModel
import io.redditapp.data.Preferences
import io.redditapp.data.RedditRepository
import io.redditapp.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainViewModel(val pref: Preferences) : ViewModel() {

    val parentJob = Job()
    val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    val scope = CoroutineScope(coroutineContext)

    val respPosts = MutableLiveData<ResponsePublicatiosModel>()
    val errorMsg = SingleLiveEvent<String>()

    private val repo = RedditRepository()

    fun getTopPublications() {

        scope.launch {
            val resp = ApiRepository().getTopPublications()
            when (resp) {
                is Result.Success -> {
                    scope.launch(Dispatchers.Main) {
                        val answer = resp.data as ResponsePublicatiosModel
                        respPosts.postValue(answer)

                    }
                }
                is Result.Error -> {
                    errorMsg.postValue(resp.errMsg)
                    scope.launch(Dispatchers.Main) {
                        errorMsg.postValue(resp.errMsg)
                    }
                }
            }
        }
    }

}