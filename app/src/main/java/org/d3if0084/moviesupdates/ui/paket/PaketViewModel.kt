package org.d3if0084.moviesupdates.ui.paket

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0084.moviesupdates.MainActivity
import org.d3if0084.moviesupdates.model.Paket
import org.d3if0084.moviesupdates.network.ApiStatus2
import org.d3if0084.moviesupdates.network.PaketApi
import org.d3if0084.moviesupdates.network.UpdateWorker
import java.util.concurrent.TimeUnit


class PaketViewModel : ViewModel() {

    private val data = MutableLiveData<List<Paket>>()
    private val status = MutableLiveData<ApiStatus2>()
    init {
        retrieveData()
    }
    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus2.LOADING)

            try {
                data.postValue(PaketApi.service.getPaket())
                status.postValue(ApiStatus2.SUCCESS)

            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus2.FAILED)

            }
        }
    }

    fun getData(): LiveData<List<Paket>> = data
    fun getStatus(): LiveData<ApiStatus2> = status
    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            MainActivity.CHANNEL_ID,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}