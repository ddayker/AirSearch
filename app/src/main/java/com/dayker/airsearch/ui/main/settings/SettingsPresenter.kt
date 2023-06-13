package com.dayker.airsearch.ui.main.settings

import com.dayker.airsearch.network.ApiService
import com.dayker.airsearch.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsPresenter(
    private val apiService: ApiService
) : SettingsContract.Presenter() {

    override fun downloadCountriesFromApi() {
        view?.initRecyclerView()
        coroutineScope.launch {
            try {
                val response =
                    apiService.getCountries(API_KEY)
                withContext(Dispatchers.Main) {
                    view?.setCountries(response.countries)
                }
            } catch (e: Exception) {
                println(javaClass.simpleName + e.message)
                withContext(Dispatchers.Main) {
                    view?.dataIsNotAvailable()
                }
            }
        }
    }
}